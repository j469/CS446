package com.fitgoose.fitgoosedemo.data;

import java.util.ArrayList;

/**
 * Some global variables are here
 * jerry 2015/6/1
 */

public class GlobalVariables {

    /** TODO: (Selway) add more exercises here
     *  Stored exercises:
     *  Format: ( int eid, false , String name, String unit, boolean shoulder, boolean arms,
     *       boolean back, boolean chest, boolean abs, boolean legs, boolean oxy,
     *       boolean cardio, boolean secondUnit)
     */
    public static Exercise[] storedExercises = new Exercise[] {
            new Exercise(0,false, "Run", "Meters", false, false, false, false, false, true, true, false,false),
            new Exercise(1,false, "Bench Press", "KG", true, true, false, true, false, false, false, true, true)
    };

    public static String searchENameByEid (int eid) {
        String rtn = "";
        for (Exercise e: storedExercises) {
            if ( eid == e.getID()) {
                rtn = e.name;
                break;
            }
        }
        return rtn;
    }

    public static String searchUnitByEid (int eid) {
        for (Exercise e: storedExercises) {
            if ( eid == e.getID()) return e.unit;
        }
        return null;
    }

    public static Boolean searchSecondUnitByEid (int eid) {
        for (Exercise e: storedExercises) {
            if ( eid == e.getID()) return e.secondUnit;
        }
        return null;
    }

    //TODO: need addExercise() method to let users store their exercises


}
