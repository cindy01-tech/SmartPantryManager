# Smart Pantry Manager — Application Structure

## 1. Overview

Smart Pantry Manager is organised into separate Android Activities that provide the main functions of the application.

The application uses Android Intents to move between Activities and uses SQLite for persistent local data storage.

## 2. Main Activity

`MainActivity` provides the main navigation menu.

It allows the user to access:

* Add Pantry Item
* View Pantry Items
* Suggested Recipes
* Settings

## 3. Pantry Management

### AddItemActivity

`AddItemActivity` allows the user to enter a new pantry ingredient.

The user can provide:

* Ingredient name
* Category
* Quantity
* Unit
* Optional expiry date

Input validation is performed before the item is saved to SQLite.

### ViewItemsActivity

`ViewItemsActivity` retrieves pantry items from SQLite and displays them using a RecyclerView.

The screen allows the user to view the stored pantry information and access edit and delete functions.

### EditItemActivity

`EditItemActivity` loads an existing pantry item and allows the user to update its information.

The updated information is saved back to SQLite.

### PantryAdapter

`PantryAdapter` connects pantry data to the RecyclerView.

It displays the pantry item information and provides Edit and Delete actions.

## 4. Recipe Management

### SuggestedRecipesActivity

`SuggestedRecipesActivity` retrieves the user's current pantry items and passes them to the recipe-matching process.

Only recipes that satisfy all required ingredients and quantities are displayed.

### RecipeDAO

`RecipeDAO` handles recipe data retrieval and strict recipe matching.

It checks:

* Required ingredient availability
* Required quantities
* Compatible units
* Simple ingredient name variations

### RecipeAdapter

`RecipeAdapter` displays matching recipe names in a RecyclerView.

Selecting a recipe opens its detailed information.

### RecipeDetailActivity

`RecipeDetailActivity` displays:

* Recipe name
* Required ingredients
* Preparation instructions

## 5. Settings

### SettingsActivity

`SettingsActivity` provides application preferences.

The settings are stored using Android `SharedPreferences` so that the selected values remain available when the Settings screen is reopened.

## 6. Database Layer

### DatabaseHelper

`DatabaseHelper
