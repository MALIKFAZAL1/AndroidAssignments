package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.*;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button mainMenu;
    @SuppressLint("MissingInflatedId")
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton =(Button) findViewById(R.id.button);
        //mainMenu =(Button) findViewById(R.id.button2);
        SharedPreferences preferences=getSharedPreferences("Prefs", MODE_PRIVATE);
        String savedEmail=preferences.getString("DefaultEmail", "");
        EditText email=findViewById(R.id.editTextTextEmailAddress);
        email.setText(savedEmail);


        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("Prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                EditText emailField=findViewById(R.id.editTextTextEmailAddress);
                EditText pswdField=findViewById(R.id.editPassword);
                String email=emailField.getText().toString();
                String password = pswdField.getText().toString();

                if (isValidEmail(email) && isValidPassword(password))
                {
                    editor.putString("DefaultEmail",email);
                    editor.apply();
                    openMainMenu();
                    finish();
                } else
                {
                    print(getResources().getString(R.string.invalidEP));
                }

            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LoginActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LoginActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LoginActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LoginActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LoginActivity","onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("LoginActivity","onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("LoginActivity","onRestoreInstanceState");
    }

    public void openActivityList()
    {
        Intent intent1= new Intent(this, ListItemsActivity.class);
        startActivity(intent1);
    }

    public void openMainMenu()
    {
        Intent intent2= new Intent(this, MainActivity.class);
        startActivity(intent2);
    }

    private void print(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private boolean isValidEmail(String email)
    {
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private boolean isValidPassword(String password)
    {
        int minLength=8;
        return password.length()>=minLength;
    }
}