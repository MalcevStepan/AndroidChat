package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="ClientData";
    public static final String DATABASE_CONTACTS="contacts";

    public static final String KEY_ID="_id";
    public static final String KEY_NAME="name";
    public static final String KEY_LOGIN="login";
    public static final String KEY_PASSWORD="password";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
sqLiteDatabase.execSQL("create table "+DATABASE_CONTACTS+"("+KEY_ID+" integer primary key,"+KEY_LOGIN+" text,"+KEY_PASSWORD+" text,"+KEY_NAME+" text"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
sqLiteDatabase.execSQL("drop table if exists "+ DATABASE_CONTACTS);
onCreate(sqLiteDatabase);
    }
}
