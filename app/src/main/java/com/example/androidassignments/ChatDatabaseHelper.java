package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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

    @SuppressLint("Range")
    public MessageDetails getMessageDetails(long messageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = KEY_ID + "=?";
        String[] selectionArgs = {String.valueOf(messageId)};
        MessageDetails messageDetails = new MessageDetails();
        String[] projection = {KEY_ID, KEY_MESSAGE};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            messageDetails.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
            messageDetails.setContent(cursor.getString(cursor.getColumnIndex(KEY_MESSAGE)));
            cursor.close();
        }

        db.close();
        return messageDetails;
    }
    public void deleteMessage(int messageId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(messageId)});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

