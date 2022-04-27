package com.example.week6_qn8;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class MyDataBase extends SQLiteOpenHelper {
    public Context context;
    public static final String DATABASE_NAME = "dataManager";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "data";
    public static final String KEY_ID = "id";
    public static final String KEY_IMG_URL = "ImgFavourite";

    public MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static final String query_Create = "CREATE TABLE " + TABLE_NAME  + "(" + KEY_ID +
            "INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_IMG_URL + "BLOB);";
    public static final String query_Drop = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query_Create);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(query_Drop);
        onCreate(db);
    }
    public void deleteEntry(long row){
        SQLiteDatabase sqliteDatabase = getWritableDatabase();
        sqliteDatabase.delete(TABLE_NAME, KEY_ID + "=" + row, null);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(requestCode == 102 && resultCode == Activity.RESULT_OK)
        {
            Bitmap theImage = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            theImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MyDataBase.KEY_IMG_URL, byteArray);
            db.insert(MyDataBase.TABLE_NAME, null, values);
            db.close();
            Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}

