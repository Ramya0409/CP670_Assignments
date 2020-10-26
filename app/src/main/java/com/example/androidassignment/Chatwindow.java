package com.example.androidassignment;


import androidx.appcompat.app.AppCompatActivity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Chatwindow extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "Chatwindow";
    ListView chatview;
    Button sendbtn;
    EditText editText;
    ArrayList<String> chat;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatview = findViewById(R.id.chatview);
        sendbtn = findViewById(R.id.button4);
        editText = findViewById(R.id.editText3);
        chat = new ArrayList<String>();
        adapter = new ChatAdapter(this);
        chatview.setAdapter(adapter);
        Log.i(ACTIVITY_NAME, "ChatWindow onCreate()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "onDestroy()");
    }

    public void sendChat(View view) {
        Log.i(ACTIVITY_NAME, "Clicked Send button");
        chat.add(String.valueOf(editText.getText()));
        adapter.notifyDataSetChanged();
        editText.setText("");
    }

    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctxt) {
            super(ctxt,0);
        }
        @Override
        public int getCount(){
            //super.getCount();
            return chat.size();
        }
        @Override
        public String getItem(int pos){
            //super.getItem(pos);
            return chat.get(pos);
        }
        @Override
        public View getView(int pos, View convertView, ViewGroup parent){
            //super.getView(pos,convertView,parent);
            View result = null;
            LayoutInflater inflater = Chatwindow.this.getLayoutInflater();
            if(pos%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView msg = (TextView) result.findViewById(R.id.message);
            msg.setText(getItem(pos));
            return result;
        }
    }
}