package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import static android.app.PendingIntent.getActivity;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadUserData();
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }

    private void loadUserData() {
        String file_name = getString(R.string.preference_name);
        SharedPreferences myPrefs = getSharedPreferences(file_name, MODE_PRIVATE);
        String email_key = getString(R.string.emailKey);
        String email_value = myPrefs.getString(email_key, getString(R.string.emailValue));
        ((EditText)findViewById(R.id.editText1)).setText(email_value);
    }

    public void onSaveClicked(View v){
        saveUserData();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void saveUserData() {
        String file_name = getString(R.string.preference_name);
        SharedPreferences myPrefs1 = getSharedPreferences(file_name, MODE_PRIVATE);
        SharedPreferences.Editor myEditor = myPrefs1.edit();
        myEditor.clear();
        String email_key = getString(R.string.emailKey);
        String email_entered = ((EditText)findViewById(R.id.editText1)).getText().toString();
        myEditor.putString(email_key, email_entered);
        myEditor.commit();
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
}