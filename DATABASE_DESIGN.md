# Smart Pantry Manager — Database Design

## 1. Database Overview

Smart Pantry Manager uses a local SQLite database named `smart_pantry.db`.

SQLite was selected to provide persistent storage directly on the Android device without requiring an internet connection or an external database server.

The database is managed using Android's `SQLiteOpenHelper` class.

## 2. Database Tables

The application uses three main tables.

### 2.1 pantry_items

The `pantry_items` table stores ingredients currently available in the user's pantry.

| Field       | Data Type | Description          |
| ----------- | --------- | -------------------- |
| id          | INTEGER   | Primary key          |
| name        | TEXT      | Ingredient name      |
| category    | TEXT      | Ingredient category  |
| quantity    | REAL      | Available quantity   |
| unit        | TEXT      | Quantity unit        |
| expiry_date | TEXT      | Optional expiry date |

The table supports the application's pantry CRUD operations. Users can add, view, edit and delete pantry items.

### 2.2 recipes

The `recipes` table stores the recipes available in the application.

| Field        | Data Type | Description              |
| ------------ | --------- | ------------------------ |
| id           | INTEGER   | Primary key              |
| name         | TEXT      | Recipe name              |
| instructions | TEXT      | Preparation instructions |

The application contains a pre-loaded collection of recipes.

### 2.3 recipe_ingredients

The `recipe_ingredients` table stores the ingredients required by each recipe.

| Field           | Data Type | Description                   |
| --------------- | --------- | ----------------------------- |
| id              | INTEGER   | Primary key                   |
| recipe_id       | INTEGER   | Identifies the related recipe |
| ingredient_name | TEXT      | Required ingredient           |
| quantity        | REAL      | Required quantity             |
| unit            | TEXT      | Required quantity unit        |

The `recipe_id` field links each recipe ingredient to its corresponding recipe in the `recipes` table.

## 3. Relationships

The database uses a relationship between the `recipes` and `recipe_ingredients` tables.

One recipe can contain multiple required ingredients.

Therefore:

**recipes (1) → recipe_ingredients (many)**

The `pantry_items` table is used by the recipe-matching process to determine which recipes can be prepared using ingredients currently available to the user.

## 4. Strict Matching Process

When the user opens the Suggested Recipes screen, the application reads the available pantry items from SQLite.

The recipe-matching process then checks each recipe against the pantry.

A recipe is considered a match only when:

1. Every required ingredient exists in the pantry.
2. The available quantity is sufficient.
3. Compatible units can be compared or converted.
4. Ingredient name differences such as simple singular and plural forms can be normalised.

Recipes with missing ingredients or insufficient quantities are excluded from the suggested recipe list.

## 5. Database Persistence

Pantry data is stored locally in SQLite, so the information remains available when the application is closed and reopened.

The database therefore provides persistent storage for the user's pantry information and the application's recipe data.

## 6. Database Management

The `DatabaseHelper` class extends `SQLiteOpenHelper` and is responsible for:

* Creating the database.
* Creating the database tables.
* Managing database version changes.
* Seeding the initial recipe data.

The `RecipeDAO` class is responsible for retrieving recipes and applying the strict recipe-matching logic.
