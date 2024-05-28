# Mobile Security Example Application

## Overview

This Android application demonstrates a secure login mechanism that requires multiple conditions to be met before allowing the user to proceed. The conditions include checking WiFi and NFC status, verifying battery level, ensuring screen brightness is above a threshold, and matching the battery level with the first two digits of the entered password.

## Features

1. **WiFi Status Check:** Ensures that WiFi is enabled.
2. **NFC Status Check:** Ensures that NFC is enabled.
3. **Battery Level Verification:** Compares the battery level with the first two digits of the entered password.
4. **Screen Brightness Check:** Ensures that the screen brightness level is greater than 50%.
5. **Time Range Verification:** Allows login only if the current time is between 10:00 AM and 10:00 PM.

## Project Structure

### MainActivity.java

The main activity of the application. It handles the initialization of views and system service managers, setting up listeners, and verifying login conditions.

#### Key Methods

- `onCreate(Bundle savedInstanceState)`: Initializes the activity, binds views, sets listeners, and initializes system service managers.
- `initialManagers()`: Initializes the WiFi, NFC, and Battery managers.
- `findViews()`: Binds the views from the layout.
- `setListeners()`: Sets the onClick listener for the submit button.
- `verifyLoginAndNavigate()`: Verifies the login conditions and navigates to the next activity if all conditions are met.
- `verifyFirstTwoCharsAreDigits(String password)`: Checks if the first two characters of the password are digits.
- `checkWifiStatus()`: Checks if WiFi is enabled.
- `verifyTimeInRange()`: Verifies if the current time is within the specified range.
- `checkNfcStatus()`: Checks if NFC is enabled.
- `verifyBatteryLevelMatch()`: Verifies if the first two characters of the password match the current battery level.
- `verifyBrightnessLevel()`: Verifies if the screen brightness level is greater than 50%.
- `verifyPasswordNotEmpty()`: Checks if the password input is not empty.
- `verifyConditions()`: Verifies all the required conditions for login.
