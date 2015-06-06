package com.fitgoose.fitgoosedemo.data;

/**
 * Some global variables are here
 * jerry 2015/6/1
 */

public class GlobalVariables {

    /** TODO: (Selway) add more exercises here
     *  Stored exercises:
     *  Format: ( false , String name, String unit, boolean shoulder, boolean arms,
     *       boolean back, boolean chest, boolean abs, boolean legs, boolean oxy,
     *       boolean cardio, boolean secondUnit)
     */
    public static Exercise[] storedExercises = new Exercise[] {
            new Exercise(false, "Run", "Meters", false, false, false, false, false, true, true, false,false),
            new Exercise(false, "Push up", "Repeats", true, true, false, true, false, false, false, true, true)
    };
    
}
