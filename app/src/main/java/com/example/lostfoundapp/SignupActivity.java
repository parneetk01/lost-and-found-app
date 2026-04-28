package com.example.lostfoundapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText name, email, phone, password;
    Button signup;
    DBHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.e1);
        email = findViewById(R.id.e2);
        phone = findViewById(R.id.e3);
        password = findViewById(R.id.e4);
        signup = findViewById(R.id.b1);

        db = new DBHelper(this);

        signup.setOnClickListener(v -> {

            String nameStr = name.getText().toString().trim();
            String emailStr = email.getText().toString().trim();
            String phoneStr = phone.getText().toString().trim();
            String passStr = password.getText().toString().trim();

            if (!nameStr.matches("[a-zA-Z ]+")) {
                name.setError("Name should contain only alphabets");
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                email.setError("Enter valid email (example@gmail.com)");
                return;
            }

            if (!phoneStr.matches("[0-9]{10}")) {
                phone.setError("Enter valid 10-digit mobile number");
                return;
            }

            if (passStr.length() < 8) {
                password.setError("Password must be at least 8 characters");
                return;
            }

            if (db.checkEmailExists(emailStr)) {
                email.setError("Email already registered");
                return;
            }

            // If all valid then save user
            if (db.registerUser(nameStr, emailStr, phoneStr, passStr)) {
                Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, StartActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}