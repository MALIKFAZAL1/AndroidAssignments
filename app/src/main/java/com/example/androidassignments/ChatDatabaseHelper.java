package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "msgs";
    public static final String KEY_ID="id";
    public static final String KEY_MESSAGE="msg";
    private static final String DB_NAME="Messages.db";
    private static final int VERSION_NUM=1;

    public ChatDatabaseHelper(Context ct)
    {
        super(ct, DB_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MESSAGE + " TEXT);");

        Log.i("ChatDatabaseHelper","onCreate called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i("ChatDatabaseHelper","Calling onUpgrade,oldVersion="+oldVer+"newVersion="+newVer);
    }
}

