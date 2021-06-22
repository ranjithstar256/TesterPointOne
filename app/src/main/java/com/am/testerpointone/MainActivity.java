package com.am.testerpointone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextTextPersonName);
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()){

        }else {
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            //Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        }

    }

    public void ok(View view) {
        String number = editText.getText().toString().trim();
        if (number.isEmpty() || number.length() < 10) {
            editText.setError("Valid number is required");
            editText.requestFocus();
            return;
        }
        String phoneNumber = "+" + "91" + number;
        Intent intent = new Intent(MainActivity.this,OTPscreen.class);
        intent.putExtra("phonenumber", phoneNumber);
        startActivity(intent);
        finish();


    }
}