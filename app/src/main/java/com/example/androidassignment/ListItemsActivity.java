package com.example.androidassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Switch;

public class ListItemsActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
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

    public void imageClick(View view) {
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE);
        }
        catch(ActivityNotFoundException e){
            System.out.println(e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageButton imgbtn = findViewById(R.id.imageButton);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgbitmap = (Bitmap) extras.get("data");
            imgbtn.setImageBitmap(imgbitmap);
        }

    }

    public void setOnCheckChanged(View view) {
        Switch switch_val = findViewById(R.id.switch1);
        if(switch_val.isChecked()) {
            CharSequence text1 =  switch_val.getTextOn();
            Toast toast = Toast.makeText(this, text1, Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            CharSequence text2 =  switch_val.getTextOff();
            Toast toast = Toast.makeText(this, text2, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void setOnTicked(View view) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(ListItemsActivity.this);
        alertbox.setMessage(R.string.dialog_msg);
        alertbox.setTitle(R.string.title);
        alertbox.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked OK button
                Intent result = new Intent(ListItemsActivity.this, MainActivity.class);
                result.putExtra("Response", getString(R.string.response));
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
        alertbox.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked cancel button
            }
        });
        alertbox.show();

    }
}