Feature: Increasing Product Quantity in the Bag

  Scenario: Increasing the quantity of a product in the Bag
    Given there is a product in the bag for quantity increase
    When the user increases the quantity of the product
    Then the quantity of the product should increase
