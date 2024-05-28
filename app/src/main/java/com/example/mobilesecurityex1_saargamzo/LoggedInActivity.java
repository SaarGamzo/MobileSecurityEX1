package com.example.mobilesecurityex1_saargamzo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoggedInActivity extends AppCompatActivity {
    private Button backBTN;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_activity);
        findViews();
        setListeners();
    }

    private void findViews() {
        backBTN = findViewById(R.id.BTN_back);
    }

    private void setListeners(){
        backBTN.setOnClickListener(view -> navigateToMainActivirt());
    }

    private void navigateToMainActivirt() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
