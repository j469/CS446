package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table ExSet
 * jerry 2015/6/1
 */

public class ExSet {
    private static int setID = 0;
    public int eprID;
    public int quantity;
    public int numOfReps;
    public boolean complete;

    public ExSet(int eprID, int quantity, int numOfReps) {
        setID ++;
        this.eprID = eprID;
        this.quantity = quantity;
        this.numOfReps = numOfReps;
        this.complete = false;
    }

    public int getID() {
        return setID;
    }
}
