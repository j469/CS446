package com.fitgoose.fitgoosedemo.data;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Some global variables are here
 * jerry 2015/6/1
 */

public class GlobalVariables {

    /** TODO: (Selway) add more exercises here
     * TODO: save to database
     *  Stored exercises:
     *  Format: ( int eid, false , String name, String unit, boolean shoulder, boolean arms,
     *       boolean back, boolean chest, boolean abs, boolean legs, boolean oxy,
     *       boolean cardio, boolean secondUnit)
     */
    public static ArrayList<Exercise> storedExercises = new ArrayList<>();

    public static ArrayList<String> getAllEName () {
        ArrayList<String> rtn = new ArrayList<>();
        for (Exercise e: storedExercises) {
            rtn.add(e.name);
        }
        return rtn;
    }

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
        String rtn = "";
        for (Exercise e: storedExercises) {
            if ( eid == e.getID()) {
                rtn = e.unit;
                break;
            }
        }
        return rtn;
    }

    public static Boolean searchSecondUnitByEid (int eid) {
        Boolean rtn = false;
        for (Exercise e: storedExercises) {
            if ( eid == e.getID()) {
                rtn = e.secondUnit;
                break;
            }
        }
        return rtn;
    }

    public Exercise getExerciseByEid (int eid) {
        Exercise rtn = null;
        for (Exercise e: storedExercises) {
            if ( eid == e.getID()) {
                rtn = e;
                break;
            }
        }
        return rtn;
    }

    //TODO: need addExercise() method to let users store their exercises


}
