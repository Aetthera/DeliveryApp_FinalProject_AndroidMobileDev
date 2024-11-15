package com.example.deliveryapp_finalproject_androidmobiledev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button =  findViewById(R.id.btnTips);
        // Button to navigate to TipsActivity
       button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TipsActivity.class);
            startActivity(intent);
        });
    }
}
