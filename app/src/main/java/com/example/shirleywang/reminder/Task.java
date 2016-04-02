package com.example.shirleywang.reminder;

import java.sql.Time;

/**
 * Created by ShirleyWang on 3/30/16.
 */
public class Task {
    private int id;
    private String message;
    public Task()
    {

    }

    public Task(String message)
    {
        this.message=message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
