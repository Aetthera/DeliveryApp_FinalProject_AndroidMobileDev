package com.example.deliveryapp_finalproject_androidmobiledev;

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
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Client_App.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
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
                String email, password, fname, lname, number;
                fname = String.valueOf((editTextFname).getText());
                lname = String.valueOf((editTextLname).getText());
                number = String.valueOf((editTextNumber).getText());
                email = String.valueOf((editTextEmail).getText());
                password = String.valueOf((editTextPassword).getText());

                if(TextUtils.isEmpty(fname)){
                    Toast.makeText(Client_Regis.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(lname)){
                    Toast.makeText(Client_Regis.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(number)){
                    Toast.makeText(Client_Regis.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Client_Regis.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Client_Regis.this, "Enter Password", Toast.LENGTH_SHORT).show();
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
                                    userInfo.put("UserFname",editTextFname.getText().toString());
                                    userInfo.put("UserLname",editTextLname.getText().toString());
                                    userInfo.put("UserPhone",editTextNumber.getText().toString());
                                    userInfo.put("UserEmail",editTextEmail.getText().toString());
                                    userInfo.put("UserPassword", editTextPassword.getText().toString());
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
