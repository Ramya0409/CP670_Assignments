package com.example.androidassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper
{
    private static final String ACTIVITY_NAME = "ChatDatabaseHelper";
    private static final String DATABASE_NAME = "Assignment3.db";
    private static final int VERSION_NUM = 3;

    public final static String TABLE_NAME = "messages";
    public static final String KEY_ID = "ID";
    public static final String KEY_MESSAGE = "MESSAGE";
    //Query to create table
    private static final String create_sql = "Create table "
            + TABLE_NAME + "(" + KEY_ID
            + " integer primary key autoincrement," + KEY_MESSAGE
            + " text not null);";
    //Query to delete table
    private static final String del_sql = "Drop table if exists "+ TABLE_NAME;

    public ChatDatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(ACTIVITY_NAME,"In onCreate()");
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(ACTIVITY_NAME,"In onUpgrade(), old version ="+ oldVersion +"new version ="+ newVersion);
        db.execSQL(del_sql);
        onCreate(db);
    }
}
