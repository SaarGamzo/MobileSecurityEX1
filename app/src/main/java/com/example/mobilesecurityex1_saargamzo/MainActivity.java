package com.example.mobilesecurityex1_saargamzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.net.wifi.WifiManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView missingConditions;
    private Button submitBtn;
    private EditText passwordInputText;
    private String passwordAsString;
    private WifiManager wifiManager;
    private NfcManager nfcManager;
    private BatteryManager batteryManager;

    /**
     * Called when the activity is first created.
     * Initializes the views and managers.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        missingConditions.setVisibility(View.GONE);
        setListeners();
        initialManagers();
    }

    /**
     * Initializes the necessary system service managers for WiFi, NFC, and battery.
     */
    private void initialManagers() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        nfcManager = (NfcManager) getSystemService(Context.NFC_SERVICE);
        batteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
    }

    /**
     * Finds and binds the views from the layout.
     */
    private void findViews() {
        missingConditions = findViewById(R.id.TXT_missingStuff);
        submitBtn = findViewById(R.id.BTN_submitpassword);
        passwordInputText = findViewById(R.id.INPUT_password);
    }

    /**
     * Sets listeners for the UI elements.
     */
    private void setListeners() {
        submitBtn.setOnClickListener(view -> verifyLoginAndNavigate());
    }

    /**
     * Verifies the login conditions and navigates to the next activity if all conditions are met.
     */
    private void verifyLoginAndNavigate() {
        passwordAsString = String.valueOf(passwordInputText.getText());
        if(!verifyPasswordNotEmpty()){ // verify password is not empty
            missingConditions.setVisibility(View.VISIBLE);
            missingConditions.setText("Password must not be empty!");
            return;
        }
        if (!verifyFirstTwoCharsAreDigits(passwordAsString)) { // verify password starts with 2 digits
            missingConditions.setVisibility(View.VISIBLE);
            missingConditions.setText("Password must start with 2 digits!");
            return;
        }
        if (verifyConditions()) { // verify all other conditions
            Intent intent = new Intent(this, LoggedInActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Verifies that the first two characters of the password are digits.
     * @param password The password to verify.
     * @return true if the first two characters are digits, false otherwise.
     */
    private boolean verifyFirstTwoCharsAreDigits(String password) {
        if (password.length() < 2) {
            return false;
        }
        String firstTwoChars = password.substring(0, 2);
        return firstTwoChars.matches("\\d\\d");
    }

    /**
     * Checks if WiFi is enabled.
     * @return true if WiFi is enabled, false otherwise.
     */
    private boolean checkWifiStatus() {
        return wifiManager.isWifiEnabled();
    }

    /**
     * Verifies if the current time is within the specified range.
     * @return true if the current time is between 10:00 and 21:00, false otherwise.
     */
    private boolean verifyTimeInRange() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int minHour = 10;
        int maxHour = 21;
        return currentHour >= minHour && currentHour < maxHour;
    }

    /**
     * Checks if NFC is enabled.
     * @return true if NFC is enabled, false otherwise.
     */
    private boolean checkNfcStatus() {
        return nfcManager.getDefaultAdapter() != null && nfcManager.getDefaultAdapter().isEnabled();
    }

    /**
     * Verifies if the first two characters of the password match the current battery level.
     * @return true if the first two characters of the password match the battery level, false otherwise.
     */
    private boolean verifyBatteryLevelMatch() {
        int batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        int enteredBatteryLevel = Integer.parseInt(passwordAsString.substring(0, 2));
        return enteredBatteryLevel == batteryLevel;
    }

    /**
     * Verifies if the screen brightness level is greater than 50%.
     * @return true if the brightness level is greater than 50%, false otherwise.
     */
    private boolean verifyBrightnessLevel() {
        int currentBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
        return currentBrightness > 131; // 131 is approximately 50% of the brightness scale (0-255)
    }

    /**
     * Verifies if the password input is not empty.
     * @return true if the password is not empty, false otherwise.
     */
    private boolean verifyPasswordNotEmpty() {
        return !passwordInputText.getText().toString().isEmpty();
    }

    /**
     * Verifies all the required conditions for login.
     * @return true if all conditions are met, false otherwise.
     */
    private boolean verifyConditions() {
        StringBuilder missingText = new StringBuilder();
        boolean result = true;
        if (!checkWifiStatus()) {
            missingText.append("# WiFi is not enabled!\n");
            result = false;
        }
        if (!checkNfcStatus()) {
            missingText.append("# NFC is not enabled!\n");
            result = false;
        }
        if (!verifyBatteryLevelMatch()) {
            missingText.append("# Battery level does not match first 2 chars on password!\n");
            result = false;
        }
        if (!verifyBrightnessLevel()) {
            missingText.append("# Brightness is not greater than 50%!\n");
            result = false;
        }
        if (!verifyTimeInRange()) {
            missingText.append("# Hour is not between 10-21!\n");
            result = false;
        }
        if (!result) {
            missingConditions.setVisibility(View.VISIBLE);
        }
        missingConditions.setText("Missing conditions:\n\n" + missingText);
        return result;
    }
}
