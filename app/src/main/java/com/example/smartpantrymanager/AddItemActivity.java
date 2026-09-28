package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText etItemName;
    private EditText etCategory;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etItemName = findViewById(R.id.etItemName);
        etCategory = findViewById(R.id.etCategory);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        Button btnSaveItem = findViewById(R.id.btnSaveItem);
        Button btnCancel = findViewById(R.id.btnCancel);

        databaseHelper = new DatabaseHelper(this);

        btnSaveItem.setOnClickListener(v -> saveItem());

        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveItem() {

        String name = etItemName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String quantityText = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {
            etItemName.setError("Enter ingredient name");
            etItemName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            etQuantity.setError("Enter quantity");
            etQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            etUnit.setError("Enter unit");
            etUnit.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);

            if (quantity <= 0) {
                etQuantity.setError("Quantity must be greater than 0");
                etQuantity.requestFocus();
                return;
            }

        } catch (NumberFormatException e) {
            etQuantity.setError("Enter a valid quantity");
            etQuantity.requestFocus();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("category", category);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        long result = db.insert("pantry_items", null, values);

        db.close();

        if (result != -1) {
            Toast.makeText(this, "Pantry item saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save pantry item", Toast.LENGTH_SHORT).show();
        }
    }
}