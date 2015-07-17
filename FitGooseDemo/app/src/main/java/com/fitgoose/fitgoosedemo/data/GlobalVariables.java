package com.fitgoose.fitgoosedemo.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static List<String> bodyPartName = Arrays.asList("all","shoulder", "arms", "back", "chest", "abs","legs", "oxy", "cardio");

    public static ArrayList<Exercise> getExercisesByType( int type) {
        if (type<1 || type>8) return storedExercises;

        ArrayList<Exercise> rtn = new ArrayList<>();
        for (Exercise e: storedExercises) {
            switch (type) {
                case 1: {
                    if (e.shoulder) rtn.add(e);
                    break;
                }
                case 2: {
                    if (e.arms) rtn.add(e);
                    break;
                }
                case 3: {
                    if (e.back) rtn.add(e);
                    break;
                }
                case 4: {
                    if (e.chest) rtn.add(e);
                    break;
                }
                case 5: {
                    if (e.abs) rtn.add(e);
                    break;
                }
                case 6: {
                    if (e.legs) rtn.add(e);
                    break;
                }
                case 7: {
                    if (e.oxy) rtn.add(e);
                    break;
                }
                case 8: {
                    if (e.cardio) rtn.add(e);
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return rtn;
    }

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
