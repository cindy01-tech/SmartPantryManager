package com.example.smartpantrymanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItems;
    private DatabaseHelper databaseHelper;
    private PantryAdapter pantryAdapter;
    private List<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        Button btnBack = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);
        pantryItems = new ArrayList<>();

        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));

        loadPantryItems();

        btnBack.setOnClickListener(v -> finish());
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (pantryItems != null) {
            loadPantryItems();
        }
    }

    private void loadPantryItems() {

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

        pantryItems.clear();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
            String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiry_date"));

            PantryItem item = new PantryItem(
                    id,
                    name,
                    category,
                    quantity,
                    unit,
                    expiryDate
            );

            pantryItems.add(item);
        }

        cursor.close();
        db.close();

        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerViewItems.setAdapter(pantryAdapter);

        if (pantryItems.isEmpty()) {
            Toast.makeText(
                    this,
                    "No pantry items found",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}