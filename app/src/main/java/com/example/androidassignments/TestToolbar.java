package com.example.androidassignments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;
import com.google.android.material.snackbar.Snackbar;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "About: App Version 1.0 --By Malik Fazal--", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Change the Snackbar message here
//                Snackbar.make(v, "Your custom message here", Snackbar.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_one) {
            Toast.makeText(this, "First Action", Toast.LENGTH_SHORT).show();
            Log.d("Toolbar", "First Action selected");
        } else if (id == R.id.action_two) {
            Toast.makeText(this, "Second Action", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to go back?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            Log.d("Toolbar", "Second Action selected");
        } else if (id == R.id.action_three) {
            Toast.makeText(this, "Third Action", Toast.LENGTH_SHORT).show();
            Log.d("Toolbar", "Third Action selected");
            AlertDialog.Builder customDialogBuilder = new AlertDialog.Builder(this);
            View customDialogView = getLayoutInflater().inflate(R.layout.dialog, null);
            final EditText newMessageEditText = customDialogView.findViewById(R.id.newMessageEditText);
            Button okButton = customDialogView.findViewById(R.id.okButton);

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newMessage = newMessageEditText.getText().toString();
                    Snackbar.make(findViewById(android.R.id.content), newMessage, Snackbar.LENGTH_LONG).show();
                }
            });

            customDialogBuilder.setView(customDialogView);
            AlertDialog customDialog = customDialogBuilder.create();
            customDialog.show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}