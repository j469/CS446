package com.fitgoose.fitgoosedemo.data;

import java.util.ArrayList;

public class Daily {
    public int dID = 0;
    public String date;
    public int eID;
    public int numOfSets;
    public ArrayList<ExSet> exSets;

    public Daily (String date, int eID, int numOfSets) {
        //dID++;
        this.date = date;
        this.eID = eID;
        this.numOfSets = numOfSets;
    }

    public Daily (int dID, String date, int eID, int numOfSets) {
        this.dID = dID;
        this.date = date;
        this.eID = eID;
        this.numOfSets = numOfSets;
    }

    public int getID() {
        return dID;
    }

    public void resetID() {
        dID = -1;
    }

    public static void attachDaily (Daily d, ArrayList<ExSet> exSets) {
        d.exSets = exSets;
    }
}
