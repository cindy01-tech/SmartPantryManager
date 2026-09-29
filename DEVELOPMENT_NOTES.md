# Smart Pantry Manager — Development Notes

## 1. Development Overview

Smart Pantry Manager was developed as a Java Android application using Android Studio and SQLite.

Development focused on implementing the required pantry management features and ensuring that recipe suggestions follow the strict ingredient-matching rule.

## 2. Challenges and Solutions

### 2.1 Pantry Data Persistence

One of the important requirements was to keep pantry information available after the application was closed.

**Solution:**

SQLite was implemented using Android's `SQLiteOpenHelper`. Pantry items are stored in the local `pantry_items` table so that the information can be retrieved when the application is reopened.

### 2.2 Pantry CRUD Operations

The application needed to support adding, viewing, editing and deleting pantry ingredients.

**Solution:**

Separate Activities were used for adding and editing items. `ViewItemsActivity` retrieves the stored items, while `PantryAdapter` provides Edit and Delete actions for individual RecyclerView items.

### 2.3 Strict Recipe Matching

A major development challenge was ensuring that recipes were not suggested when the user was missing an ingredient.

**Solution:**

The recipe-matching logic was implemented in `RecipeDAO`. Each required ingredient is checked against the current pantry. A recipe is only returned when every required ingredient is available in a sufficient quantity.

### 2.4 Ingredient Name Variations

Ingredient names can be entered in slightly different forms, such as singular and plural names.

**Solution:**

Ingredient names are normalised before comparison. This allows simple variations such as `apple` and `apples` to be matched appropriately.

### 2.5 Quantity and Unit Matching

Recipes can require quantities using different units, while pantry items may be entered using compatible units.

**Solution:**

The matching logic normalises common units and performs conversions where appropriate, including conversions between kilograms and grams, litres and millilitres, and cups and millilitres.

### 2.6 RecyclerView Display

The application needed to display pantry items and suggested recipes in a structured list.

**Solution:**

RecyclerView was implemented with custom adapters. `PantryAdapter` displays pantry information, while `RecipeAdapter` displays matching recipes and handles selection of a recipe.

### 2.7 Application Navigation

Several different screens were required, including pantry management, recipes and settings.

**Solution:**

Android Activities were separated according to their responsibilities and Android Intents were used to navigate between the screens.

## 3. Testing During Development

Testing was performed after implementing the main application functions.

The following areas were checked:

* Adding pantry items
* Viewing pantry items
* Editing pantry items
* Deleting pantry items
* SQLite data persistence
* Strict recipe matching
* Quantity validation
* Unit handling
* Recipe details
* Navigation
* Settings persistence

Particular attention was given to testing recipes with missing ingredients and insufficient quantities to ensure that they were not incorrectly suggested.

## 4. Lessons Learned

The development process provided practical experience with Android Activities, XML layouts, RecyclerView adapters, SQLite persistence, input validation and data-driven application logic.

The strict recipe-matching requirement also demonstrated the importance of validating all required conditions before presenting a result to the user.

## 5. Future Improvements

Possible future improvements include:

* Adding a date picker for expiry dates.
* Providing more detailed expiry notifications.
* Expanding the recipe collection.
* Improving the visual design of the application.
* Adding additional unit options where appropriate.

These improvements are outside the core functionality required for the current assignment.
