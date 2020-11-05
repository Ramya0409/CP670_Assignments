package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myButton = findViewById(R.id.button);
        Log.i(ACTIVITY_NAME, "In onCreate()");
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

    public void onClicked(View view) {
        Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if(requestCode == 10 && responseCode == RESULT_OK){
            String message = data.getStringExtra("Response");
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
        }
    }

    public void startChat(View view) {
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        Intent window = new Intent(MainActivity.this, Chatwindow.class);
        startActivity(window);
    }

    public void testToolbar(View view) {
        Log.i(ACTIVITY_NAME, "User clicked Toolbar activity");
        Intent toolbar = new Intent(MainActivity.this, TestToolbar.class);
        startActivity(toolbar);
    }
}