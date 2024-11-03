package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefs {

  private final WebDriver driver;
  private Long productId;
  private int expectedQuantity;
  private BagPage bagPage;

  public ProductStepDefs() {
    this.driver = Hooks.getDriver();
  }
  private BagPage getBagPage() {
    if (bagPage == null) {
      bagPage = new BagPage();
    }
    return bagPage;
  }

  private void addMultipleProductsToBag(int quantity) {
    for (int i = 0; i < quantity; i++) {
      navigateToProductPage();
      addProductToBag();
    }
  }

  private void navigateToProductPage() {
    driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    new ProductDisplayPage(); // Ensures the page is initialized on navigation
  }

  private void addProductToBag() {
    ProductDisplayPage productDisplayPage = new ProductDisplayPage();
    productDisplayPage.selectSmallSize();
    productDisplayPage.selectAddToBag();
  }

  // Scenario 1: Adding a Product to the Bag
  @Given("the user is on a product page")
  public void theUserIsOnAProductPage() {
    driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    productId = 39654522814667L;
    new ProductDisplayPage();
  }

  @When("adding the product to the Bag")
  public void addingTheProductToTheBag() {
    ProductDisplayPage productDisplayPage = new ProductDisplayPage();
    productDisplayPage.selectSmallSize();
    productDisplayPage.selectAddToBag();
  }

  @Then("the product should appear in the Bag")
  public void theProductShouldAppearInTheBag() {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    assertThat(variantIds).as("Expected product is in Bag").contains(productId);
  }

  // Scenario 2: Removing a Product from the Bag
  @Given("there are products in the bag for removal")
  public void thereIsAProductInTheBagForRemoval() {
    navigateToProductPage();
    addProductToBag();
    getBagPage().setQuantityForFirstProduct(10);
  }

  @When("i remove a product from the Bag")
  public void iRemoveAProduct() {
    getBagPage().removeFirstProduct();
  }

  @Then("the product is removed from the Bag")
  public void theProductShouldBeRemovedFromTheBag() {
    getBagPage().waitForBagToBeEmpty();
    boolean isBagEmpty = getBagPage().isBagEmpty();
    assertThat(isBagEmpty)
            .as("The bag should be empty after removing the product.")
            .isTrue();
  }

  // Scenario 3: Increasing Product Quantity in the Bag
  @Given("there is a product in the bag for quantity increase")
  public void thereIsAProductInTheBagForQuantityIncrease() {
    navigateToProductPage();
    addProductToBag();
  }

  @When("the user increases the quantity of the product")
  public void iAddQuantityToAProduct() {
    getBagPage().setQuantityForFirstProduct(2);
  }

  @Then("the quantity of the product should increase")
  public void theProductQuantityIsIncreased() {
    expectedQuantity = 2;
    assertThat(getBagPage().isQuantitySetTo(expectedQuantity))
            .as("Expected quantity of product in the bag to be 'Qty: " + expectedQuantity + "'")
            .isTrue();
  }

  //These are two approaches first is just decreasing the same product
  //The second adds the same product to the cart
  //Since we already tested the qty drop dropdown i was not sure of the scope for this test
  //Or the last scenario can be another case which was not covered before

  //Scenario 4: Decreasing Product Quantity in the Bag
  @Given("there are products in the bag for quantity decrease")
  public void thereAreMultipleUnitsOfProductInTheBag() {
    addMultipleProductsToBag(2);
  }

  @When("the user decreases the quantity of the product")
  public void theUserReducesTheQuantityOfTheProduct() {
    getBagPage().setQuantityForFirstProduct(1);
  }

  @Then("the quantity of the product should decrease")
  public void theQuantityOfTheProductShouldBeReducedByOne() {
    expectedQuantity = 1;
    assertThat(getBagPage().isQuantitySetTo(expectedQuantity))
            .as("Expected quantity of product in the bag to be 'Qty: " + expectedQuantity + "'")
            .isTrue();
  }

  //Scenario 5: Increasing and decreasing the Product Quantity in the Bag
  @Given("there is one product and the quantity will be increase")
  public void thereIsOnProductInTheBagAndIncreaseIt() {
    addMultipleProductsToBag(1);
    getBagPage().setQuantityForFirstProduct(7);
  }
  @When("the user will decreases the quantity of the product")
  public void theUserReducesTheQuantityOfTheProductAfterIncrease() {
    getBagPage().setQuantityForFirstProduct(1);
  }
  @Then("the quantity of the product should decrease to 1")
  public void theQuantityOfTheProductShouldBeReducedToOne() {
    expectedQuantity = 1;
    assertThat(getBagPage().isQuantitySetTo(expectedQuantity))
            .as("Expected quantity of product in the bag to be 'Qty: " + expectedQuantity + "'")
            .isTrue();
  }
}