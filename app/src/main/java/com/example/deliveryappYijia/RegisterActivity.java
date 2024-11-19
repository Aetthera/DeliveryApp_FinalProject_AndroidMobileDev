package com.example.deliveryappYijia;

import android.content.Intent;
import android.os.Bundle;

import com.example.deliveryappYijia.User;
import com.example.deliveryapp_finalproject_androidmobiledev.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference database;
    EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        editText1 =  findViewById(R.id.editTextUserName);
        editText2 =  findViewById(R.id.editTextPassword);
        Button reggisterButton = findViewById(R.id.btnRegister);
        database = FirebaseDatabase.getInstance().getReference("products");


        reggisterButton.setOnClickListener(v -> addUser());


    }

    private void addUser() {
        String UserName =  editText1.getText().toString().trim();

        String Password = editText2.getText().toString().trim();

        if (TextUtils.isEmpty(UserName) || TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "All the field need to be fill", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = database.push().getKey();


        User user = new User(id, UserName, Password);

        if (id!= null) {
            database.child(id).setValue(user);
            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();

            editText1.setText("");
            editText2.setText("");

        }


    }
}