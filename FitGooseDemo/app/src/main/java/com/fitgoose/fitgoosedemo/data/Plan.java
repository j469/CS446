package com.fitgoose.fitgoosedemo.data;

import java.util.ArrayList;

/**
 * Java object for sql table plan
 * jerry 2015/6/1
 */

public class Plan {
    public int pID = 0;
    public String pname;
    public boolean active;
    public boolean Mon;
    public boolean Tue;
    public boolean Wed;
    public boolean Thu;
    public boolean Fri;
    public boolean Sat;
    public boolean Sun;
    public ArrayList<Daily> dailies;

    public Plan(String pname, int active, int Mon, int Tue, int Wed, int Thu, int Fri, int Sat, int Sun) {
        //pID ++;
        this.pname = pname;
        this.active = (active != 0) ;
        this.Mon = (Mon != 0) ;
        this.Tue = (Tue != 0) ;
        this.Wed = (Wed != 0) ;
        this.Thu = (Thu != 0) ;
        this.Fri= (Fri != 0) ;
        this.Sat = (Sat != 0) ;
        this.Sun = (Sun != 0) ;
    }

    public Plan(int pID, String pname, int active, int Mon, int Tue, int Wed, int Thu, int Fri, int Sat, int Sun) {
        this.pID = pID;
        this.pname = pname;
        this.active = (active != 0) ;
        this.Mon = (Mon != 0) ;
        this.Tue = (Tue != 0) ;
        this.Wed = (Wed != 0) ;
        this.Thu = (Thu != 0) ;
        this.Fri= (Fri != 0) ;
        this.Sat = (Sat != 0) ;
        this.Sun = (Sun != 0) ;
    }

    public int getID() {
        return pID;
    }

    public void resetID() {
        pID = -1;
    }

    public void attachPlan(Plan p, ArrayList<Daily> dailies) {
        p.dailies = dailies;
    }
}
