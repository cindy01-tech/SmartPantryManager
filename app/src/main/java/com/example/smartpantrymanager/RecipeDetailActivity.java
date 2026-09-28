package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView tvDetailRecipeName;
    private TextView tvDetailIngredients;
    private TextView tvDetailInstructions;
    private Button btnBackRecipeDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        tvDetailRecipeName = findViewById(R.id.tvDetailRecipeName);
        tvDetailIngredients = findViewById(R.id.tvDetailIngredients);
        tvDetailInstructions = findViewById(R.id.tvDetailInstructions);
        btnBackRecipeDetail = findViewById(R.id.btnBackRecipeDetail);

        String recipeName = getIntent().getStringExtra("recipe_name");
        String ingredients = getIntent().getStringExtra("recipe_ingredients");
        String instructions = getIntent().getStringExtra("recipe_instructions");

        if (recipeName != null) {
            tvDetailRecipeName.setText(recipeName);
        }

        if (ingredients != null) {
            tvDetailIngredients.setText(ingredients);
        }

        if (instructions != null) {
            tvDetailInstructions.setText(instructions);
        }

        btnBackRecipeDetail.setOnClickListener(v -> finish());
    }
}