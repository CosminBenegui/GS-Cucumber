Feature: Increasing and Decreasing Product Quantity in the Bag

  Scenario: Increasing and Decreasing the quantity of a product in the Bag
    Given there is one product and the quantity will be increase
    When the user will decreases the quantity of the product
    Then the quantity of the product should decrease to 1
