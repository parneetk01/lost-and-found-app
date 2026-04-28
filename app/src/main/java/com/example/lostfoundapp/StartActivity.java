package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // LOGIN
        findViewById(R.id.lc).setOnClickListener(v ->
                startActivity(new Intent(StartActivity.this, LoginActivity.class))
        );

        // SIGNUP
        findViewById(R.id.sc).setOnClickListener(v ->
                startActivity(new Intent(StartActivity.this, SignupActivity.class))
        );
        findViewById(R.id.lc).setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
            finish();
        });

        findViewById(R.id.sc).setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, SignupActivity.class));
            finish();
        });
    }
}