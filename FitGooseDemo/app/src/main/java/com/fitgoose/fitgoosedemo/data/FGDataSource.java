package com.fitgoose.fitgoosedemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/** FGDataSource:
 * This class maintains the database connection and supports adding, fetching
 * jerry 2015/6/4
 */

public class FGDataSource extends SQLiteOpenHelper {

    // Use a Singleton to instantiate FGDataSource
    private static FGDataSource sInstance;

    public static synchronized FGDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FGDataSource(context.getApplicationContext());
        }
        return sInstance;
    }

    // Private Constructor to ensure singleton
    private FGDataSource (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_NAME = "FitGoose.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * initial database etc.
     */
    private static final String PLAN_CREATE = "CREATE TABLE plan ( "
            + "pid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "date TEXT NOT NULL, "
            + "pname TEXT NOT NULL UNIQUE"
            + ");" ;

    private static final String EXPLANXREF_CREATE = "CREATE TABLE explanxref ( "
            + "eprid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "pid INTEGER NOT NULL, "
            + "eid INTEGER NOT NULL, "
            + "numofsets INTEGER NOT NULL "
            + ");" ;

    private static final String EXSET_CREATE = "CREATE TABLE exset ( "
            + "setid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "eprid INTEGER NOT NULL, "
            + "quantity INTEGER NOT NULL, "
            + "numofreps INTEGER NOT NULL, "
            + "complete INTEGER NOT NULL "
            + ");" ;

    // Delete tables
    private static final String TABLES_DELETE = " DROP TABLE IF EXISTS plan;"
            + " DROP TABLE IF EXISTS explanxref;"
            + " DROP TABLE IF EXISTS exset;" ;



    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLES_DELETE);
        database.execSQL(PLAN_CREATE);
        database.execSQL(EXPLANXREF_CREATE);
        database.execSQL(EXSET_CREATE);
        Log.d(FGDataSource.class.getName(), "Database on create.");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(FGDataSource.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(db);
    }


    /**
     * CRUD operations (create "add", read "get", update, delete)
     */

    // Books table name
    private static final String TABLE_PLAN = "plan";
    private static final String TABLE_EXPLANXREF = "explanxref";
    private static final String TABLE_EXSET = "exset";

    // Books Table Columns names
    private static final String PLAN_PID = "pid";
    private static final String PLAN_DATE = "date";
    private static final String PLAN_PNAME = "pname";
    private static final String EXPLANXREF_EPRID = "eprid";
    private static final String EXPLANXREF_PID = "pid";
    private static final String EXPLANXREF_EID = "eid";
    private static final String EXPLANXREF_SETS = "numofsets";
    private static final String EXSET__SETID = "setid";
    private static final String EXSET__EPRID = "eprid";
    private static final String EXSET__QUANTITY = "quantity";
    private static final String EXSET__REPS = "numofreps";
    private static final String EXSET__COMPLETE = "complete";

    private static final String[] PLAN_COLUMNS = {PLAN_PID,PLAN_DATE,PLAN_PNAME};
    private static final String[] EXPLANXREF_COLUMNS = {EXPLANXREF_EPRID,EXPLANXREF_PID,EXPLANXREF_EID,EXPLANXREF_SETS};
    private static final String[] EXSET_COLUMNS = {EXSET__SETID,EXSET__EPRID,EXSET__QUANTITY,EXSET__REPS,EXSET__COMPLETE};


    public void storePlan(Plan p) {
        // 0. set log information
        Log.d("storePlan", Integer.toString(p.getID()));

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(PLAN_PID,p.getID());
        values.put(PLAN_DATE, p.date);
        values.put(PLAN_PNAME, p.name);

        // 3. insert
        db.insert(TABLE_PLAN, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void storeExPlanXRef (ExPlanXRef e) {
        Log.d("storeExPlanXRef", Integer.toString(e.getID()));
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPLANXREF_EPRID,e.getID());
        values.put(EXPLANXREF_PID, e.pID);
        values.put(EXPLANXREF_EID, e.eID);
        values.put(EXPLANXREF_SETS, e.numOfSets);

        db.insert(TABLE_EXPLANXREF, null, values);

        db.close();
    }

    public void storeExSet (ExSet e) {
        Log.d("storeExSet", Integer.toString(e.getID()));
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXSET__SETID,e.getID());
        values.put(EXSET__EPRID, e.eprID);
        values.put(EXSET__QUANTITY, e.quantity);
        values.put(EXSET__REPS, e.numOfReps);
        values.put(EXSET__COMPLETE, e.complete);

        db.insert(TABLE_EXSET, null, values);

        db.close();
    }

    /** TODO: bug here
     * addPlan(): store table Plan and ExPlanXRef, and return an integer eprID to further ExSet use
     * @param pName, pDate, eID, numOfSets
     * @return (int) eprID
     */
    public int addPlan (String pName, String pDate, int eID, int numOfSets) {
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
    public void addExSet (int eprID, int quantity, int numOfReps) {
        ExSet tempExSet = new ExSet(eprID, quantity,numOfReps);
        storeExSet(tempExSet);
    }


    /**
     * searchByDate(): get all the plans & sets of the specific date
     * @param date (format: yyyy-MM-dd in String)
     * @return an ArrayList of class StatChunk
     */
    public ArrayList<StatChunk> searchByDate (String date) {

        ArrayList<StatChunk> rtn = new ArrayList<> ();
        SQLiteDatabase database = this.getReadableDatabase();

        String SEARCH_BY_DATE = "SELECT p.pname, x.eid, e.setid, e.quantity, e.numofreps, e.complete "
            + "FROM plan AS p, explanxref AS x, exset AS e "
            + "WHERE p.date = ? "
            + "AND p.pid = x.pid "
            + "AND e.eprid = x.eprid "
            + "ORDER BY p.pname, x.eid ; "   ;

        Cursor cursor = database.rawQuery(SEARCH_BY_DATE, new String[]{date} );
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

        database.close();
        return rtn;
    }

    /**
     * searchByDate(): get details of a plan name
     * @param pname (format: yyyy-MM-dd in String)
     * @return an ArrayList of class StatChunk
     */
    public ArrayList<StatChunk> searchByPName (String pname) {

        ArrayList<StatChunk> rtn = new ArrayList<> ();
        SQLiteDatabase database = this.getReadableDatabase();

        String SEARCH_BY_DATE = "SELECT x.eid, e.setid, e.quantity, e.numofreps, e.complete "
                + "FROM plan AS p, explanxref AS x, exset AS e"
                + "WHERE p.pname = ? "
                + "AND p.pid = x.pid "
                + "AND e.eprid = x.eprid "
                + "ORDER BY x.eid; "   ;

        Cursor cursor = database.rawQuery(SEARCH_BY_DATE, new String[]{pname});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    boolean tempBool = (cursor.getInt(4) == 1);
                    StatChunk tempStatChunk = new StatChunk( cursor.getInt(0),
                            cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), tempBool);
                    rtn.add(tempStatChunk);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
        return rtn;
    }

    /**
     * searchPNameByDate():
     * @param date (format: yyyy-MM-dd in String)
     * @return an ArrayList of plan names
     */
    public ArrayList<String> searchPNameByDate (String date) {
        ArrayList<String> rtn = new ArrayList<> ();
        SQLiteDatabase database = this.getReadableDatabase();

        String SEARCH_BY_DATE = "SELECT pname FROM plan WHERE date = ?";

        Cursor cursor = database.rawQuery(SEARCH_BY_DATE, new String[]{date});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    rtn.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
        return rtn;
    }

    /**
     * searchEIDByPName():
     * @param pid
     * @return a list of exercise IDs in this plan
     */
    public ArrayList<Integer> searchEidByPid (int pid) {
        ArrayList<Integer> rtn = new ArrayList<> ();
        SQLiteDatabase database = this.getReadableDatabase();


        String s = "SELECT eid FROM explanxref WHERE pid = ?";
        Cursor cursor = database.rawQuery(s, new String[]{ Integer.toString(pid) });

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    rtn.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
        return rtn;
    }

    public int searchPidByPname (String pname) {
        String s = "SELECT pid FROM plan WHERE pname = ?";
        SQLiteDatabase database = this.getReadableDatabase();
        int rtn = 0;
        Cursor cursor = database.rawQuery(s, new String[]{pname});
        if (cursor != null) {
            if (cursor.moveToFirst()) rtn = cursor.getInt(0);
            cursor.close();
        }

        database.close();
        return rtn;
    }

    /**
     * searchExSet(): use pid and eid to search all the exercises sets
     * @param pid
     * @param eid
     * @return ArrayList<ExSet>
     */
    public ArrayList<ExSet> searchExSet (int pid, int eid) {

        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<ExSet> rtn = new ArrayList<>();
        String s = "SELECT s.quantity, s.numofreps, s.complete "
                    + " FROM exset AS s, explanxref AS e "
                    + " WHERE s.eprid = e.eprid AND e.pid = ? AND e.eid = ? ;" ;
        Cursor cursor = database.rawQuery(s, new String[]{ Integer.toString(pid),Integer.toString(eid) });
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    boolean tempBool = (cursor.getInt(2) == 1);
                    ExSet exSet = new ExSet( cursor.getInt(0), cursor.getInt(1),tempBool);
                    rtn.add(exSet);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
        return rtn;
    }

    public void deleteAll() {
        SQLiteDatabase database = this.getReadableDatabase();
        database.execSQL("delete from "+ TABLE_PLAN);
        database.execSQL("delete from "+ TABLE_EXPLANXREF);
        database.execSQL("delete from "+ TABLE_EXSET);
        database.close();
    }
}
