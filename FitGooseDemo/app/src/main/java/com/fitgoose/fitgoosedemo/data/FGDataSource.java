package com.fitgoose.fitgoosedemo.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/** FGDataSource:
 * This class maintains the database connection and supports adding, fetching
 * jerry 2015/6/4
 */

public class FGDataSource {

    // Database fields
    private static SQLiteDatabase database;
    private FGDataBase fgDB;

    public FGDataSource(Context context) {
        fgDB = new FGDataBase(context);
    }

    public void open() throws SQLException {
        database = fgDB.getWritableDatabase();
    }

    public void close() {
        fgDB.close();
    }

    /* just store all the exercises in the global class, not in the database
    public static void storeExercise (Exercise e) {
        String STORE_EXERCISE = "INSERT INTO exercise (eid,type,ename,unit,shoulder,arms,back,chest,abs,legs,oxy,cardio) VALUES ("
                + e.getID() + " , "
                + e.type + " , "
                + e.name + " , "
                + e.unit + " , "
                + e.shoulder + " , "
                + e.arms + " , "
                + e.back + " , "
                + e.chest + " , "
                + e.abs + " , "
                + e.legs + " , "
                + e.oxy + " , "
                + e.cardio
                + " );";
        database.execSQL(STORE_EXERCISE);
    }*/

    public static void storePlan (Plan p) {
        String STORE_PLAN = "INSERT INTO plan (pid,date,pname) VALUES ( "
                + p.getID() + " , "
                + p.date + " , "
                + p.name
                + " );";
        database.execSQL(STORE_PLAN);
    }

    public static void storeExPlanXRef (ExPlanXRef e) {
        String STORE = "INSERT INTO explanxref (eprid,pid,eid,numofsets) VALUES ( "
                + e.getID() + " , "
                + e.pID + " , "
                + e.eID + " , "
                + e.numOfSets
                + " );";
        database.execSQL(STORE);
    }

    public static void storeExSet (ExSet e) {
        String STORE_EXSET = "INSERT INTO exset ( setid, eprid, quantity, numofreps, complete) VALUES ( "
                + e.getID() + " , "
                + e.eprID + " , "
                + e.quantity + " , "
                + e.numOfReps + " , "
                + e.complete
                + " );";
        database.execSQL(STORE_EXSET);
    }

    /**
     * addPlan(): store table Plan and ExPlanXRef, and return an integer eprID to further ExSet use
     * @param pName, pDate, eID, numOfSets
     * @return (int) eprID
     */
    public static int addPlan (String pName, String pDate, int eID, int numOfSets) {
        Plan tempPlan = new Plan(pName,pDate);
        int pID = tempPlan.getID();
        storePlan(tempPlan);
        ExPlanXRef tempExPlanXRef = new ExPlanXRef(pID, eID, numOfSets);
        int eprID = tempExPlanXRef.getID();
        storeExPlanXRef(tempExPlanXRef);
        return eprID;
    }


    /**
     * addExSet(): store ONLY ONE set into table, so call it multi times is needed
     * @param eprID
     * @param quantity
     * @param numOfReps
     */
    public static void addExSet (int eprID, int quantity, int numOfReps) {
        ExSet tempExSet = new ExSet(eprID, quantity,numOfReps);
        storeExSet(tempExSet);
    }


    /**
     * searchByDate(): get all the plans & sets of the specific date
     * @param date (format: yyyy-MM-dd in String)
     * @return an ArrayList of class StatChunk
     */
    public static ArrayList searchByDate (String date) {

        ArrayList<StatChunk> rtn = new ArrayList<> ();


        String SEARCH_BY_DATE = "SELECT p.pname, x.eid, x.numofsets, e.quantity, e.numofreps, e.complete "
            + "FROM plan AS p, explanxref AS x, exset AS e"
            + "WHERE p.pdate = " + date
            + " AND p.pid = x.pid"
            + " AND e.eprid = x.eprid ;";

        Cursor cursor = database.rawQuery(SEARCH_BY_DATE, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    boolean tempBool = (cursor.getInt(5) == 1);
                    StatChunk tempStatChunk = new StatChunk( date, cursor.getString(0), cursor.getInt(1),
                            cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), tempBool);
                    rtn.add(tempStatChunk);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return rtn;
    }
}
