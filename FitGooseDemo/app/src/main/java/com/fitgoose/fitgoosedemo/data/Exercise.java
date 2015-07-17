package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table exercise
 * jerry 2015/6/1
 */

public final class Exercise {

    public int eID;
    public boolean type; // false means default, true means user's modification
    public String name;
    public String unit;
    public boolean shoulder;
    public boolean arms;
    public boolean back;
    public boolean chest;
    public boolean abs;
    public boolean legs;
    public boolean oxy;
    public boolean cardio;
    public boolean secondUnit; // weight exercise is true here
    public String youtubeURL;

    // Constructors
    public Exercise (int eid, boolean type, String name, String unit, boolean shoulder, boolean arms,
                 boolean back, boolean chest, boolean abs, boolean legs, boolean oxy, boolean cardio, boolean secondUnit,String youtubeURL) {
        this.eID = eid;
        this.type = type;
        this.name = name;
        this.unit = unit;
        this.shoulder = shoulder;
        this.arms = arms;
        this.back = back;
        this.chest = chest;
        this.abs = abs;
        this.legs = legs;
        this.oxy = oxy;
        this.cardio = cardio;
        this.secondUnit = secondUnit;
        this.youtubeURL = youtubeURL;
    }

    public Exercise (int eid, int type, String name, String unit, int shoulder, int arms,
                     int back, int chest, int abs, int legs, int oxy, int cardio, int secondUnit,String youtubeURL) {
        this.eID = eid;
        this.type = (type>=1) ;
        this.name = name;
        this.unit = unit;
        this.shoulder = (shoulder>=1) ;
        this.arms = (arms>=1) ;
        this.back = (back>=1) ;
        this.chest = (chest>=1) ;
        this.abs = (abs>=1) ;
        this.legs = (legs>=1) ;
        this.oxy = (oxy>=1) ;
        this.cardio = (cardio>=1) ;
        this.secondUnit = (secondUnit>=1) ;
        this.youtubeURL = youtubeURL;
    }

    public int getID() {
        return eID;
    }
}