Feature: Decreasing Product Quantity in the Bag

  Scenario: Decreasing the quantity of a product in the Bag
    Given there are products in the bag for quantity decrease
    When the user decreases the quantity of the product
    Then the quantity of the product should decrease
