Feature: Removing a Product from the Bag

  Scenario: Removing a product from the Bag
    Given there are products in the bag for removal
    When i remove a product from the Bag
    Then the product is removed from the Bag
