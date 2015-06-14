package com.fitgoose.fitgoosedemo.data;

/**
 * StatChunk is the class to retrieve data from database
 * Created by guest on 2015-06-06.
 */
public class StatChunk {
    public String date;
    public String pname;
    public int eid;
    public int setid;
    public int quantity;
    public int reps; // 0 for exercises like running etc;
    public boolean complete;

    public StatChunk( String date, String pname, int eid, int setid, int quantity, int reps, boolean complete) {
        this.date = date;
        this.pname = pname;
        this.eid = eid;
        this.setid = setid;
        this.quantity = quantity;
        this.reps = reps;
        this.complete = complete;
    }

    public StatChunk( int eid, int setid, int quantity, int reps, boolean complete) {
        this.eid = eid;
        this.setid = setid;
        this.quantity = quantity;
        this.reps = reps;
        this.complete = complete;
    }

}
