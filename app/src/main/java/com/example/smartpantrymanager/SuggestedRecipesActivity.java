package com.example.smartpantrymanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecipes;
    private TextView tvRecipeMessage;
    private DatabaseHelper databaseHelper;

    private List<PantryItem> pantryItems;
    private List<Recipe> matchingRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        tvRecipeMessage = findViewById(R.id.tvRecipeMessage);
        Button btnBackRecipes = findViewById(R.id.btnBackRecipes);

        databaseHelper = new DatabaseHelper(this);

        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        btnBackRecipes.setOnClickListener(v -> finish());

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        pantryItems = loadPantryItemsFromDatabase();

        RecipeDAO recipeDAO = new RecipeDAO(this);

        matchingRecipes = recipeDAO.getMatchingRecipes(pantryItems);

        RecipeAdapter recipeAdapter =
                new RecipeAdapter(matchingRecipes);

        recyclerViewRecipes.setAdapter(recipeAdapter);

        if (matchingRecipes.isEmpty()) {

            tvRecipeMessage.setText(
                    "No recipes available. Add more ingredients to your pantry."
            );

        } else {

            tvRecipeMessage.setText(
                    "These recipes use only ingredients currently in your pantry."
            );
        }
    }

    private List<PantryItem> loadPantryItemsFromDatabase() {

        List<PantryItem> items = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "pantry_items",
                null,
                null,
                null,
                null,
                null,
                "name ASC"
        );

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
            );

            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow("category")
            );

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("quantity")
            );

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow("unit")
            );

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("expiry_date")
            );

            PantryItem item = new PantryItem(
                    id,
                    name,
                    category,
                    quantity,
                    unit,
                    expiryDate
            );

            items.add(item);
        }

        cursor.close();
        db.close();

        return items;
    }
}