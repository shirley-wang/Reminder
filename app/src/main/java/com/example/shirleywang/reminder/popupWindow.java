package com.example.shirleywang.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
    TextView tasks;
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
        dbHandler = new MyDBHandler(this,null,null,1);
        tasks = (TextView)findViewById(R.id.tasks_view);
        //Cancel button to go to previous page
        cancelButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        finish();
                    }
                }
        );

        //
        addTaskButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        addButtonClicked();
                        //printData();
                    }
                }
        );
    }

    public void addButtonClicked(){
        Task task = new Task(message.getText().toString());
        dbHandler.addTask(task);
        finish();
    }

    public void printData(){
        String str = dbHandler.toString();
        tasks.setText(str);
        message.setText("");
    }
}
