Feature: Add To Bag

  Scenario: Adding a product to the Bag
    Given the user is on a product page
    When adding the product to the Bag
    Then the product should appear in the Bag