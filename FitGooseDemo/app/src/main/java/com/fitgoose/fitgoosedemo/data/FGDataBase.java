package com.fitgoose.fitgoosedemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** FGDataBase:
 * This class is responsible for creating the database.
 * jerry 2015/6/1
 */

public class FGDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FitGoose.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation
/*    private static final String EXERCISE_CREATE = "CREATE TABLE exercise ( "
            + "eid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "type INTEGER NOT NULL, "
            + "ename TEXT PRIMARY KEY NOT NULL, "
            + "unit TEXTNOT NULL, "
            + "shoulder INTEGER NOT NULL, "
            + "arms INTEGER NOT NULL, "
            + "back INTEGER NOT NULL, "
            + "chest INTEGER NOT NULL, "
            + "abs INTEGER NOT NULL, "
            + "legs INTEGER NOT NULL, "
            + "oxy INTEGER NOT NULL, "
            + "cardio INTEGER NOT NULL "
            + "secondUnit INTEGER NOT NULL "
            + ");" ;
*/
    private static final String PLAN_CREATE = "CREATE TABLE plan ( "
            + "pid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "date TEXT NOT NULL, "
            + "pname TEXT PRIMARY KEY NOT NULL"
            + ");" ;

    private static final String EXPLANXREF_CREATE = "CREATE TABLE explanxref ( "
            + "eprid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "pid INTEGER NOT NULL, "
            + "eid INTEGER NOT NULL, "
            + "numofsets INTEGER NOT NULL "
            + ");" ;

    private static final String EXSET_CREATE = "CREATE TABLE exset ( "
            + "setid INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "epr INTEGER NOT NULL, "
            + "quantity INTEGER NOT NULL, "
            + "numofreps INTEGER NOT NULL, "
            + "complete INTEGER NOT NULL "
            + ");" ;

    // Delete tables
    private static final String TABLES_DELETE = " DROP TABLE IF EXISTS plan;"
            + " DROP TABLE IF EXISTS explanxref;"
            + " DROP TABLE IF EXISTS exset;" ;

    // Constructor
    public FGDataBase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        // First create tables
        //database.execSQL(EXERCISE_CREATE);
        database.execSQL(PLAN_CREATE);
        database.execSQL(EXPLANXREF_CREATE);
        database.execSQL(EXSET_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FGDataBase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL(TABLES_DELETE);
        onCreate(db);
    }
}
