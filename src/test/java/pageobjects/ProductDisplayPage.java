package pageobjects;

import org.openqa.selenium.By;

import static utils.SeleniumCommands.getCommands;


public class ProductDisplayPage {

  private static final By PRODUCT_DISPLAY_PAGE = By.cssSelector("[data-locator-id='pdp-page']");
  private static final By SIZE_SMALL_BUTTON = By.cssSelector("[data-locator-id='pdp-size-s-select']");
  private static final By ADD_TO_BAG_BUTTON = By.cssSelector("[data-locator-id='pdp-addToBag-submit']");

  public ProductDisplayPage() {
    getCommands().waitForAndGetVisibleElementLocated(PRODUCT_DISPLAY_PAGE);
  }

  public ProductDisplayPage selectSmallSize() {
    getCommands().waitForAndClickOnElementLocated(SIZE_SMALL_BUTTON);
    return this;
  }

  public ProductDisplayPage selectAddToBag() {
    getCommands().waitForAndClickOnElementLocated(ADD_TO_BAG_BUTTON);
    getCommands().waitForAndGetVisibleElementLocated(ADD_TO_BAG_BUTTON);
    return this;
  }
}
