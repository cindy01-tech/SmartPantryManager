package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPantryTable = "CREATE TABLE pantry_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "category TEXT, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiry_date TEXT)";

        db.execSQL(createPantryTable);

        createRecipeTables(db);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        if (oldVersion < 2) {
            // Add unit column for older databases.
            db.execSQL("ALTER TABLE pantry_items ADD COLUMN unit TEXT NOT NULL DEFAULT 'piece'");
        }

        if (oldVersion < 3) {
            createRecipeTables(db);
            seedRecipes(db);
        }
    }

    private void createRecipeTables(SQLiteDatabase db) {

        String createRecipesTable = "CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "instructions TEXT NOT NULL)";

        db.execSQL(createRecipesTable);

        String createRecipeIngredientsTable =
                "CREATE TABLE recipe_ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "ingredient_name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "FOREIGN KEY(recipe_id) REFERENCES recipes(id))";

        db.execSQL(createRecipeIngredientsTable);
    }

    private void seedRecipes(SQLiteDatabase db) {

        addRecipe(
                db,
                1,
                "Apple Toast",
                "Toast the bread and add sliced apple on top."
        );
        addIngredient(db, 1, "bread", 2, "pieces");
        addIngredient(db, 1, "apple", 1, "piece");

        addRecipe(
                db,
                2,
                "Egg Toast",
                "Toast the bread and serve with a cooked egg."
        );
        addIngredient(db, 2, "bread", 2, "pieces");
        addIngredient(db, 2, "egg", 1, "piece");

        addRecipe(
                db,
                3,
                "French Toast",
                "Dip bread in beaten egg and milk, then cook until golden."
        );
        addIngredient(db, 3, "bread", 2, "pieces");
        addIngredient(db, 3, "egg", 2, "pieces");
        addIngredient(db, 3, "milk", 100, "ml");

        addRecipe(
                db,
                4,
                "Apple Pancakes",
                "Mix flour, milk and egg, then add chopped apple and cook as pancakes."
        );
        addIngredient(db, 4, "flour", 1, "cup");
        addIngredient(db, 4, "milk", 100, "ml");
        addIngredient(db, 4, "egg", 1, "piece");
        addIngredient(db, 4, "apple", 1, "piece");

        addRecipe(
                db,
                5,
                "Banana Pancakes",
                "Mix flour, milk and egg with mashed banana and cook as pancakes."
        );
        addIngredient(db, 5, "flour", 1, "cup");
        addIngredient(db, 5, "milk", 100, "ml");
        addIngredient(db, 5, "egg", 1, "piece");
        addIngredient(db, 5, "banana", 1, "piece");

        addRecipe(
                db,
                6,
                "Apple Smoothie",
                "Blend apple, banana and milk until smooth."
        );
        addIngredient(db, 6, "apple", 1, "piece");
        addIngredient(db, 6, "banana", 1, "piece");
        addIngredient(db, 6, "milk", 200, "ml");

        addRecipe(
                db,
                7,
                "Cheese Omelette",
                "Beat the eggs with milk and cook with cheese until set."
        );
        addIngredient(db, 7, "egg", 2, "pieces");
        addIngredient(db, 7, "milk", 50, "ml");
        addIngredient(db, 7, "cheese", 50, "g");

        addRecipe(
                db,
                8,
                "Tomato Sandwich",
                "Place sliced tomato and cheese between two slices of bread."
        );
        addIngredient(db, 8, "bread", 2, "pieces");
        addIngredient(db, 8, "tomato", 1, "piece");
        addIngredient(db, 8, "cheese", 30, "g");

        addRecipe(
                db,
                9,
                "Egg Sandwich",
                "Cook the egg and place it with cheese between slices of bread."
        );
        addIngredient(db, 9, "bread", 2, "pieces");
        addIngredient(db, 9, "egg", 1, "piece");
        addIngredient(db, 9, "cheese", 30, "g");

        addRecipe(
                db,
                10,
                "Fruit Salad",
                "Chop the fruits and mix them together."
        );
        addIngredient(db, 10, "apple", 1, "piece");
        addIngredient(db, 10, "banana", 1, "piece");
        addIngredient(db, 10, "orange", 1, "piece");

        addRecipe(
                db,
                11,
                "Apple Porridge",
                "Cook oats with milk and top with sliced apple."
        );
        addIngredient(db, 11, "oats", 1, "cup");
        addIngredient(db, 11, "milk", 200, "ml");
        addIngredient(db, 11, "apple", 1, "piece");

        addRecipe(
                db,
                12,
                "Banana Oatmeal",
                "Cook oats with milk and add sliced banana."
        );
        addIngredient(db, 12, "oats", 1, "cup");
        addIngredient(db, 12, "milk", 200, "ml");
        addIngredient(db, 12, "banana", 1, "piece");

        addRecipe(
                db,
                13,
                "Vegetable Omelette",
                "Cook eggs with chopped onion, tomato and pepper."
        );
        addIngredient(db, 13, "egg", 2, "pieces");
        addIngredient(db, 13, "onion", 1, "piece");
        addIngredient(db, 13, "tomato", 1, "piece");
        addIngredient(db, 13, "pepper", 1, "piece");

        addRecipe(
                db,
                14,
                "Vegetable Fried Rice",
                "Stir-fry rice with egg, carrot and onion."
        );
        addIngredient(db, 14, "rice", 2, "cups");
        addIngredient(db, 14, "egg", 2, "pieces");
        addIngredient(db, 14, "carrot", 1, "piece");
        addIngredient(db, 14, "onion", 1, "piece");

        addRecipe(
                db,
                15,
                "Chicken Rice",
                "Cook rice with chicken and onion until the chicken is fully cooked."
        );
        addIngredient(db, 15, "rice", 2, "cups");
        addIngredient(db, 15, "chicken", 200, "g");
        addIngredient(db, 15, "onion", 1, "piece");

        addRecipe(
                db,
                16,
                "Grilled Cheese Toast",
                "Place cheese and butter on bread and grill until golden."
        );
        addIngredient(db, 16, "bread", 2, "pieces");
        addIngredient(db, 16, "cheese", 50, "g");
        addIngredient(db, 16, "butter", 10, "g");

        addRecipe(
                db,
                17,
                "Chicken Sandwich",
                "Place cooked chicken, tomato and cheese between slices of bread."
        );
        addIngredient(db, 17, "bread", 2, "pieces");
        addIngredient(db, 17, "chicken", 150, "g");
        addIngredient(db, 17, "tomato", 1, "piece");
        addIngredient(db, 17, "cheese", 30, "g");

        addRecipe(
                db,
                18,
                "Vegetable Rice",
                "Cook rice with carrot, onion and tomato."
        );
        addIngredient(db, 18, "rice", 2, "cups");
        addIngredient(db, 18, "carrot", 1, "piece");
        addIngredient(db, 18, "onion", 1, "piece");
        addIngredient(db, 18, "tomato", 1, "piece");
    }

    private void addRecipe(
            SQLiteDatabase db,
            int id,
            String name,
            String instructions) {

        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("name", name);
        values.put("instructions", instructions);

        db.insert("recipes", null, values);
    }

    private void addIngredient(
            SQLiteDatabase db,
            int recipeId,
            String ingredientName,
            double quantity,
            String unit) {

        ContentValues values = new ContentValues();

        values.put("recipe_id", recipeId);
        values.put("ingredient_name", ingredientName);
        values.put("quantity", quantity);
        values.put("unit", unit);

        db.insert("recipe_ingredients", null, values);
    }
}