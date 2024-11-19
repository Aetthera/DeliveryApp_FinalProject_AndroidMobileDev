package com.example.deliveryappYijia;

import android.content.Intent;
import android.os.Bundle;

import com.example.deliveryapp_finalproject_androidmobiledev.R;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

//import com.example.deliveryapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button driverButton = findViewById(R.id.btnDriver);
        Button clientButton = findViewById(R.id.btnClient);


        driverButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, DriverCheckLogin.class);
            startActivity(intent);
        });

        clientButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ClientCheckLogin.class);
            startActivity(intent);
        });
    }
}