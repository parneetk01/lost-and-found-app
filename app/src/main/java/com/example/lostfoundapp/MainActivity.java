package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView add, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        view = findViewById(R.id.view);

        add.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddItemActivity.class)));

        view.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewItemsActivity.class)));
    }
}