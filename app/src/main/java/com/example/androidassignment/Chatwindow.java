package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Chatwindow extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "Chatwindow";
    ListView chatview;
    Button sendbtn;
    EditText editText;
    Boolean flayout;
    ArrayList<String> chat;
    ChatAdapter adapter;
    Cursor cursor;
    private SQLiteDatabase db;
    private ChatDatabaseHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws SQLException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "ChatWindow onCreate()");
        chatview = findViewById(R.id.chatview);
        sendbtn = findViewById(R.id.button4);
        editText = findViewById(R.id.editText3);
        flayout = Boolean.valueOf(String.valueOf(findViewById(R.id.flayout)));

        chat = new ArrayList<String>();
        adapter = new ChatAdapter(this);
        chatview.setAdapter(adapter);
        dbOpenHelper = new ChatDatabaseHelper(this);
        db = dbOpenHelper.getWritableDatabase();
        String[] columns = {ChatDatabaseHelper.KEY_ID,ChatDatabaseHelper.KEY_MESSAGE};
        cursor = db.query(dbOpenHelper.TABLE_NAME, columns,null,null,null,
                null,null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "Cursor's column count:" + cursor.getColumnCount());
            Log.i(ACTIVITY_NAME, "SQL Message:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            chat.add(cursor.getString(1)); //this gets the previous chat on ListView
            Log.i(ACTIVITY_NAME, "CN \t CV");
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.i(ACTIVITY_NAME, cursor.getColumnName(i) + "\t" + cursor.getString(i));
            }
            cursor.moveToNext();
        }
        cursor.close();

        //fragment(adapter.getItemId());
    }

    public void fragment(int position, String msg){
        MessageFragment msgFragment = new MessageFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("message", msg);

        if(flayout) {
            Log.i(ACTIVITY_NAME, "Using tablet layout");
            ft.add(R.id.fragment, msgFragment);
            msgFragment.setArguments(args);
        }
        else
            Log.i(ACTIVITY_NAME,"Using phone layout");
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
        db.close();
    }

    public void sendChat(View view) {
        Log.i(ACTIVITY_NAME, "Send button is clicked");
        String chatData = String.valueOf(editText.getText());
        chat.add(chatData);
        adapter.notifyDataSetChanged();
        editText.setText("");

        ContentValues vals = new ContentValues();
        vals.put(ChatDatabaseHelper.KEY_MESSAGE, chatData);
        db.insert(dbOpenHelper.TABLE_NAME,"NullPlaceHolder",vals);
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
        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
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