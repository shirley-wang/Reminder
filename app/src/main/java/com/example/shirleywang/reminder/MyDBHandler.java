package com.example.shirleywang.reminder;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

/**
 * Created by ShirleyWang on 3/30/16.
 */
public class MyDBHandler {
    private Context context;
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "reminder.db";
    private static final String DATABASE_TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK_MESSAGE = "task_message";
    private static final String COLUMN_TASK_DATE = "task_date";
    private static final String TAG = "ShirleysMessage";
    private static final String DATABASE_CREATE_TASK =
            "CREATE table if not exists " + DATABASE_TABLE_TASKS +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TASK_MESSAGE + " VARCHAR NOT NULL, " +
                    COLUMN_TASK_DATE + " DATE " +
                    ");";

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public MyDBHandler(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE_TASK);
                Log.i(TAG,"Database is created");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TASKS);
            onCreate(db);
        }
    }

    /**
     * Open the database
     *
     * @return
     * @throws SQLException
     */
    public MyDBHandler open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    /**
     * Close the database
     */
    public void close() {
        DBHelper.close();
    }

    /**
     * Insert a new task to database
     *
     * @param task
     * @return
     */
    public long insertTask(Task task) {
        ContentValues initialValues = new ContentValues();
        String message = task.getMessage();
        String date = task.getDate();
        Log.i(TAG,message+date);
        initialValues.put(COLUMN_TASK_MESSAGE, message);
        initialValues.put(COLUMN_TASK_DATE, date);
        return db.insert(DATABASE_TABLE_TASKS, null, initialValues);
    }

    public void deleteTable()
    {
        db.execSQL("DROP TABLE "+DATABASE_TABLE_TASKS);
    }

    /**
     * Delete a particular task by id
     *
     * @param rowId
     * @return
     */
    public boolean deleteTask(long rowId) {
        return db.delete(DATABASE_TABLE_TASKS, COLUMN_ID + "=" + rowId, null) > 0;
    }

    /**
     * Retrieves all the tasks
     *
     * @return
     */
    public Cursor getAllTasks() {
        return db.query(DATABASE_TABLE_TASKS, new String[]{COLUMN_ID, COLUMN_TASK_MESSAGE, COLUMN_TASK_DATE}, null, null, null, null, null);
    }

    /**
     * Retrieves a particular task by id
     *
     * @param rowId
     * @return
     * @throws SQLException
     */
    public Cursor getTask(long rowId) throws SQLException {
        Cursor mCursor = db.query(DATABASE_TABLE_TASKS, new String[]{COLUMN_ID, COLUMN_TASK_MESSAGE, COLUMN_TASK_DATE}, COLUMN_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Update a task
     *
     * @param rowId
     * @param message
     * @param date
     * @return
     */
    public boolean updateTask(long rowId, String message, String date) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_TASK_MESSAGE, message);
        args.put(COLUMN_TASK_DATE, date);
        return db.update(DATABASE_TABLE_TASKS, args, COLUMN_ID + "=" + rowId, null) > 0;
    }

}


