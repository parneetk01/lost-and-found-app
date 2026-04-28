package com.example.lostfoundapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewItemsActivity extends AppCompatActivity {

    ListView listView;
    DBHelper db;

    ArrayList<String> data;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        listView = findViewById(R.id.listView);
        db = new DBHelper(this);

        data = new ArrayList<>();
        images = new ArrayList<>();

        Cursor cursor = db.getAllItems();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String type = cursor.getString(1);
                String item_name = cursor.getString(2);
                String desc = cursor.getString(3);
                String date = cursor.getString(4);
                String location = cursor.getString(5);
                String name = cursor.getString(6);
                String mobile = cursor.getString(7);
                String image = cursor.getString(8);

                String item =
                        "Type: " + type + "\n" +
                                "Item Name: " + item_name + "\n" +
                                "Description: " + desc + "\n" +
                                "Date: " + date + "\n" +
                                "Location: " + location + "\n" +
                                "Name: " + name + "\n" +
                                "Phone: " + mobile;

                data.add(item);
                images.add(image);

            } while (cursor.moveToNext());

            cursor.close();
        }

        listView.setAdapter(new CustomAdapter());
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_row, parent, false);
            }

            ImageView img = view.findViewById(R.id.itemImage);
            TextView details = view.findViewById(R.id.itemDetails);

            details.setText(data.get(position));

            String imgName = images.get(position);

            @SuppressLint("DiscouragedApi")
            int resId = getResources().getIdentifier(
                    imgName,
                    "drawable",
                    getPackageName()
            );

            if (imgName != null && !imgName.isEmpty() && resId != 0) {
                img.setImageResource(resId);
            } else {
                img.setImageResource(android.R.drawable.ic_menu_gallery);
            }
            return view;
        }
    }
}