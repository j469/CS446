package com.fitgoose.fitgoosedemo.data;

import java.util.ArrayList;

public class Plan {
    public int pID = 0;
    public String date;
    public int eID;
    public int numOfSets;
    public ArrayList<ExSet> exSets;

    public Plan(String date, int eID, int numOfSets) {
        this.date = date;
        this.eID = eID;
        this.numOfSets = numOfSets;
    }

    public Plan(int pID, String date, int eID, int numOfSets) {
        this.pID = pID;
        this.date = date;
        this.eID = eID;
        this.numOfSets = numOfSets;
    }

    public void attachExSets (ArrayList<ExSet> exSets) {
        this.exSets = exSets;
    }
}
