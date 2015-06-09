package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table plan
 * jerry 2015/6/1
 */

public class Plan {
    private static int pID = 0;
    public String date;
    public String name;

    public Plan(String date, String name) {
        pID ++;
        this.date = date;
        this.name = name;
    }

    public int getID() {
        return pID;
    }

    public void resetID() {pID = 0;}
}
