# Smart Pantry Manager — Strict Recipe Matching

## 1. Purpose

The main purpose of Smart Pantry Manager is to suggest recipes using ingredients that the user already has in their pantry.

The application follows a strict matching rule so that a suggested recipe does not require the user to purchase an additional ingredient.

## 2. Matching Rule

A recipe is displayed as a suggested recipe only when the user's pantry contains:

* Every ingredient required by the recipe.
* A sufficient quantity of every required ingredient.
* A compatible unit for comparing the available and required quantities.

If any required ingredient is missing or the available quantity is insufficient, the recipe is not suggested.

## 3. Ingredient Normalisation

The application normalises ingredient names before comparing them.

Simple singular and plural differences are handled so that names such as:

* `apple` and `apples`
* `banana` and `bananas`
* `tomato` and `tomatoes`

can be compared appropriately.

This helps prevent a valid match from being rejected only because of a simple singular/plural difference.

## 4. Quantity Matching

The application also checks whether the quantity available in the pantry is enough to prepare the recipe.

For example, if a recipe requires:

**2 pieces of bread**

and the pantry contains:

**1 piece of bread**

the recipe is not suggested.

If the pantry contains:

**2 or more pieces of bread**

the bread requirement can be satisfied.

## 5. Unit Handling

Common compatible units are handled by the recipe-matching logic.

Examples include:

* Pieces
* Grams
* Kilograms
* Millilitres
* Litres
* Cups

Where appropriate, quantities are converted into a common unit before comparison.

For example:

**1 kg = 1000 g**

and:

**1 liter = 1000 ml**

This allows the application to compare quantities even when compatible units are entered differently.

## 6. Matching Process

The matching process follows these general steps:

1. Read the user's current pantry items from SQLite.
2. Read the available recipes and their required ingredients.
3. Normalize ingredient names.
4. Compare each required ingredient with the pantry.
5. Check whether sufficient quantity is available.
6. Compare or convert compatible units where necessary.
7. Reject the recipe if any requirement cannot be satisfied.
8. Add the recipe to the suggested list only when all requirements are satisfied.

## 7. Example

Consider a recipe requiring:

* Bread - 2 pieces
* Apple - 1 piece
* Milk - 100 ml

If the pantry contains:

* Bread - 4 pieces
* Apple - 2 pieces
* Milk - 200 ml

all requirements are satisfied, so the recipe can be suggested.

If the pantry contains bread and apple but no milk, the recipe is rejected.

If the pantry contains only 1 piece of bread, the recipe is also rejected because the available quantity is insufficient.

## 8. Zero-Match Behaviour

When no recipes satisfy the strict matching requirements, the Suggested Recipes screen informs the user that there are no suitable recipes available and that additional pantry ingredients may be required.

This prevents the application from presenting recipes that cannot actually be prepared using the user's current pantry.

## 9. Implementation

The strict recipe-matching functionality is implemented in the `RecipeDAO` class.

The `SuggestedRecipesActivity` obtains the current pantry data and requests matching recipes from `RecipeDAO`.

The resulting matching recipes are displayed using the `RecipeAdapter`.

This separation keeps the recipe-matching logic separate from the user-interface code.
