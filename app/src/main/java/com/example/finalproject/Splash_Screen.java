package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash_Screen.this, Login.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}