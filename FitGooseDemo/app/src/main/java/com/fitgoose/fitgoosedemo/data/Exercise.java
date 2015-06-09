package com.fitgoose.fitgoosedemo.data;

/**
 * Java object for sql table exercise
 * jerry 2015/6/1
 */

public final class Exercise {

    private static int eID = 0;
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

    // Constructors
    public Exercise (boolean type, String name, String unit, boolean shoulder, boolean arms,
                 boolean back, boolean chest, boolean abs, boolean legs, boolean oxy, boolean cardio, boolean secondUnit) {
        eID ++;
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
    }

    public int getID() {
        return eID;
    }

    public void resetID() {
        eID=0;
    }
    /**
     * getExerciseByID:
     * @param eid
     * @return an Exercise object if valid eid, otherwise null pointer
     */

    public Exercise getExerciseByID (int eid) {
        for (Exercise e: GlobalVariables.storedExercises) {
            if (e.getID() == eid) return e;
        }
        return null;
    }

    //public Exercise getExerciseByName (String name) {}
}