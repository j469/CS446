package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table ExPlanXRef
 * jerry 2015/6/1
 */

public class ExPlanXRef {
    private static int eprID = 0;
    public int pID;
    public int eID;
    public int numOfSets;

    public ExPlanXRef(int pID, int eID, int numOfSets) {
        eprID ++;
        this.pID = pID;
        this.eID = eID;
        this.numOfSets = numOfSets;
    }

    public int getID() {
        return eprID;
    }

    public void resetID() {eprID = 0;}
}
