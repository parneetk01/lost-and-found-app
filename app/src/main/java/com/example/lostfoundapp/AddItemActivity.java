package com.example.lostfoundapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {
    RadioButton lostBtn, foundBtn;
    EditText itemName, descField, dateField, locationField, nameField, mobileField;
    Spinner imageSpinner;
    Button save;
    DBHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        lostBtn = findViewById(R.id.lostBtn);
        foundBtn = findViewById(R.id.foundBtn);
        itemName = findViewById(R.id.item);
        descField = findViewById(R.id.desc);
        dateField = findViewById(R.id.date);
        locationField = findViewById(R.id.location);
        nameField = findViewById(R.id.name);
        mobileField = findViewById(R.id.mobile);
        imageSpinner = findViewById(R.id.imageSpinner);
        save = findViewById(R.id.save);

        db = new DBHelper(this);

        // DATE PICKER
        dateField.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();

            DatePickerDialog dp = new DatePickerDialog(this,
                    (view, y, m, d) -> dateField.setText(d + "/" + (m + 1) + "/" + y),
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            );
            dp.getDatePicker().setMaxDate(System.currentTimeMillis());
            dp.show();
        });

        String[] images = {"keys", "women_wallet", "specs","headset", "scarf", "bottle","smart_watch"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, images);
        imageSpinner.setAdapter(adapter);

        // SAVE
        save.setOnClickListener(v -> {
            String type = lostBtn.isChecked() ? "Lost" :
                    foundBtn.isChecked() ? "Found" : "";

            String item_name = itemName.getText().toString().trim();
            String desc = descField.getText().toString().trim();
            String date = dateField.getText().toString().trim();
            String location = locationField.getText().toString().trim();
            String name = nameField.getText().toString().trim();
            String mobile = mobileField.getText().toString().trim();

            if (date.isEmpty()) date = "NA";
            if (location.isEmpty()) location = "NA";

            if (type.isEmpty()) {
                Toast.makeText(this, "Select Post Type", Toast.LENGTH_SHORT).show();
                return;
            }

            if (item_name.isEmpty()) {
                itemName.setError("Required");
                itemName.requestFocus();
                return;
            }

            if (desc.isEmpty()) {
                descField.setError("Required");
                descField.requestFocus();
                return;
            }

            if (name.isEmpty()) {
                nameField.setError("Required");
                nameField.requestFocus();
                return;
            }

            if (!mobile.matches("[0-9]{10}")) {
                mobileField.setError("Invalid number");
                return;
            }

            String imageName = imageSpinner.getSelectedItem().toString();
            boolean result = db.insertItem(type, item_name, desc, date, location, name, mobile, imageName);
            if (result) finish();
        });
    }
}