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

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Forgot_Pass extends AppCompatActivity {

    private TextInputEditText emailField;
    private Button recoverButton;
    private TextView recoverText;
    private ProgressBar progressBar;
    TextView textView;

    private FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailField = findViewById(R.id.email);
        recoverButton = findViewById(R.id.btn_recover);
        recoverText = findViewById(R.id.recover_text);
        progressBar = findViewById(R.id.progressBar);
        mStore = FirebaseFirestore.getInstance();
        textView = findViewById(R.id.loginNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        recoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Forgot_Pass.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mStore.collection("Users")
                        .whereEqualTo("UserEmail", email)
                        .get()
                        .addOnCompleteListener(task -> {
                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    String password = document.getString("UserPassword");
                                    recoverText.setText(password != null ? "Password: " + password : "Password not found");
                                }
                            } else {
                                recoverText.setText("Email not registered");
                            }
                        })
                        .addOnFailureListener(e -> {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Forgot_Pass.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}
