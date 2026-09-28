package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditItemName;
    private EditText etEditCategory;
    private EditText etEditQuantity;
    private EditText etEditUnit;
    private EditText etEditExpiryDate;

    private DatabaseHelper databaseHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditItemName = findViewById(R.id.etEditItemName);
        etEditCategory = findViewById(R.id.etEditCategory);
        etEditQuantity = findViewById(R.id.etEditQuantity);
        etEditUnit = findViewById(R.id.etEditUnit);
        etEditExpiryDate = findViewById(R.id.etEditExpiryDate);

        Button btnUpdateItem = findViewById(R.id.btnUpdateItem);
        Button btnCancelEdit = findViewById(R.id.btnCancelEdit);

        databaseHelper = new DatabaseHelper(this);

        itemId = getIntent().getIntExtra("item_id", -1);

        if (itemId == -1) {
            Toast.makeText(this, "Invalid pantry item", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadItem();

        btnUpdateItem.setOnClickListener(v -> updateItem());

        btnCancelEdit.setOnClickListener(v -> finish());
    }

    private void loadItem() {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        android.database.Cursor cursor = db.query(
                "pantry_items",
                null,
                "id = ?",
                new String[]{String.valueOf(itemId)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {

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

            etEditItemName.setText(name);
            etEditCategory.setText(category);
            etEditQuantity.setText(String.valueOf(quantity));
            etEditUnit.setText(unit);

            if (expiryDate != null) {
                etEditExpiryDate.setText(expiryDate);
            }
        }

        cursor.close();
        db.close();
    }

    private void updateItem() {

        String name = etEditItemName.getText().toString().trim();
        String category = etEditCategory.getText().toString().trim();
        String quantityText = etEditQuantity.getText().toString().trim();
        String unit = etEditUnit.getText().toString().trim();
        String expiryDate = etEditExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {
            etEditItemName.setError("Enter ingredient name");
            etEditItemName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            etEditQuantity.setError("Enter quantity");
            etEditQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            etEditUnit.setError("Enter unit");
            etEditUnit.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);

            if (quantity <= 0) {
                etEditQuantity.setError("Quantity must be greater than 0");
                etEditQuantity.requestFocus();
                return;
            }

        } catch (NumberFormatException e) {
            etEditQuantity.setError("Enter a valid quantity");
            etEditQuantity.requestFocus();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("category", category);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        int rowsUpdated = db.update(
                "pantry_items",
                values,
                "id = ?",
                new String[]{String.valueOf(itemId)}
        );

        db.close();

        if (rowsUpdated > 0) {
            Toast.makeText(
                    this,
                    "Pantry item updated",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "Failed to update pantry item",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}