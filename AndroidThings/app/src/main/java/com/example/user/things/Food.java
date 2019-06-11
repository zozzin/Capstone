package com.example.user.things;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Food {

    String Name;
    String day;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getday() {
        return day;
    }

    public void setday(String day) {
        this.day = day;
    }


    public Food(String name, String day) {
        this.Name = name;
        this.day = day;
    }
}