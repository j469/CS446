package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table ExSet
 * jerry 2015/6/1
 */

public class ExSet {
    public int setID = 0;
    public int dID;
    public int quantity;
    public int numOfReps;
    public boolean complete;

    public ExSet(int dID, int quantity, int numOfReps) {
        //setID ++;
        this.dID = dID;
        this.quantity = quantity;
        this.numOfReps = numOfReps;
        this.complete = false;
    }

    public ExSet(int setID,int dID,int quantity, int numOfReps, int complete) {
        this.setID = setID;
        this.dID = dID;
        this.quantity = quantity;
        this.numOfReps = numOfReps;
        this.complete = (complete != 0);
    }

    public int getID() {
        return setID;
    }

    public void resetID() {setID = -1;}
}
