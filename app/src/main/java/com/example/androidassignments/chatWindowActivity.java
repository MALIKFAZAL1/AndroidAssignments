package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class chatWindowActivity extends AppCompatActivity implements Cursor {
    Button sendButton;
    ListView chatListView;
    EditText chatText;
    ArrayList<String> Msgs = new ArrayList<>();
    List<ChatModel> Model = new ArrayList<>();
    ChatAdapter messageAdapter;
    ChatDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        databaseHelper = new ChatDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        sendButton = findViewById(R.id.sendButton);
        chatListView = findViewById(R.id.chatListView);
        chatText = findViewById(R.id.messageEditText);
        messageAdapter = new ChatAdapter(this, this);
        chatListView.setAdapter(messageAdapter);


        String query = "SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        
        Log.i("ChatWindow", "Cursor's column count = " + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i("ChatWindow", "" + i);
            Log.i("ChatWindow", "Column Name: " + cursor.getColumnName(i));
        }
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));

                ChatModel chatModel = new ChatModel(id,message);

                Msgs.add(message);
                Model.add(chatModel);
                Log.i("ChatWindow", "SQL MESSAGE: " + message);
            } while (cursor.moveToNext());
        }


        if (cursor != null) {
            cursor.close();
        }
        db.close();
        sendButton.setOnClickListener(view -> {
            if (chatText.getText().toString() != null && !chatText.getText().toString().isEmpty()) {
                String newMessage = chatText.getText().toString();
                Msgs.add(newMessage);
                messageAdapter.notifyDataSetChanged();
                chatText.setText("");
                SQLiteDatabase db1 = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, newMessage);
                db1.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
                db1.close();

            } else {
                Toast.makeText(getApplicationContext(), "Enter text", Toast.LENGTH_LONG).show();
            }
        });

        chatListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Log.i("ChatWindow","pos:"+position);
            Log.i("ChatWindow","chatModel"+Model);
            ChatModel chatModel = Model.get(position);
            showDetails(chatModel);
        });
    }

    private void showDetails(ChatModel item) {
        if (isTabletLayout()) {
           
            Bundle bundle = new Bundle();
            bundle.putInt("messageId", item.id);
            bundle.putString("message", item.chatMessage);
            MessageFragment fragment = new MessageFragment(bundle,true, chatWindowActivity.this::updateListView);

            Log.i("ChatWindow","InTablet");
            fragment.setArguments(bundle);

            // Start a FragmentTransaction to replace the fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            Intent intent = new Intent(chatWindowActivity.this, MessageDetails.class);
            intent.putExtra("messageId", item.id);
            intent.putExtra("message",item.chatMessage);

            startActivity(intent);
        }
    }
 
    public void updateListView() {
        databaseHelper = new ChatDatabaseHelper(this);
        Msgs = new ArrayList<>();
        Model= new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String query = "SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));

                ChatModel chatModel = new ChatModel(id,message);

                Msgs.add(message);
                Model.add(chatModel);
                Log.i("ChatWindow", "SQL MESSAGE: " + message);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        messageAdapter.notifyDataSetChanged();
    }

    private boolean isTabletLayout() {

        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        return frameLayout != null;
    }

    @Override
    public int getCount() {return 0;}

    @Override
    public int getPosition() {return 0;}

    @Override
    public boolean move(int offset) {return false;}

    @Override
    public boolean moveToPosition(int position) {return false;}

    @Override
    public boolean moveToFirst() {return false;}

    @Override
    public boolean moveToLast() {return false;}

    @Override
    public boolean moveToNext() {return false;}

    @Override
    public boolean moveToPrevious() {return false;}

    @Override
    public boolean isFirst() {
        return false;
    }

    @Override
    public boolean isLast() {
        return false;
    }

    @Override
    public boolean isBeforeFirst() {
        return false;
    }

    @Override
    public boolean isAfterLast() {
        return false;
    }

    @Override
    public int getColumnIndex(String columnName) {
        return 0;
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        return 0;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ChatWindow","I'm in onResume");
        updateListView();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return null;
    }

    @Override
    public String[] getColumnNames() {
        return new String[0];
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        return new byte[0];
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {

    }

    @Override
    public short getShort(int columnIndex) {
        return 0;
    }

    @Override
    public int getInt(int columnIndex) {
        return 0;
    }

    @Override
    public long getLong(int columnIndex) {
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) {
        return 0;
    }

    @Override
    public double getDouble(int columnIndex) {
        return 0;
    }

    @Override
    public int getType(int columnIndex) {
        return Cursor.FIELD_TYPE_NULL;
    }

    @Override
    public boolean isNull(int columnIndex) {
        return false;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public boolean requery() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {

    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {

    }

    @Override
    public Uri getNotificationUri() {
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    @Override
    public void setExtras(Bundle extras) {

    }

    @Override
    public Bundle getExtras() {
        return null;
    }

    @Override
    public Bundle respond(Bundle extras) {
        return null;
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        private final Cursor cursor;

        public ChatAdapter(@NonNull Context context, Cursor cursor) {

            super(context, 0);
            this.cursor = cursor;
        }

        public int getCount() {
            return Msgs.size();
        }

        public String getItem(int position) {
            return Msgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            if (cursor.moveToPosition(position)) {
                return cursor.getLong(cursor.getColumnIndexOrThrow(ChatDatabaseHelper.KEY_ID));
            }
            return -1;
        }

        @NonNull
        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = chatWindowActivity.this.getLayoutInflater();
            View result;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.incoming_chat, null);
            else
                result = inflater.inflate(R.layout.outgoing_chat, null);
            TextView message = result.findViewById(R.id.messageText);
            message.setText(getItem(position));
            return result;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateListView();
    }
}