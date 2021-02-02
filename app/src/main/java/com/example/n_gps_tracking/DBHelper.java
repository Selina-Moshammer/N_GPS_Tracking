package com.example.n_gps_tracking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.n_gps_tracking.Model.GPS;


public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME ="GPS";
    private final static int DB_VERSION =3;

    public DBHelper(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(GPSTbl.SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(GPSTbl.SQL_DROP);
    db.execSQL(GPSTbl.SQL_CREATE);
    }

}
