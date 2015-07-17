package com.fitgoose.fitgoosedemo.data;

import java.util.ArrayList;

/**
 * Java object for sql table plan
 * jerry 2015/6/1
 */

public class RepeatPlan {
    public int rpID = 0;
    public String pname;
    public boolean active;
    public boolean Mon;
    public boolean Tue;
    public boolean Wed;
    public boolean Thu;
    public boolean Fri;
    public boolean Sat;
    public boolean Sun;
    public ArrayList<Integer> eIDs;
    public ArrayList<Integer> sets;

    public RepeatPlan(String pname, int active, int Mon, int Tue, int Wed, int Thu, int Fri, int Sat, int Sun) {
        this.pname = pname;
        this.active = (active != 0) ;
        this.Mon = (Mon != 0) ;
        this.Tue = (Tue != 0) ;
        this.Wed = (Wed != 0) ;
        this.Thu = (Thu != 0) ;
        this.Fri= (Fri != 0) ;
        this.Sat = (Sat != 0) ;
        this.Sun = (Sun != 0) ;
        eIDs = new ArrayList<>();
        sets = new ArrayList<>();
    }

    public RepeatPlan(int rpID, String pname, int active, int Mon, int Tue, int Wed, int Thu, int Fri, int Sat, int Sun) {
        this.rpID = rpID;
        this.pname = pname;
        this.active = (active != 0) ;
        this.Mon = (Mon != 0) ;
        this.Tue = (Tue != 0) ;
        this.Wed = (Wed != 0) ;
        this.Thu = (Thu != 0) ;
        this.Fri= (Fri != 0) ;
        this.Sat = (Sat != 0) ;
        this.Sun = (Sun != 0) ;
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
