# Data Driven Testing In JUnit

Story: Standard discount coupons
Business Rule: Different discount coupons can be used to obtain different levels of discount

Scenario Outline: Standard discount coupons
    Given I am a registered customer
    When I buy a product with a discount coupon of type <coupon>
    And my country of residence is <country>
    Then I should get a discount of <discount>%

    Examples: UK discount coupons
        | coupon    | country | discount | 
        | 10PERCENT | UK      | 10       | 
        | 20PERCENT | UK      | 20       |
        | 30PERCENT | UK      | 30       |

    Examples: FR discount coupons
        | coupon    | country | discount | 
        | 10PERCENT | FR      | 15       | 
        | 20PERCENT | FR      | 25       |
        | 30PERCENT | FR      | 35       |
