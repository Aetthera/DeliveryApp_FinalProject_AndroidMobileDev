package com.example.deliveryapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.deliveryapp_finalproject_androidmobiledev.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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