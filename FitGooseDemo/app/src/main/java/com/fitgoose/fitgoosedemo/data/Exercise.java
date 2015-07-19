package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table exercise
 * jerry 2015/6/1
 */

public final class Exercise {

    public int eID;
    public boolean type; // false means default, true means user's modification
    public String name;

    // unit
    public boolean secondUnit; // weight exercise is true here
    public int unit1; // 0 = null, 1 = lbs, 2 = reps, 3 = mins, 4 = m
    public int unit2;

    // body parts
    public boolean shoulder;
    public boolean chest;
    public boolean abs;
    public boolean upper_arm;
    public boolean forearm;
    public boolean quads;
    public boolean calves;
    public boolean back;
    public boolean cardio;

    // Youtube video
    public String youtubeURL;

    // Constructors
    public Exercise (int eid, boolean type, String name, boolean secondUnit, int unit1, int unit2, boolean shoulder, boolean chest,
                     boolean abs, boolean upper_arm, boolean forearm, boolean quads, boolean calves, boolean back, boolean cardio,
                     String youtubeURL) {
        this.eID = eid;
        this.type = type;
        this.name = name;
        this.secondUnit = secondUnit;
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.shoulder = shoulder;
        this.chest = chest;
        this.abs = abs;
        this.upper_arm = upper_arm;
        this.forearm = forearm;
        this.quads = quads;
        this.calves = calves;
        this.back = back;
        this.cardio = cardio;
        this.youtubeURL = youtubeURL;
    }

    public Exercise (int eid, int type, String name, int secondUnit, int unit1, int unit2, int shoulder, int chest, int abs,
                     int upper_arm, int forearm, int quads, int calves, int back, int cardio, String youtubeURL) {
        this.eID = eid;
        this.type = (type>=1) ;
        this.name = name;
        this.secondUnit = (secondUnit>=1) ;
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.shoulder = (shoulder>=1) ;
        this.chest = (chest>=1) ;
        this.abs = (abs>=1) ;
        this.upper_arm = (upper_arm >=1) ;
        this.forearm = (forearm >=1) ;
        this.quads = (quads >=1) ;
        this.calves = (calves >=1);
        this.back = (back>=1) ;
        this.cardio = (cardio>=1) ;
        this.youtubeURL = youtubeURL;
    }

    public int getID() {
        return eID;
    }
}