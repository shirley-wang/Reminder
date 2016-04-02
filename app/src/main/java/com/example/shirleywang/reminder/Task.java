package com.example.shirleywang.reminder;

import java.sql.Time;

/**
 * Created by ShirleyWang on 3/30/16.
 */
public class Task {
    private int id;
    private String message;
    private String date;

    public Task() {

    }

    public Task(String message, String date) {
        this.message = message;
        this.date = date;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
