package com.example.deliveryappYijia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.deliveryapp_finalproject_androidmobiledev.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Button;
import android.widget.EditText;


public class DriverCheckLogin extends AppCompatActivity {

    DatabaseReference database;
    EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        editText1 =  findViewById(R.id.editTextUserName);
        editText2 =  findViewById(R.id.editTextPassword);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button checkButton = findViewById(R.id.btnCheck);
        database = FirebaseDatabase.getInstance().getReference("users");


        checkButton.setOnClickListener(v -> checkUser());


    }
    private void checkUser() {

    }
}