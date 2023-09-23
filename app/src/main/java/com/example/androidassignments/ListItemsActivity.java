package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListItemsActivity extends AppCompatActivity {
    private Button loginButton;
    private Button mainMenu;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        loginButton =(Button) findViewById(R.id.button4);
        mainMenu = (Button) findViewById(R.id.button5);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityList();
            }
        });
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });

    }
    public void openActivityList()
    {
        Intent intent1= new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }

    public void openMainMenu()
    {
        Intent intent2= new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
}