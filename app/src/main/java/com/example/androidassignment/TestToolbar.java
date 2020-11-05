package com.example.androidassignment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "TestToolbar";
    EditText edit;
    Snackbar snack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String msg = getString(R.string.action_one);
        snack = snack.make(findViewById(R.id.toolbar), msg , Snackbar.LENGTH_LONG);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.action), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id) {
            case R.id.action_one:
                snack.show();
                break;
            case R.id.action_two:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.dialog_msg);
                alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //User clicked OK button
                        Intent result = new Intent(TestToolbar.this, MainActivity.class);
                        startActivity(result);
                    }
                });
                alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //User clicked cancel button
                    }
                });
                alert.show();
                break;
            case R.id.action_three:
                AlertDialog.Builder customalert = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View v = inflater.inflate(R.layout.custom_layout, null);
                customalert.setView(v)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                edit = v.findViewById(R.id.custom_text);
                                String message = edit.getText().toString();
                                snack.setText(message);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //on clicking cancel it stays on the same page
                            }
                        });
                customalert.create().show();
                break;
            case R.id.action_about:
                Toast.makeText(this, R.string.about, Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}