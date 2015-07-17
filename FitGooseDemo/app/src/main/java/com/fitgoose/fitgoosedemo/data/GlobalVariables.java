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
     *  Format: ( int eid, false , String name, String unit, boolean shoulder, boolean upper_arm,
     *       boolean back, boolean chest, boolean abs, boolean quads, boolean forearm,
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

    public static int searchUnitByEid (int eid) {
        int rtn = 0;
        for (Exercise e: storedExercises) {
            if ( eid == e.getID()) {
                rtn = e.unit1;
                break;
            }
        }
        return rtn;
    }

    public static int searchSecondUnitByEid (int eid) {
        int rtn = 0;
        for (Exercise e: storedExercises) {
            if ( eid == e.getID()) {
                rtn = e.unit2;
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
