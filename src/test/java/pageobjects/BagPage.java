package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

import static utils.SeleniumCommands.getCommands;

public class BagPage {

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  private static final By REMOVE_BUTTON = By.cssSelector("[data-locator-id^='miniBag-remove']");
  private static final By QUANTITY_DROPDOWN = By.cssSelector("[data-locator-id^='miniBag-quantityDropdown']");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
  private static final By EMPTY_BAG_MESSAGE = By.cssSelector("[data-locator-id='miniBag-miniBagEmpty-read']");
  private static final By QUANTITY_TEXT_IN_DROPDOWN = By.cssSelector("span[class^='qty-selector_text']");
  private static final By LOADING_OVERLAY = By.cssSelector("[data-locator-id='miniBag-loadingOverlay-read']");

  public BagPage() {
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
  }

  public List<Long> getVariantIdsInBag() {
    return getBagItems().stream()
            .map(this::getVariantId)
            .collect(Collectors.toList());
  }

  private List<WebElement> getBagItems() {
    return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
  }

  private long getVariantId(WebElement bagItem) {
    return Long.parseLong(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE).replaceAll("\\D+", ""));
  }

  public void removeFirstProduct() {
    List<WebElement> bagItems = getBagItems();
    if (!bagItems.isEmpty()) {
      WebElement firstItem = bagItems.getFirst();
      firstItem.findElement(REMOVE_BUTTON).click();
      waitForBagToBeEmpty();
    }
  }

  public boolean isBagEmpty() {
    return getCommands().waitForAndGetVisibleElementLocated(EMPTY_BAG_MESSAGE).isDisplayed();
  }

  public void waitForBagToBeEmpty() {
    getCommands().waitForAndGetVisibleElementLocated(EMPTY_BAG_MESSAGE);
  }

  public void setQuantityForFirstProduct(int quantity) {
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);

    List<WebElement> bagItems = getBagItems();
    if (!bagItems.isEmpty()) {
      WebElement firstItem = bagItems.getFirst();
      WebElement quantityDropdown = firstItem.findElement(QUANTITY_DROPDOWN);

      Select select = new Select(quantityDropdown);
      select.selectByValue(String.valueOf(quantity));

      waitForLoadingOverlayToDisappear();
    }
  }

  public void waitForLoadingOverlayToDisappear() {
    getCommands().waitForElementToBeInvisibleLocated(LOADING_OVERLAY);
  }

  public boolean isQuantitySetTo(int expectedQuantity) {
    WebElement firstItem = getBagItems().getFirst();
    WebElement quantityTextElement = firstItem.findElement(QUANTITY_TEXT_IN_DROPDOWN);
    String quantityText = quantityTextElement.getText().trim();
    return quantityText.equals("Qty: " + expectedQuantity);
  }
}