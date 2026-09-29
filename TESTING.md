# Smart Pantry Manager — Testing

## 1. Testing Overview

Testing was performed to verify that the main functions of Smart Pantry Manager operate correctly and that the application follows the strict recipe-matching requirement.

The main areas tested were pantry management, data persistence, navigation, settings and recipe matching.

## 2. Pantry Item Testing

| Test                                     | Expected Result                            | Status |
| ---------------------------------------- | ------------------------------------------ | ------ |
| Add a pantry item with valid information | Item is saved successfully                 | Pass   |
| Add an item without a name               | Validation prevents saving                 | Pass   |
| Add an item without a quantity           | Validation prevents saving                 | Pass   |
| Add an item with an invalid quantity     | Validation prevents saving                 | Pass   |
| View saved pantry items                  | Items are displayed in RecyclerView        | Pass   |
| Edit an existing item                    | Updated information is saved and displayed | Pass   |
| Delete an existing item                  | Item is removed from the pantry            | Pass   |
| Close and reopen the application         | Saved pantry data remains available        | Pass   |

## 3. Recipe Matching Testing

| Test                                     | Expected Result                          | Status |
| ---------------------------------------- | ---------------------------------------- | ------ |
| Pantry contains all required ingredients | Matching recipe is displayed             | Pass   |
| Pantry is missing a required ingredient  | Recipe is not displayed                  | Pass   |
| Pantry quantity is insufficient          | Recipe is not displayed                  | Pass   |
| Pantry has sufficient quantity           | Recipe can be displayed                  | Pass   |
| Singular/plural ingredient variation     | Compatible ingredient names are matched  | Pass   |
| Compatible units are used                | Quantities can be compared appropriately | Pass   |
| No recipes satisfy requirements          | No-match message is displayed            | Pass   |

## 4. Recipe Detail Testing

| Test                          | Expected Result                     | Status |
| ----------------------------- | ----------------------------------- | ------ |
| Select a suggested recipe     | Recipe detail screen opens          | Pass   |
| View required ingredients     | Ingredients are displayed           | Pass   |
| View preparation instructions | Instructions are displayed          | Pass   |
| Press Back                    | User returns to the previous screen | Pass   |

## 5. Navigation Testing

| Test                     | Expected Result              | Status |
| ------------------------ | ---------------------------- | ------ |
| Select Add Item          | Add Item screen opens        | Pass   |
| Select View Items        | Pantry list opens            | Pass   |
| Select Suggested Recipes | Recipe suggestions open      | Pass   |
| Select Settings          | Settings screen opens        | Pass   |
| Press Back               | Previous screen is displayed | Pass   |

## 6. Settings Testing

| Test                  | Expected Result                     | Status |
| --------------------- | ----------------------------------- | ------ |
| Enable Expiry Alerts  | Setting is stored                   | Pass   |
| Disable Expiry Alerts | Setting is stored                   | Pass   |
| Change Units setting  | Setting is stored                   | Pass   |
| Reopen Settings       | Previously selected settings remain | Pass   |

## 7. Strict Matching Example

A strict matching test was performed using a recipe that requires multiple ingredients.

For example, if a recipe requires:

* Bread
* Apple
* Milk

and the pantry contains only:

* Bread
* Apple

the recipe must not appear in the Suggested Recipes list.

When the missing milk is added in a sufficient quantity, the recipe can become eligible for suggestion.

This test verifies the main business rule of the application: suggested recipes must be achievable using ingredients already available in the user's pantry.

## 8. Conclusion

The testing performed confirms that the main application functions operate as intended.

Pantry CRUD operations, persistent SQLite storage, navigation, recipe details, settings and strict recipe matching were tested.

Particular attention was given to the strict recipe-matching requirement to ensure that recipes requiring missing or insufficient ingredients are not suggested.
