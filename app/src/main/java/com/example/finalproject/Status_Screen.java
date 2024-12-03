package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Status_Screen extends AppCompatActivity {

    Button btn_drv;
    Button btn_usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_screen);

        btn_drv = findViewById(R.id.btn_driver);
        btn_usr = findViewById(R.id.btn_user);

        btn_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Client_Regis.class);
                startActivity(intent);
                finish();
            }
        });

        btn_drv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Driver_Regis.class);
                startActivity(intent);
                finish();
            }
        });
    }
}