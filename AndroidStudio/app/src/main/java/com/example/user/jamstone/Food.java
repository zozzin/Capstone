package com.example.user.jamstone;

/**
 * Created by user on 2019-04-29.
 */

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
