package com.example.shirleywang.reminder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ShirleyWang on 3/31/16.
 */
public class popupWindow extends Activity {
    MyDBHandler dbHandler;
    Button addTaskButton;
    Button cancelButton;
    EditText message;
    EditText date;
    TextView tasks;
    private static final String TAG = "ShirleysMessage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_popup);

        //set the pop up window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));


        addTaskButton = (Button)findViewById(R.id.confirm_adding_button);
        cancelButton = (Button)findViewById(R.id.cancel_adding_button);
        message = (EditText)findViewById(R.id.task_message);
        date = (EditText)findViewById(R.id.task_date);
        tasks = (TextView)findViewById(R.id.tasks_view);
        dbHandler = new MyDBHandler(this);

        //Cancel button to go to previous page
        cancelButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        //dbHandler.deleteTable();
                        finish();
                    }
                }
        );

        //
        addTaskButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        addButtonClicked();
                        printData();
                    }
                }
        );
    }

    public void addButtonClicked(){
        dbHandler.open();
        Task task = new Task(message.getText().toString(),date.getText().toString());
        dbHandler.insertTask(task);
        dbHandler.close();
        finish();
    }

    public void printData(){
        dbHandler.open();
        Cursor c = dbHandler.getAllTasks();
        if (c.moveToFirst())
        {
            do {
                DisplayRecord(c);
            } while (c.moveToNext());
        }
        dbHandler.close();
    }

    public void DisplayRecord(Cursor c) {
        tasks.append(
                "id: " + c.getString(0) +
                        "Message: " + c.getString(1) +
                        "Due Date:  " + c.getString(2));
    }
}
