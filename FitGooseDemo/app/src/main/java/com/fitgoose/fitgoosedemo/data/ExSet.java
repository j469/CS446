package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table ExSet
 * jerry 2015/6/1
 */

public class ExSet {
    public int setID = 0;
    public int pID;
    public int quantity;
    public int numOfReps;
    public boolean complete;

    public ExSet(int pID, int quantity, int numOfReps) {
        //setID ++;
        this.pID = pID;
        this.quantity = quantity;
        this.numOfReps = numOfReps;
        this.complete = false;
    }

    public ExSet(int setID,int pID,int quantity, int numOfReps, int complete) {
        this.setID = setID;
        this.pID = pID;
        this.quantity = quantity;
        this.numOfReps = numOfReps;
        this.complete = (complete != 0);
    }
}
