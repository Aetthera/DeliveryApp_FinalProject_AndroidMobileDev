package com.example.finalproject;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    TextInputEditText editTextEmail, editTextPassword, editTextFname, editTextLname, editTextNumber;
    Button buttonReg;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Client_App.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_regis);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        editTextFname = findViewById(R.id.f_name);
        editTextLname = findViewById(R.id.l_name);
        editTextNumber = findViewById(R.id.pnumber);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String fname = String.valueOf(editTextFname.getText()).trim();
                String lname = String.valueOf(editTextLname.getText()).trim();
                String number = String.valueOf(editTextNumber.getText()).trim();
                String email = String.valueOf(editTextEmail.getText()).trim();
                String password = String.valueOf(editTextPassword.getText()).trim();

                // Flag to track if all inputs are valid
                boolean isValid = true;

                // Clear existing errors
                editTextFname.setError(null);
                editTextLname.setError(null);
                editTextNumber.setError(null);
                editTextEmail.setError(null);
                editTextPassword.setError(null);

                // Validate fields
                if (TextUtils.isEmpty(fname)) {
                    editTextFname.setError("First name is required");
                    isValid = false;
                }

                if (TextUtils.isEmpty(lname)) {
                    editTextLname.setError("Last name is required");
                    isValid = false;
                }

                if (TextUtils.isEmpty(number)) {
                    editTextNumber.setError("Phone number is required");
                    isValid = false;
                } else if (!number.matches("\\d{7,15}")) { // Basic phone number validation
                    editTextNumber.setError("Enter a valid phone number (no spaces or dashes)");
                    isValid = false;
                }

                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is required");
                    isValid = false;
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Validate email format
                    editTextEmail.setError("Enter a valid email address");
                    isValid = false;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is required");
                    isValid = false;
                } else if (password.length() < 6) { // Ensure password has at least 6 characters
                    editTextPassword.setError("Password must be at least 6 characters");
                    isValid = false;
                }

                if (!isValid) {
                    progressBar.setVisibility(View.GONE); // Stop showing progress bar if input is invalid
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Client_Regis.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();
                                    DocumentReference df = mStore.collection("Users").document(user.getUid());
                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("UserFname", editTextFname.getText().toString());
                                    userInfo.put("UserLname", editTextLname.getText().toString());
                                    userInfo.put("UserPhone", editTextNumber.getText().toString());
                                    userInfo.put("UserEmail", editTextEmail.getText().toString());
                                    userInfo.put("IsUser", 1);

                                    df.set(userInfo);
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Client_Regis.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
