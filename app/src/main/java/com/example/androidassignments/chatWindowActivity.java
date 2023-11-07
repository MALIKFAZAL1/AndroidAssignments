package com.example.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class chatWindowActivity extends AppCompatActivity {
    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private ArrayList<String> Msgs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatListView=findViewById(R.id.chatListView);
        messageEditText=findViewById(R.id.messageEditText);
        sendButton=findViewById(R.id.sendButton);
        ChatAdapter chatAdapter = new ChatAdapter(this);
        chatListView.setAdapter(chatAdapter);
        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME, null);

        int messageColumnIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);

        if (messageColumnIndex != -1) {
            if (cursor.moveToFirst()) {
                do {
                    String message = cursor.getString(messageColumnIndex);
                    Msgs.add(message);

                    Log.i("chatWindowactivity", "SQL MESSAGE: " + message);
                } while (cursor.moveToNext());
            } else {
                Log.i("chatWindowactivity", "No messages found in the database.");
            }
        } else {
            Log.e("chatWindowactivity", "Column " + ChatDatabaseHelper.KEY_MESSAGE + " not found in the cursor.");
        }

        Log.i("chatWindowactivity", "Cursor's column count = " + cursor.getColumnCount());

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String columnName = cursor.getColumnName(i);
            Log.i("chatWindowactivity", "Column Name: " + columnName);
        }

        cursor.close();
        database.close();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String message = messageEditText.getText().toString();
//                Msgs.add(message);
//                chatAdapter.notifyDataSetChanged();
//                messageEditText.setText("");
                String newMessage = messageEditText.getText().toString();

                // Add the new message to your ArrayList
                Msgs.add(newMessage);

                // Insert the new message into the database
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, newMessage);

                SQLiteDatabase database = dbHelper.getWritableDatabase();
                database.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
                database.close();
            }
        });

    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        // Close the database if it's open
//        if (dbHelper != null) {
//            dbHelper.close();
//        }
//    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public int getCount() {
            return Msgs.size();
        }


        @Override
        public String getItem(int position) {
            return Msgs.get(position);
        }

        @Override
        public View getView(int pos,View convertView,ViewGroup parent) {
            LayoutInflater inflater=chatWindowActivity.this.getLayoutInflater();
            View res=null;

            if (pos%2==0)
            {
                res=inflater.inflate(R.layout.incoming_chat,null);
            }
            else
            {
                res=inflater.inflate(R.layout.outgoing_chat, null);
            }

            TextView mess=res.findViewById(R.id.messageText);
            mess.setText(getItem(pos));

            return res;
        }
    }


}


