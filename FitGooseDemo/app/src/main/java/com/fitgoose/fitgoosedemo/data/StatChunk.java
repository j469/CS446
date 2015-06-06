package com.fitgoose.fitgoosedemo.data;

/**
 * StatChunk is the class to retrieve data from database
 * Created by guest on 2015-06-06.
 */
public class StatChunk {
    public String date;
    public String pname;
    public int eid;
    public int sets;
    public int quantity;
    public int reps; // 0 for exercises like running etc;
    public boolean complete;

    public StatChunk( String date, String pname, int eid, int sets, int quantity, int reps, boolean complete) {
        this.date = date;
        this.pname = pname;
        this.eid = eid;
        this.sets = sets;
        this.quantity = quantity;
        this.reps = reps;
        this.complete = complete;
    }

}
