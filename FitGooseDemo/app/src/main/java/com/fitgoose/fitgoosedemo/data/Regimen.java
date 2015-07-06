package com.fitgoose.fitgoosedemo.data;

import java.util.ArrayList;

public class Regimen {
    public int rID = 0;
    public String rname;
    public ArrayList<Integer> eIDs;
    public ArrayList<Integer> sets; //corresponding to eID

    public Regimen(int rID, String rname, ArrayList<Integer> eIDs,ArrayList<Integer> sets) {
        this.rID = rID;
        this.rname = rname;
        this.eIDs = eIDs;
        this.sets = sets;
    }

    public Regimen(String rname) {
        this.rname = rname;
        eIDs = new ArrayList<>();
        sets = new ArrayList<>();
    }

    public void attachEID(int eID) {
        this.eIDs.add(eID);
    }

    public void attachNumOfSet(int numOfSets) {
        this.sets.add(numOfSets);
    }
}
