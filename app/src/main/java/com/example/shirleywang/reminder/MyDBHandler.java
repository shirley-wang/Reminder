package com.example.shirleywang.reminder;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
/**
 * Created by ShirleyWang on 3/30/16.
 */
public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reminder.db";
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK_MESSAGE = "task_message";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE " + TABLE_TASKS + "(" +
                COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_MESSAGE + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // add a new task to table tasks
    public void addTask(Task task)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_MESSAGE,task.getMessage());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TASKS,null,values);
        db.close();
    }

    // delete a task when I check it
    public void deleteTask(String taskMessage)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE " + COLUMN_TASK_MESSAGE
                + "=\"" + taskMessage + "\";");
    }

    // print database
    public String toString()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS + " WHERE 1";

        //Cursor point to a location in results
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in results
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_TASK_MESSAGE))!=null)
            {
                dbString += c.getString(c.getColumnIndex(COLUMN_TASK_MESSAGE));
                dbString +="\n";
            }
        }
        db.close();
        return dbString;
    }
}
