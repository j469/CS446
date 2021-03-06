package com.fitgoose.fitgoosedemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fitgoose.fitgoosedemo.MyDate;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
    private static final int DATABASE_VERSION = 7;

    /**
     * initial database etc.
     */

    private static final String EXERCISE_CREATE = "CREATE TABLE IF NOT EXISTS exercise ( "
            + "eid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "type INTEGER NOT NULL, "
            + "name TEXT NOT NULL UNIQUE, "
            + "secondUnit INTEGER NOT NULL, "
            + "unit1 TEXT NOT NULL, "
            + "unit2 TEXT NOT NULL, "
            + "shoulder INTEGER NOT NULL, "
            + "chest INTEGER NOT NULL, "
            + "abs INTEGER NOT NULL, "
            + "upper_arm INTEGER NOT NULL, "
            + "forearm INTEGER NOT NULL, "
            + "quads INTEGER NOT NULL, "
            + "calves INTEGER NOT NULL, "
            + "back INTEGER NOT NULL, "
            + "cardio INTEGER NOT NULL, "
            + "youtube TEXT NOT NULL "
            + "); " ;

    private static final String RP_CREATE = "CREATE TABLE IF NOT EXISTS rp ( "
            + "rpid INTEGER PRIMARY KEY AUTOINCREMENT, "
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

    private static final String PLAN_CREATE = "CREATE TABLE IF NOT EXISTS plan ( "
            + "pid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "date INTEGER NOT NULL, "
            + "eid INTEGER NOT NULL, "
            + "numofsets INTEGER NOT NULL "
            + "); " ;

    private static final String EXSET_CREATE = "CREATE TABLE IF NOT EXISTS exset ( "
            + "setid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "pid INTEGER NOT NULL, "
            + "quantity1 INTEGER NOT NULL, "
            + "quantity2 INTEGER NOT NULL "
            + ");" ;

    private static final String REGIMEN_CREATE = "CREATE TABLE IF NOT EXISTS regimen ( "
            + "rid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "rname TEXT NOT NULL UNIQUE"
            + ");" ;

    private static final String REXREF_CREATE = "CREATE TABLE IF NOT EXISTS rexref ( "
            + "refid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "rid INTEGER NOT NULL, "
            + "eid INTEGER NOT NULL, "
            + "numofreps INTEGER NOT NULL "
            + ");" ;

    private static final String RPE_CREATE = "CREATE TABLE IF NOT EXISTS rpe ( "
            + "rpeid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "rpid INTEGER NOT NULL, "
            + "eid INTEGER NOT NULL, "
            + "numofreps INTEGER NOT NULL "
            + ");" ;

    // Delete tables
    private static final String TABLES_DELETE = " DROP TABLE IF EXISTS exercise; "
            + " DROP TABLE IF EXISTS rp; "
            + " DROP TABLE IF EXISTS plan; "
            + " DROP TABLE IF EXISTS exset; "
            + " DROP TABLE IF EXISTS regimen; "
            + " DROP TABLE IF EXISTS rexref; "
            + " DROP TABLE IF EXISTS rpe; ";



    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(EXERCISE_CREATE);
        database.execSQL(RP_CREATE);
        database.execSQL(PLAN_CREATE);
        database.execSQL(EXSET_CREATE);
        database.execSQL(REGIMEN_CREATE);
        database.execSQL(REXREF_CREATE);
        database.execSQL(RPE_CREATE);
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
    private static final String TABLE_EXERCISE= "exercise";
    private static final String TABLE_PLAN = "plan";
    private static final String TABLE_RP = "rp";
    private static final String TABLE_EXSET = "exset";
    private static final String TABLE_REGIMEN = "regimen";
    private static final String TABLE_REXREF = "rexref";
    private static final String TABLE_RPE = "rpe";

    // Books Table Columns names
    private static final String RP_PID = "rpid";
    private static final String RP_PNAME = "pname";
    private static final String RP_ACTIVE = "active";
    private static final String RP_MON = "mon";
    private static final String RP_TUE = "tue";
    private static final String RP_WED = "wed";
    private static final String RP_THU = "thu";
    private static final String RP_FRI = "fri";
    private static final String RP_SAT = "sat";
    private static final String RP_SUN = "sun";
    private static final String PLAN_PID = "pid";
    private static final String PLAN_DATE = "date";
    private static final String PLAN_EID = "eid";
    private static final String PLAN_SETS = "numofsets";
    private static final String EXSET__SETID = "setid";
    private static final String EXSET__PID = "pid";
    private static final String EXSET__QUANTITY1 = "quantity1";
    private static final String EXSET__QUANTITY2 = "quantity2";
    private static final String EXERCISE_EID = "eid";
    private static final String EXERCISE_TYPE = "type";
    private static final String EXERCISE_NAME = "name";
    private static final String EXERCISE_SU = "secondUnit";
    private static final String EXERCISE_UNIT1 = "unit1";
    private static final String EXERCISE_UNIT2 = "unit2";
    private static final String EXERCISE_SHOULDER = "shoulder";
    private static final String EXERCISE_CHEST = "chest";
    private static final String EXERCISE_ABS = "abs";
    private static final String EXERCISE_UPPER_ARM = "upper_arm";
    private static final String EXERCISE_FOREARM = "forearm";
    private static final String EXERCISE_QUADS = "quads";
    private static final String EXERCISE_CALVES = "calves";
    private static final String EXERCISE_BACK = "back";
    private static final String EXERCISE_CARDIO = "cardio";
    private static final String EXERCISE_YOUTUBE = "youtube";
    private static final String REGIMEN_RID = "rid";
    private static final String REGIMEN_RNAME = "rname";
    private static final String REXREF_REFID = "refid";
    private static final String REXREF_RID = "rid";
    private static final String REXREF_EID = "eid";
    private static final String REXREF_SETS = "numofsets";
    private static final String RPE_RPEID = "rpeid";
    private static final String RPE_RPID = "rpid";
    private static final String RPE_EID = "eid";
    private static final String RPE_SETS = "numofsets";

    private static final String[] RP_COLUMNS = {RP_PID,RP_PNAME,RP_ACTIVE,RP_MON,RP_TUE,RP_WED,RP_THU,RP_FRI,RP_SAT,RP_SUN};
    private static final String[] PLAN_COLUMNS = {PLAN_PID,PLAN_DATE,PLAN_EID,PLAN_SETS};
    private static final String[] EXSET_COLUMNS = {EXSET__SETID,EXSET__PID,EXSET__QUANTITY1,EXSET__QUANTITY2};

    /**
     * The methods storeDaily, storeExSet etc. are the methods to store records into database.
     * First, you need to make a new object    ( Plan tempDaily = new Plan(0,date,0,0) )
     * then store it   ( FGDataSource.storeDaily(tempDaily) )
     * You don't need to worry about the first parameter of each object(which is the auto increment primary key),
     * just type zero or any integer you like.
     */
    public static void storeRP(RepeatPlan rp) {
        // 0. set log information
        Log.d("storeRP", rp.pname);

        // 1. get reference to writable DB
        SQLiteDatabase db = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(RP_PNAME, rp.pname);
        values.put(RP_ACTIVE, rp.active);
        values.put(RP_MON, rp.Mon);
        values.put(RP_TUE, rp.Tue);
        values.put(RP_WED, rp.Wed);
        values.put(RP_THU, rp.Thu);
        values.put(RP_FRI, rp.Fri);
        values.put(RP_SAT, rp.Sat);
        values.put(RP_SUN, rp.Sun);

        // 3. insert
        db.insert(TABLE_RP, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. store to RPExerciseXRef
        int rpID = 0;
        String s = "SELECT rpid FROM rp WHERE pname = ? ;";
        Cursor cursor = db.rawQuery(s, new String[]{rp.pname});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                rpID = cursor.getInt(0);
            }
            cursor.close();
        }

        // then store to TABLE_RPE
        for (int i = 0; i < rp.eIDs.size(); i++) {
            int eID = rp.eIDs.get(i);
            int numOfSets = rp.sets.get(i);
            ContentValues temp = new ContentValues();
            temp.put(RPE_RPID, rpID);
            temp.put(RPE_EID, eID);
            temp.put(RPE_SETS, numOfSets);
            db.insert(TABLE_RPE, null, temp);
        }


        db.close();
    }

    public static void storeExercise(Exercise e) {
        Log.d("storeExercise", Integer.toString(e.eID));

        SQLiteDatabase db = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXERCISE_TYPE, 1);
        values.put(EXERCISE_NAME, e.name);
        values.put(EXERCISE_SU, e.secondUnit);
        values.put(EXERCISE_UNIT1, e.unit1);
        values.put(EXERCISE_UNIT2, e.unit2);
        values.put(EXERCISE_SHOULDER, e.shoulder);
        values.put(EXERCISE_CHEST, e.chest);
        values.put(EXERCISE_ABS, e.abs);
        values.put(EXERCISE_UPPER_ARM, e.upper_arm);
        values.put(EXERCISE_FOREARM, e.forearm);
        values.put(EXERCISE_QUADS, e.quads);
        values.put(EXERCISE_CALVES, e.calves);
        values.put(EXERCISE_BACK, e.back);
        values.put(EXERCISE_CARDIO, e.cardio);
        values.put(EXERCISE_YOUTUBE, e.youtubeURL);

        db.insert(TABLE_EXERCISE,null,values);

        db.close();
    }


    public static void storePlan (Plan p) {
        Log.d("storePlan", Integer.toString(p.pID));
        SQLiteDatabase db = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLAN_DATE, p.date.getTimeStamp());
        values.put(PLAN_EID, p.eID);
        values.put(PLAN_SETS, p.numOfSets);

        db.insert(TABLE_PLAN, null, values);

        db.close();
    }

    public static void storeExSet (ExSet e) {
        Log.d("storeExSet", Integer.toString(e.setID));
        SQLiteDatabase db = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXSET__PID, e.pID);
        values.put(EXSET__QUANTITY1, e.quantity1);
        values.put(EXSET__QUANTITY2, e.quantity2);

        db.insert(TABLE_EXSET, null, values);

        db.close();
    }

    public static void storeRegimen (Regimen r) {
        Log.d("storeRegimen", r.rname);
        SQLiteDatabase db = getInstance(mContext.getApplicationContext()).getWritableDatabase();

        // first store to regimen table
        ContentValues values = new ContentValues();
        values.put(REGIMEN_RNAME, r.rname);
        db.insert(TABLE_REGIMEN, null, values);

        // then get corresponding rID
        int rID = r.rID;
        if (rID == 0) {
            String s = "SELECT rid FROM regimen WHERE rname = ? ;";
            Cursor cursor = db.rawQuery(s, new String[]{r.rname});
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    rID = cursor.getInt(0);
                }
                cursor.close();
            }
        }

        // then store to TABLE_REXREF
        for (int i = 0; i < r.eIDs.size(); i++) {
            int eID = r.eIDs.get(i);
            int numOfSets = r.sets.get(i);
            ContentValues temp = new ContentValues();
            temp.put(REXREF_RID, rID);
            temp.put(REXREF_EID, eID);
            temp.put(REXREF_SETS, numOfSets);
            db.insert(TABLE_REXREF, null, temp);
        }

        db.close();
    }

    /**
     * call this method whenever start the app
     */
    public static void cacheExercise() {
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();
        GlobalVariables.storedExercises.clear();

        // read custom exercise
        String s = " SELECT * FROM exercise;";
        Cursor cursor = database.rawQuery(s, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Exercise e = new Exercise(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3),
                            cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),
                            cursor.getInt(9),cursor.getInt(10),cursor.getInt(11),cursor.getInt(12),cursor.getInt(13),
                            cursor.getInt(14),"");
                    GlobalVariables.storedExercises.add(e);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        // read default exercise
        try {
            File yourFile = new File(mContext.getFilesDir(), "/default.json");
            FileInputStream stream = new FileInputStream(yourFile);
            String jsonStr = null;
            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                jsonStr = Charset.defaultCharset().decode(bb).toString();
            }
            finally {
                stream.close();
            }

            JSONObject jsonObj = new JSONObject(jsonStr);

            // Getting data JSON Array nodes
            JSONArray data  = jsonObj.getJSONArray("exercise");

            // looping through All nodes
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                String name = c.getString("name");
                boolean secondUnit = c.getBoolean("secondUnit");
                int unit1 = c.getInt("unit1");
                int unit2 = c.getInt("unit2");
                boolean shoulder = c.getBoolean("shoulder");
                boolean chest = c.getBoolean("chest");
                boolean abs = c.getBoolean("abs");
                boolean uarm = c.getBoolean("uarm");
                boolean farm = c.getBoolean("farm");
                boolean quads = c.getBoolean("quads");
                boolean calves = c.getBoolean("calves");
                boolean back = c.getBoolean("back");
                boolean cardio = c.getBoolean("cardio");
                String url = c.getString("url");

                Exercise e = new Exercise(10000+i,false,name,secondUnit,unit1,unit2,shoulder,chest,
                        abs,uarm,farm,quads,calves,back,cardio,url);
                GlobalVariables.storedExercises.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        database.close();
    }

    /**
     * @return all the dates of existing plan records
     */
    public static ArrayList<Calendar> searchAllDates() {
        ArrayList<Calendar> rtn = new ArrayList<>();
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        String s = " SELECT DISTINCT date FROM plan;";
        Cursor cursor = database.rawQuery(s, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Calendar newDate = Calendar.getInstance();
                    newDate.setTimeInMillis(cursor.getLong(0));
                    rtn.add(newDate);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
        return rtn;
    }

    /** TODO: this is for calendar
     * searchProgressByDate(): get the total sets & complete sets of the specific date
     * @param date represented by a Calendar object
     * @return ArrayList< (int)total_sets, (int)complete_sets >
     */
    public static ArrayList<Integer> searchProgressByDate (String date) {

        ArrayList<Integer> rtn = new ArrayList<> ();
        int total_sets = 0;
        int complete_sets = 0;

        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        String s = " SELECT numofsets "
                + " FROM plan "
                + " WHERE date = ? ";

        Cursor cursor = database.rawQuery(s, new String[]{date} );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    total_sets += cursor.getInt(0);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        s = " SELECT COUNT(*) FROM plan AS p, exset AS e "
                + " WHERE p.date = ? "
                + " AND p.pid = e.pid; " ;

        cursor = database.rawQuery(s, new String[]{date} );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    complete_sets += cursor.getInt(0);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        database.close();
        rtn.add(total_sets);
        rtn.add(complete_sets);
        return rtn;
    }


    /** TODO: this is for checklist/graph/history etc. And ExSets are already attached to Plan.
     * searchPlanByDate(): get Plan and ExSet of a specific date
     * @param date represented by a Calendar object
     * @return An arraylist of class Plan, each Plan object attached an array of ExSet
     */
    public static ArrayList<Plan> searchPlanByDate(MyDate date) {

        ArrayList<Plan> rtn = new ArrayList<> ();
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        // first build Plan
        String s = " SELECT pid, eid, numofsets "
                + " FROM plan"
                + " WHERE date = ? ";

        Cursor cursor = database.rawQuery(s, new String[]{Integer.toString(date.getTimeStamp())} );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int pid = cursor.getInt(0);
                    int eid = cursor.getInt(1);
                    int num = cursor.getInt(2);
                    Plan plan = new Plan(pid,date,eid,num);
                    rtn.add(plan);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        //then use pid to get ExSet, and attach the ExSets to the Plan
        for (Plan plan : rtn) {
            ArrayList<ExSet> exSets = new ArrayList<>();
            int pid = plan.pID;
            s = " SELECT setid, quantity1, quantity2"
                    + " FROM exset "
                    + " WHERE pid = ? ";
            cursor = database.rawQuery(s, new String[]{ Integer.toString(pid)} );
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int setid = cursor.getInt(0);
                        int quantity1 = cursor.getInt(1);
                        int quantity2 = cursor.getInt(2);
                        ExSet exSet = new ExSet(setid,pid,quantity1,quantity2);
                        exSets.add(exSet);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            plan.attachExSets(exSets);
        }

        database.close();
        return rtn;
    }

    public static ArrayList<Plan> searchPlanByExerciseAndTimeRange(int eid, MyDate before,
                                                                MyDate after) {
        ArrayList<Plan> planList = new ArrayList<>();
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        String s = " SELECT pid, date, eid, numofsets "
                + " FROM plan"
                + " WHERE eid = ? "
                + " AND date >= ? "
                + " AND date <= ? ";

        Cursor cursor = database.rawQuery(s, new String[]{
                Integer.toString(eid),
                Integer.toString(before.getTimeStamp()),
                Integer.toString(after.getTimeStamp()) });

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int cPid = cursor.getInt(0);
                    int cDate = cursor.getInt(1);
                    int cEid = cursor.getInt(2);
                    int cNum = cursor.getInt(3);

                    Plan plan = new Plan(cPid, new MyDate(cDate),cEid,cNum);
                    planList.add(plan);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        //then use pid to get ExSet, and attach the ExSets to the Plan
        for (Plan plan : planList) {
            ArrayList<ExSet> exSetList = new ArrayList<>();
            int pid = plan.pID;
            s = " SELECT setid, quantity1, quantity2 "
                    + " FROM exset "
                    + " WHERE pid = ? ";
            cursor = database.rawQuery(s, new String[]{ Integer.toString(pid)} );
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int setid = cursor.getInt(0);
                        int quantity1 = cursor.getInt(1);
                        int quantity2 = cursor.getInt(2);
                        ExSet exSet = new ExSet(setid,pid,quantity1,quantity2);
                        exSetList.add(exSet);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            plan.attachExSets(exSetList);
        }

        database.close();

        return planList;
    }

    public static MyDate searchEarliestPlanDate() {
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        String s = " SELECT MIN(date) "
                + " FROM plan ";
        Cursor cursor = database.rawQuery(s, null);
        int earliest = -1;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                earliest = cursor.getInt(0);
            }
            cursor.close();
        }

        database.close();
        return new MyDate(earliest);
    }

    public static ArrayList<Regimen> searchAllRegimen() {
        ArrayList<Regimen> regimenArrayList = new ArrayList<>();
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getReadableDatabase();

        String s = " SELECT rid, rname "
                + " FROM regimen";

        Cursor cursor = database.rawQuery(s, new String[]{});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int rid = cursor.getInt(0);
                    String rname = cursor.getString(1);

                    Regimen regimen = new Regimen(rid,rname);
                    regimenArrayList.add(regimen);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        //then use pid to get ExSet, and attach the ExSets to the Plan
        for (Regimen regimen : regimenArrayList) {
            int rid = regimen.rID;
            s = " SELECT eid, numofreps "
                    + " FROM rexref "
                    + " WHERE rid = ? ";
            cursor = database.rawQuery(s, new String[]{ Integer.toString(rid)} );
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int eid = cursor.getInt(0);
                        int numofreps = cursor.getInt(1);
                        regimen.attachEID(eid);
                        regimen.attachNumOfSet(numofreps);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }

        database.close();

        return regimenArrayList;
    }

    public static int deleteExercise(int eid){
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getWritableDatabase();
        if (eid < 10000) {
            database.delete(TABLE_EXERCISE, EXERCISE_EID + "=?", new String[]{ Integer.toString(eid)} );
            database.close();
            cacheExercise();
            return 1;
        }
        database.close();
        return 0;
    }


    /**
     * delete Plan and the attached ExSets of the specific date and eid,
     * @param date represented by a Calendar object
     * @param eid if eid== -1, delete all the plans of this date
     */
    public static void deletePlan(MyDate date, int eid){
        SQLiteDatabase database = getInstance(mContext.getApplicationContext()).getWritableDatabase();
        // first search all the pID
        ArrayList<Integer> pIDs = new ArrayList<>();
        String s;
        Cursor cursor;
        if (eid < 0) {
            s = "SELECT pid FROM plan WHERE date = ? ;";
            cursor = database.rawQuery(s, new String[]{Integer.toString(date.getTimeStamp())});
        } else {
            s = "SELECT pid FROM plan WHERE date = ? AND eid = ? ;";
            cursor = database.rawQuery(s, new String[]{Integer.toString(date.getTimeStamp()),
                    Integer.toString(eid)});
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    pIDs.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        // then delete all the Plan and ExSets
        for (int pid: pIDs) {
            database.delete(TABLE_EXSET, EXSET__PID + "=?", new String[]{ Integer.toString(pid)} );
            database.delete(TABLE_PLAN, PLAN_PID + "=?", new String[]{ Integer.toString(pid)} );
        }

        database.close();
    }

    /** TODO: focus on the format of each parameter, and PLEASE use String.valueOf( someString ) instead of just someString
     * dbDelte(): delete a specific row with your arguments
     * @param table_name "plan" or "daily_card_header_menu" or "exset" (LOWER CASE!!!)
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
        database.execSQL("delete from " + TABLE_RP);
        database.execSQL("delete from " + TABLE_EXSET);
        database.execSQL("delete from " + TABLE_REGIMEN);
        database.execSQL("delete from " + TABLE_REXREF);
        database.close();
    }
}
