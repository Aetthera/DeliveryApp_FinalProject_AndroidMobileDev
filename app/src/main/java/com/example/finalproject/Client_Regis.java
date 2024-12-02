package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Client_Regis extends AppCompatActivity {

    // UI Components
    private TextInputEditText editTextEmail, editTextPassword, editTextFname, editTextLname, editTextNumber;
    private Button buttonReg;
    private ProgressBar progressBar;
    private TextView textViewLoginNow;

    // Firebase Instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_regis);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        // Bind UI Components
        editTextFname = findViewById(R.id.f_name);
        editTextLname = findViewById(R.id.l_name);
        editTextNumber = findViewById(R.id.pnumber);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textViewLoginNow = findViewById(R.id.loginNow);

        // Set up "Login Now" Click Listener
        textViewLoginNow.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        // Set up "Register" Button Click Listener
        buttonReg.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        // Show ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Retrieve user inputs
        String fname = editTextFname.getText().toString().trim();
        String lname = editTextLname.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate user inputs
        if (TextUtils.isEmpty(fname)) {
            showError("Enter First Name");
            return;
        }

        if (TextUtils.isEmpty(lname)) {
            showError("Enter Last Name");
            return;
        }

        if (TextUtils.isEmpty(number)) {
            showError("Enter Phone Number");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            showError("Enter Email");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Enter a valid Email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showError("Enter Password");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters long");
            return;
        }

        // Create user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Store additional user information in Firestore
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                storeUserInfo(user.getUid(), fname, lname, number, email, password);
                            }
                        } else {
                            Toast.makeText(Client_Regis.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void storeUserInfo(String userId, String fname, String lname, String number, String email, String password) {
        DocumentReference documentReference = mStore.collection("Users").document(userId);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("UserFname", fname);
        userInfo.put("UserLname", lname);
        userInfo.put("UserPhone", number);
        userInfo.put("UserEmail", email);
        userInfo.put("UserPassword", password);
        userInfo.put("IsUser", 1);

        documentReference.set(userInfo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Client_Regis.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        // Navigate to Login Activity
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Client_Regis.this, "Failed to save user info", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showError(String message) {
        Toast.makeText(Client_Regis.this, message, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
}
