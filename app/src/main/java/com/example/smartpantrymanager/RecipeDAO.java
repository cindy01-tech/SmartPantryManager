package com.example.smartpantrymanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeDAO {

    private final DatabaseHelper databaseHelper;

    private static class IngredientRequirement {

        String ingredient;
        double quantity;
        String unit;

        IngredientRequirement(String ingredient, double quantity, String unit) {
            this.ingredient = ingredient;
            this.quantity = quantity;
            this.unit = unit;
        }
    }

    public RecipeDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, name, instructions FROM recipes ORDER BY id",
                null
        );

        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow("instructions")
                );

                List<String> ingredients = new ArrayList<>();

                Cursor ingredientCursor = db.rawQuery(
                        "SELECT ingredient_name " +
                                "FROM recipe_ingredients " +
                                "WHERE recipe_id = ? " +
                                "ORDER BY id",
                        new String[]{String.valueOf(id)}
                );

                try {
                    while (ingredientCursor.moveToNext()) {

                        String ingredient = ingredientCursor.getString(
                                ingredientCursor.getColumnIndexOrThrow(
                                        "ingredient_name"
                                )
                        );

                        ingredients.add(ingredient);
                    }
                } finally {
                    ingredientCursor.close();
                }

                recipes.add(new Recipe(
                        id,
                        name,
                        instructions,
                        ingredients
                ));
            }
        } finally {
            cursor.close();
        }

        return recipes;
    }

    public List<Recipe> getMatchingRecipes(List<PantryItem> pantryItems) {

        List<Recipe> matchingRecipes = new ArrayList<>();

        Map<String, List<PantryItem>> pantryMap = new HashMap<>();

        for (PantryItem item : pantryItems) {

            String ingredient = normaliseIngredient(item.getName());

            if (!pantryMap.containsKey(ingredient)) {
                pantryMap.put(ingredient, new ArrayList<>());
            }

            pantryMap.get(ingredient).add(item);
        }

        for (Recipe recipe : getAllRecipes()) {

            boolean allIngredientsAvailable = true;

            List<IngredientRequirement> requirements =
                    getRequirementsForRecipe(recipe.getId());

            for (IngredientRequirement requirement : requirements) {

                String requiredIngredient =
                        normaliseIngredient(requirement.ingredient);

                List<PantryItem> availableItems =
                        pantryMap.get(requiredIngredient);

                if (availableItems == null || availableItems.isEmpty()) {
                    allIngredientsAvailable = false;
                    break;
                }

                boolean quantityAvailable = false;

                for (PantryItem pantryItem : availableItems) {

                    if (hasSufficientQuantity(
                            pantryItem,
                            requirement.quantity,
                            requirement.unit)) {

                        quantityAvailable = true;
                        break;
                    }
                }

                if (!quantityAvailable) {
                    allIngredientsAvailable = false;
                    break;
                }
            }

            if (allIngredientsAvailable) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    private List<IngredientRequirement> getRequirementsForRecipe(int recipeId) {

        List<IngredientRequirement> requirements = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT ingredient_name, quantity, unit " +
                        "FROM recipe_ingredients " +
                        "WHERE recipe_id = ? " +
                        "ORDER BY id",
                new String[]{String.valueOf(recipeId)}
        );

        try {
            while (cursor.moveToNext()) {

                String ingredient = cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredient_name")
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                );

                requirements.add(
                        new IngredientRequirement(
                                ingredient,
                                quantity,
                                unit
                        )
                );
            }
        } finally {
            cursor.close();
        }

        return requirements;
    }

    private boolean hasSufficientQuantity(
            PantryItem pantryItem,
            double requiredQuantity,
            String requiredUnit) {

        String pantryUnit = normaliseUnit(pantryItem.getUnit());
        String recipeUnit = normaliseUnit(requiredUnit);

        if (isPieceUnit(pantryUnit) && isPieceUnit(recipeUnit)) {
            return pantryItem.getQuantity() >= requiredQuantity;
        }

        if (isWeightUnit(pantryUnit) && isWeightUnit(recipeUnit)) {

            double pantryGrams =
                    convertToGrams(pantryItem.getQuantity(), pantryUnit);

            double requiredGrams =
                    convertToGrams(requiredQuantity, recipeUnit);

            return pantryGrams >= requiredGrams;
        }

        if (isVolumeUnit(pantryUnit) && isVolumeUnit(recipeUnit)) {

            double pantryMillilitres =
                    convertToMillilitres(
                            pantryItem.getQuantity(),
                            pantryUnit
                    );

            double requiredMillilitres =
                    convertToMillilitres(
                            requiredQuantity,
                            recipeUnit
                    );

            return pantryMillilitres >= requiredMillilitres;
        }

        return pantryUnit.equals(recipeUnit)
                && pantryItem.getQuantity() >= requiredQuantity;
    }

    private String normaliseUnit(String unit) {

        String value = unit.trim().toLowerCase();

        if (value.equals("piece")
                || value.equals("pieces")
                || value.equals("pc")
                || value.equals("pcs")
                || value.equals("item")
                || value.equals("items")) {
            return "piece";
        }

        if (value.equals("gram")
                || value.equals("grams")
                || value.equals("g")) {
            return "g";
        }

        if (value.equals("kilogram")
                || value.equals("kilograms")
                || value.equals("kg")) {
            return "kg";
        }

        if (value.equals("millilitre")
                || value.equals("millilitres")
                || value.equals("milliliter")
                || value.equals("milliliters")
                || value.equals("ml")) {
            return "ml";
        }

        if (value.equals("litre")
                || value.equals("litres")
                || value.equals("liter")
                || value.equals("liters")
                || value.equals("l")) {
            return "l";
        }

        if (value.equals("cup")
                || value.equals("cups")) {
            return "cup";
        }

        return value;
    }

    private boolean isPieceUnit(String unit) {
        return unit.equals("piece");
    }

    private boolean isWeightUnit(String unit) {
        return unit.equals("g") || unit.equals("kg");
    }

    private boolean isVolumeUnit(String unit) {
        return unit.equals("ml")
                || unit.equals("l")
                || unit.equals("cup");
    }

    private double convertToGrams(double quantity, String unit) {

        if (unit.equals("kg")) {
            return quantity * 1000;
        }

        return quantity;
    }

    private double convertToMillilitres(double quantity, String unit) {

        if (unit.equals("l")) {
            return quantity * 1000;
        }

        if (unit.equals("cup")) {
            return quantity * 240;
        }

        return quantity;
    }

    private String normaliseIngredient(String ingredient) {

        String value = ingredient.trim().toLowerCase();

        if (value.endsWith("ies") && value.length() > 3) {
            value = value.substring(0, value.length() - 3) + "y";
        } else if (value.endsWith("s")
                && !value.endsWith("ss")
                && value.length() > 2) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }

}
