package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
private Button activityListButton,startChatButton;

    @SuppressLint("MissingInflatedId")
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityListButton =(Button) findViewById(R.id.button3);
        startChatButton=(Button) findViewById(R.id.startChatBtn);
        //Log.i();

        activityListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","onClick");
                print(getResources().getString(R.string.buttonClick));
                Intent intent = new Intent(MainActivity.this,ListItemsActivity.class);
                startActivity(intent);
            }
        });

        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","User clicked Start Chat Button");
                print(getResources().getString(R.string.buttonClick));
                Intent intent = new Intent(MainActivity.this,chatWindowActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                String messagePassed = data.getStringExtra("Response");
                Toast.makeText(this,messagePassed,Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity","onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("MainActivity","onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("MainActivity","onRestoreInstanceState");
    }
    public void openListItemsActivity()
    {
        Intent intent1= new Intent(this, ListItemsActivity.class);
        startActivity(intent1);
    }
    private void print(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}