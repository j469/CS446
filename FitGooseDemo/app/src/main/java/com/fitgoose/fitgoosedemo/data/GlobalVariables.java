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

    /**
     *  Stored exercises:
     *  Format: (int eid, boolean type, String name, boolean secondUnit, int unit1, int unit2, boolean shoulder, boolean chest,
     boolean abs, boolean upper_arm, boolean forearm, boolean quads, boolean calves, boolean back, boolean cardio,
     String youtubeURL)
     */
    public static ArrayList<Exercise> storedExercises = new ArrayList<>();

    public static List<String> bodyPartName = Arrays.asList("All","Shoulder","Chest","Abs",
            "Upper Arm","Forearm", "Quads","Calves","Back","Cardio","Regimen");

    public static List<String> exerciseUnit = Arrays.asList("None", "lbs", "Reps", "Mins", "Meters");

    public static ArrayList<Exercise> getExercisesByType( int type) {
        if (type<1 || type>9) return storedExercises;

        ArrayList<Exercise> rtn = new ArrayList<>();
        for (Exercise e: storedExercises) {
            switch (type) {
                case 1: {
                    if (e.shoulder) rtn.add(e);
                    break;
                }
                case 2: {
                    if (e.chest) rtn.add(e);
                    break;
                }
                case 3: {
                    if (e.abs) rtn.add(e);
                    break;
                }
                case 4: {
                    if (e.upper_arm) rtn.add(e);
                    break;
                }
                case 5: {
                    if (e.forearm) rtn.add(e);
                    break;
                }
                case 6: {
                    if (e.quads) rtn.add(e);
                    break;
                }
                case 7: {
                    if (e.calves) rtn.add(e);
                    break;
                }
                case 8: {
                    if (e.back) rtn.add(e);
                    break;
                }
                case 9: {
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
                if (e.secondUnit) {
                    rtn = e.unit2;
                } else {
                    rtn = -1;
                }
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
