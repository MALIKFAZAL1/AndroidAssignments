package com.example.androidassignments;

import android.content.Context;
import android.os.Bundle;
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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                Msgs.add(message);
                chatAdapter.notifyDataSetChanged();
                messageEditText.setText("");
            }
        });
    }
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


