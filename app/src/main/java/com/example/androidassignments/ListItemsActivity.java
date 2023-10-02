package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {
    private Button loginButton;
    private Button mainMenu;
    private ImageView imageView;
    private Bitmap capturedImage;
    private ImageButton imageButton;
    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @SuppressLint("MissingInflatedId")
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        loginButton =(Button) findViewById(R.id.button4);
        mainMenu = (Button) findViewById(R.id.button5);
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                openLogin();
            }
        });
        mainMenu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                openMainMenu();
            }
        });

        //imageView= findViewById(R.id.imageView2);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // if (camIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(camIntent,REQUEST_IMAGE_CAPTURE);
               // }
            }
        });

        Switch switch1 = findViewById(R.id.switch1); // Replace with your actual Switch
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked)
                {
                    Toast toast= Toast.makeText(ListItemsActivity.this,R.string.switchOn,Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast =Toast.makeText(ListItemsActivity.this,R.string.switchOff,Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        CheckBox checkBox =findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    showFinishConfirmationDialog();
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ListItemsActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ListItemsActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ListItemsActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ListItemsActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ListItemsActivity","onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("ListItemsActivity","onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("ListItemsActivity","onRestoreInstanceState");
    }
    public void openLogin()
    {
        Intent intent1= new Intent(this, LoginActivity.class);
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
    private void showFinishConfirmationDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(ListItemsActivity.this);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent resIntent=new Intent();
                        resIntent.putExtra("Response","ListItemsActivity passed");
                        setResult(Activity.RESULT_OK,resIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras=data.getExtras();
            if (extras!=null) {
                capturedImage= (Bitmap) extras.get("data");
                imageButton.setImageBitmap(capturedImage);
            }
        }
    }
}