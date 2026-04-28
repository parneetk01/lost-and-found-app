package com.example.lostfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "LostFound.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // USERS TABLE
        db.execSQL("CREATE TABLE users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT UNIQUE, " +
                "phone TEXT, " +
                "password TEXT)");

        // ITEMS TABLE
        db.execSQL("CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT, " +
                "item_name TEXT, " +
                "description TEXT, " +
                "date TEXT, " +
                "location TEXT, " +
                "name TEXT, " +
                "mobile TEXT, " +
                "imageUri TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    // REGISTER
    public boolean registerUser(String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // check duplicate email
        Cursor cursor = db.rawQuery(
                "SELECT id FROM users WHERE email=?",
                new String[]{email.trim()}
        );

        if (cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        cursor.close();

        ContentValues cv = new ContentValues();
        cv.put("name", name.trim());
        cv.put("email", email.trim());
        cv.put("phone", phone.trim());
        cv.put("password", password.trim());

        long result = db.insert("users", null, cv);

        return result != -1;
    }

    // LOGIN
    public boolean checkUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id FROM users WHERE email=? AND password=?",
                new String[]{email.trim(), password.trim()}
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();

        return exists;
    }

    public boolean checkEmailExists(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id FROM users WHERE email=?",
                new String[]{email.trim()}
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();

        return exists;
    }

    // INSERT ITEM
    public boolean insertItem(String type,
                              String item_name,
                              String desc,
                              String date,
                              String location,
                              String name,
                              String mobile,
                              String imageUri) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("item_name", item_name);
        values.put("description", desc);
        values.put("date", date);
        values.put("location", location);
        values.put("name", name);
        values.put("mobile", mobile);
        values.put("imageUri", imageUri);  // stores drawable name like "keys"

        long result = db.insert("items", null, values);

        return result != -1;
    }

    // GET ALL ITEMS
    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM items", null);
    }
}