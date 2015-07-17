package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table ExSet
 * jerry 2015/6/1
 */

public class ExSet {
    public int setID = 0;
    public int pID;
    public int quantity1;
    public int quantity2;

    public ExSet(int pID, int quantity1, int quantity2) {
        //setID ++;
        this.pID = pID;
        this.quantity1 = quantity1;
        this.quantity2 = quantity2;
    }

    public ExSet(int setID,int pID,int quantity1, int quantity2) {
        this.setID = setID;
        this.pID = pID;
        this.quantity1 = quantity1;
        this.quantity2 = quantity2;
    }
}
