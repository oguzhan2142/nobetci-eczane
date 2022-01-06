package com.oguzhan.nobetcieczane.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHelper extends SQLiteOpenHelper {

//    private static final String SQL_CREATE_ENTER_LOG =
//            "CREATE TABLE " + LocalDatabase.EnterLog.TABLE_NAME + " (" +
//                    LocalDatabase.EnterLog._ID + " INTEGER PRIMARY KEY," +
//                    LocalDatabase.EnterLog.COLUMN_NAME_LATITUDE + " REAL," +
//                    LocalDatabase.EnterLog.COLUMN_NAME_LONGITUDE + " REAL," +
//                    LocalDatabase.EnterLog.COLUMN_NAME_DATE + " TEXT)";
//

    private static final String SQL_CREATE_ENTER_LOG =
            "CREATE TABLE " + LocalDatabase.EnterLog.TABLE_NAME + " (" +
                    LocalDatabase.EnterLog._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

    private static final String SQL_CREATE_NAVIGATION_LOG =
            "CREATE TABLE " + LocalDatabase.NavigationLog.TABLE_NAME + " (" +
                    LocalDatabase.NavigationLog._ID + " INTEGER PRIMARY KEY,"
                    + LocalDatabase.NavigationLog.COLUMN_NAME_PHARMACY_NAME + " TEXT," +
                    LocalDatabase.NavigationLog.COLUMN_NAME_PHARMACY_ADDRESS + " TEXT," +
                    LocalDatabase.NavigationLog.COLUMN_NAME_LATITUDE + " REAL," +
                    LocalDatabase.NavigationLog.COLUMN_NAME_LONGITUDE + " REAL," +
                    "Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";


    private static final String SQL_DELETE_ENTER_LOG =
            "DROP TABLE IF EXISTS " + LocalDatabase.EnterLog.TABLE_NAME;
    private static final String SQL_DELETE_NAVIGATION_LOG =
            "DROP TABLE IF EXISTS " + LocalDatabase.EnterLog.TABLE_NAME;



    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NobetciEczane.db";

    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTER_LOG);
        db.execSQL(SQL_CREATE_NAVIGATION_LOG);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL(SQL_DELETE_ENTER_LOG);
        db.execSQL(SQL_DELETE_NAVIGATION_LOG);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
