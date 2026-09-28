package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryAlerts;
    private Switch switchUnits;
    private Button btnBackSettings;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        switchUnits = findViewById(R.id.switchUnits);
        btnBackSettings = findViewById(R.id.btnBackSettings);

        preferences = getSharedPreferences(
                "SmartPantrySettings",
                MODE_PRIVATE
        );

        boolean expiryAlerts = preferences.getBoolean(
                "expiry_alerts",
                false
        );

        boolean useImperialUnits = preferences.getBoolean(
                "imperial_units",
                false
        );

        switchExpiryAlerts.setChecked(expiryAlerts);
        switchUnits.setChecked(useImperialUnits);

        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences.edit()
                            .putBoolean("expiry_alerts", isChecked)
                            .apply();
                }
        );

        switchUnits.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences.edit()
                            .putBoolean("imperial_units", isChecked)
                            .apply();
                }
        );

        btnBackSettings.setOnClickListener(v -> finish());
    }
}