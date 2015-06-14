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
    private static Context mContext;
    
    public static synchronized FGDataSource getInstance(Context context) {
        mContext = context;
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
    private static final int DATABASE_VERSION = 3;

    /**
     * initial database etc.
     */
    private static final String PLAN_CREATE = "CREATE TABLE plan ( "
            + "pid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "pname TEXT NOT NULL UNIQUE, "
            + "active INTEGER NOT NULL, "
            + "mon INTEGER NOT NULL, "
            + "tue INTEGER NOT NULL, "
            + "wed INTEGER NOT NULL, "
            + "thu INTEGER NOT NULL, "
            + "fri INTEGER NOT NULL, "
            + "sat INTEGER NOT NULL, "
            + "sun INTEGER NOT NULL "
            + "); " ;

    private static final String DAILY_CREATE = "CREATE TABLE daily ( "
            + "did INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "date TEXT NOT NULL, "
            + "eid INTEGER NOT NULL, "
            + "numofsets INTEGER NOT NULL "
            + "); " ;

    private static final String EXSET_CREATE = "CREATE TABLE exset ( "
            + "setid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "did INTEGER NOT NULL, "
            + "quantity INTEGER NOT NULL, "
            + "numofreps INTEGER NOT NULL, "
            + "complete INTEGER NOT NULL "
            + ");" ;

    // Delete tables
    private static final String TABLES_DELETE = " DROP TABLE IF EXISTS plan; "
            + " DROP TABLE IF EXISTS daily; "
            + " DROP TABLE IF EXISTS exset; " ;



    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(PLAN_CREATE);
        database.execSQL(DAILY_CREATE);
        database.execSQL(EXSET_CREATE);
        Log.d(FGDataSource.class.getName(), "Database on create.");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLES_DELETE);
        Log.d(FGDataSource.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(db);
    }


    /**
     * CRUD operations (create "store", read "search", update, delete)
     */

    // Books table name
    private static final String TABLE_PLAN = "plan";
    private static final String TABLE_DAILY = "daily";
    private static final String TABLE_EXSET = "exset";

    // Books Table Columns names
    private static final String PLAN_PID = "pid";
    private static final String PLAN_PNAME = "pname";
    private static final String PLAN_ACTIVE = "active";
    private static final String PLAN_MON = "mon";
    private static final String PLAN_TUE = "tue";
    private static final String PLAN_WED = "wed";
    private static final String PLAN_THU = "thu";
    private static final String PLAN_FRI = "fri";
    private static final String PLAN_SAT = "sat";
    private static final String PLAN_SUN = "sun";
    private static final String DAILY_DID = "did";
    private static final String DAILY_DATE = "date";
    private static final String DAILY_EID = "eid";
    private static final String DAILY_SETS = "numofsets";
    private static final String EXSET__SETID = "setid";
    private static final String EXSET__DID = "did";
    private static final String EXSET__QUANTITY = "quantity";
    private static final String EXSET__REPS = "numofreps";
    private static final String EXSET__COMPLETE = "complete";

    private static final String[] PLAN_COLUMNS = {PLAN_PID,PLAN_PNAME,PLAN_ACTIVE,PLAN_MON,PLAN_TUE,PLAN_WED,PLAN_THU,PLAN_FRI,PLAN_SAT,PLAN_SUN};
    private static final String[] DAILY_COLUMNS = {DAILY_DID,DAILY_DATE,DAILY_EID,DAILY_SETS};
    private static final String[] EXSET_COLUMNS = {EXSET__SETID,EXSET__DID,EXSET__QUANTITY,EXSET__REPS,EXSET__COMPLETE};

    /**
     * The methods storeDaily, storeExSet etc. are the methods to store records into database.
     * First, you need to make a new object    ( Daily tempDaily = new Daily(0,date,0,0) )
     * then store it   ( FGDataSource.storeDaily(tempDaily) )
     * You don't need to worry about the first parameter of each object(which is the auto increment primary key),
     * just type zero or any integer you like.
     */
    public static void storePlan(Plan p) {
        // 0. set log information
        Log.d("storePlan", Integer.toString(p.getID()));

        // 1. get reference to writable DB
        SQLiteDatabase db = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(PLAN_PID,p.getID());
        values.put(PLAN_PNAME, p.pname);
        values.put(PLAN_ACTIVE, p.active);
        values.put(PLAN_MON, p.Mon);
        values.put(PLAN_TUE, p.Tue);
        values.put(PLAN_WED, p.Wed);
        values.put(PLAN_THU, p.Thu);
        values.put(PLAN_FRI, p.Fri);
        values.put(PLAN_SAT, p.Sat);
        values.put(PLAN_SUN, p.Sun);

        // 3. insert
        db.insert(TABLE_PLAN, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


    public static void storeDaily (Daily d) {
        Log.d("storeDaily", Integer.toString(d.getID()));
        SQLiteDatabase db = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(DAILY_DID, d.getID());
        values.put(DAILY_DATE, d.date);
        values.put(DAILY_EID, d.eID);
        values.put(DAILY_SETS, d.numOfSets);

        db.insert(TABLE_DAILY, null, values);

        db.close();
    }

    public static void storeExSet (ExSet e) {
        Log.d("storeExSet", Integer.toString(e.getID()));
        SQLiteDatabase db = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(EXSET__SETID,e.getID());
        values.put(EXSET__DID, e.dID);
        values.put(EXSET__QUANTITY, e.quantity);
        values.put(EXSET__REPS, e.numOfReps);
        values.put(EXSET__COMPLETE, e.complete);

        db.insert(TABLE_EXSET, null, values);

        db.close();
    }

    /**
     * @return all the dates of existing daily records
     */
    public static ArrayList<String> searchAllDates() {
        ArrayList<String> rtn = new ArrayList<>();
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        String s = "SELECT DISTINCT date FROM daily;";
        Cursor cursor = database.rawQuery(s, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    rtn.add( cursor.getString(0) );
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
        return rtn;
    }

    /** TODO: this is for calendar
     * searchProgressByDate(): get the total sets & complete sets of the specific date
     * @param date (format: yyyy-MM-dd in String)
     * @return ArrayList< (int)total_sets, (int)complete_sets >
     */
    public static ArrayList<Integer> searchProgressByDate (String date) {

        ArrayList<Integer> rtn = new ArrayList<> ();
        int total_sets = 0;
        int complete_sets = 0;

        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        String s = "SELECT e.complete "
            + "FROM daily AS d, exset AS e "
            + "WHERE d.date = ? "
            + "AND d.did = e.did; " ;

        Cursor cursor = database.rawQuery(s, new String[]{date} );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getInt(0) == 1) {complete_sets+=1 ;}
                    total_sets +=1;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
        rtn.add(total_sets);
        rtn.add(complete_sets);
        return rtn;
    }


    /** TODO: this is for checklist/graph/history etc. And ExSets are already attached to Daily.
     * searchDailyByDate(): get Daily and ExSet of a specific date
     * @param date (format: yyyy-MM-dd in String)
     * @return An arraylist of class Daily, each Daily object attached an array of ExSet
     */
    public static ArrayList<Daily> searchDailyByDate (String date) {

        ArrayList<Daily> rtn = new ArrayList<> ();
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        // first build Daily
        String s = "SELECT d.did, d.eid, d.numofsets "
                + "FROM daily AS d "
                + "WHERE d.date = ? ";
        Cursor cursor = database.rawQuery(s, new String[]{date} );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int did = cursor.getInt(0);
                    int eid = cursor.getInt(1);
                    int num = cursor.getInt(2);
                    Daily daily = new Daily(did,date,eid,num);
                    rtn.add(daily);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        //then use did to get ExSet, and attach the ExSets to the Daily
        for (Daily daily: rtn) {
            ArrayList<ExSet> exSets = new ArrayList<>();
            int did = daily.dID;
            s = "SELECT setid, quantity, numofreps, complete "
                    + "FROM exset "
                    + "WHERE did = ? ";
            cursor = database.rawQuery(s, new String[]{ Integer.toString(did)} );
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int setid = cursor.getInt(0);
                        int quantity = cursor.getInt(1);
                        int numofreps = cursor.getInt(2);
                        int complete = cursor.getInt(3);
                        ExSet exSet = new ExSet(setid,did,quantity,numofreps,complete);
                        exSets.add(exSet);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            Daily.attachDaily(daily,exSets);
        }

        database.close();
        return rtn;
    }


    /**
     * delete Daily and the attached ExSets of the specific date and eid,
     * @param date
     * @param eid if eid== -1, delete all the dailies of this date
     */
    public static void deleteDaily(String date, int eid){
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        // first search all the dID
        ArrayList<Integer> dIDs = new ArrayList<>();
        String s;
        if (eid == -1) {
            s = "SELECT did FROM daily WHERE date = ? ;";
        } else {
            s = "SELECT did FROM daily WHERE date = ? AND eid = " + Integer.toString(eid) +" ;";
        }
        Cursor cursor = database.rawQuery(s, new String[]{date} );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    dIDs.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        // then search all the setID
        ArrayList<Integer> setIDs = new ArrayList<>();
        s = "SELECT setid FROM exset WHERE did = ? ;";
        cursor = database.rawQuery(s, new String[]{date} );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    dIDs.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
    }

    /** TODO: focus on the format of each parameter, and PLEASE use String.valueOf( someString ) instead of just someString
     * dbDelte(): delete a specific row with your arguments
     * @param table_name "plan" or "daily" or "exset" (LOWER CASE!!!)
     * @param whereClause such as "pid" "pname" "date" etc
     * @param whereArgs String[] { String.valueOf(date) } or String[]{ Integer.toString(setID) }
     */
    public static void dbDelete(String table_name, String whereClause, String[] whereArgs) {
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getWritableDatabase();
        database.delete(table_name, whereClause+" =? ", whereArgs);
        database.close();
    }


    /**
     * dbUpdate():
     * @param table_name same as above
     * @param whereClause same as above
     * @param whereArgs same as above
     * @param values: ContentValues, here is an example
     *
     *              ContentValues data=new ContentValues();
     *              data.put("Field1","bob");
     *              data.put("Field2",19);
     *              data.put("Field3","male");
     */
    public static void dbUpdate(String table_name, ContentValues values, String whereClause, String[] whereArgs){
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getWritableDatabase();
        database.update(table_name, values, whereClause + " =? ", whereArgs);
        database.close();
    }


    public static void deleteAll() {
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getWritableDatabase();
        database.execSQL("delete from "+ TABLE_PLAN);
        database.execSQL("delete from " + TABLE_DAILY);
        database.execSQL("delete from " + TABLE_EXSET);
        database.close();
    }
}
