package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddItem = findViewById(R.id.btnAddItem);
        Button btnViewItems = findViewById(R.id.btnViewItems);
        Button btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        Button btnSettings = findViewById(R.id.btnSettings);

        btnAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddItemActivity.class
            );
            startActivity(intent);
        });

        btnViewItems.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ViewItemsActivity.class
            );
            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );
            startActivity(intent);
        });
    }
}