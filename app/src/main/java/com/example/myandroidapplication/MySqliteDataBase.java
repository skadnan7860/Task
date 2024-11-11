package com.example.myandroidapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MySqliteDataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATA_BASE_NAME = "login.db";
    private static final int DATA_BASE_VERSION = 1;

    private static final String TABLE_NAME = "add_user";
    private static final String COLUM_ID = "id";
    private static final String COLUM_USER_NAME = "user_name";
    private static final String COLUM_PASSWORD = "password";
    private static final String COLUM_EMAIL = "email";

    public MySqliteDataBase(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUM_USER_NAME + " TEXT, "
                + COLUM_EMAIL + " TEXT, "
                + COLUM_PASSWORD + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUM_USER_NAME, username);
        values.put(COLUM_PASSWORD, password);
        values.put(COLUM_EMAIL, email);

        long result = db.insert(TABLE_NAME, null, values);
//        db.close();

        return result != -1;  // Returns true if the user was added successfully
    }

    // Check if username and password exist in the database (for login)
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUM_ID, COLUM_USER_NAME, COLUM_PASSWORD},
                COLUM_USER_NAME + "=? AND " + COLUM_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;  // User found
        } else {
            db.close();
            return false;  // User not found
        }
    }
}
