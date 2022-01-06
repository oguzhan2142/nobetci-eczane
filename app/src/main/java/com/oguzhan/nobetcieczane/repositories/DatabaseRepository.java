package com.oguzhan.nobetcieczane.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.oguzhan.nobetcieczane.database.LocalDatabase;
import com.oguzhan.nobetcieczane.database.LocalDatabaseHelper;
import com.oguzhan.nobetcieczane.model.DbAppEntryLog;
import com.oguzhan.nobetcieczane.model.DbNavigationLog;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.model.Pharmacy;

public class DatabaseRepository {

    private SQLiteDatabase db;

    public DatabaseRepository(Context applicationContext) {
        LocalDatabaseHelper helper = new LocalDatabaseHelper(applicationContext);
        db = helper.getWritableDatabase();
    }


    public DbNavigationLog[] getNavigationLogs() {

        Cursor cursor = db.rawQuery("select * from " + LocalDatabase.NavigationLog.TABLE_NAME + " order by " + LocalDatabase.NavigationLog.COLUMN_NAME_TIMESTAMP + " DESC"
                , new String[]{});

        DbNavigationLog[] logs = new DbNavigationLog[cursor.getCount()];

        int counter = 0;
        while (cursor.moveToNext()) {
            DbNavigationLog log = new DbNavigationLog();
            log.setId(cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabase.NavigationLog._ID)));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(LocalDatabase.NavigationLog.COLUMN_NAME_TIMESTAMP));
            log.setDate(date);
            log.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(LocalDatabase.NavigationLog.COLUMN_NAME_LATITUDE)));
            log.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(LocalDatabase.NavigationLog.COLUMN_NAME_LONGITUDE)));
            log.setName(cursor.getString(cursor.getColumnIndexOrThrow(LocalDatabase.NavigationLog.COLUMN_NAME_PHARMACY_NAME)));
            log.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(LocalDatabase.NavigationLog.COLUMN_NAME_PHARMACY_ADDRESS)));

            logs[counter] = log;
            counter++;

        }

        return logs;

    }

    public DbAppEntryLog[] getEntryLogs() {
        Cursor cursor = db.rawQuery("select * from " + LocalDatabase.EnterLog.TABLE_NAME + " order by " + LocalDatabase.EnterLog.COLUMN_NAME_TIMESTAMP + " DESC"
                , new String[]{});

        DbAppEntryLog[] logs = new DbAppEntryLog[cursor.getCount()];

        int counter = 0;
        while (cursor.moveToNext()) {
            DbAppEntryLog log = new DbAppEntryLog();
            log.setId(cursor.getInt(cursor.getColumnIndexOrThrow(LocalDatabase.EnterLog._ID)));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(LocalDatabase.EnterLog.COLUMN_NAME_TIMESTAMP));
            log.setDate(date);
            logs[counter] = log;
            counter++;


        }

        return logs;
    }

    public void insertAppEntryLog() {
        db.execSQL("insert into " + LocalDatabase.EnterLog.TABLE_NAME + " DEFAULT VALUES");
    }


    public void insertNavigationLog(NosyPharmacy pharmacy) {
        ContentValues navValues = new ContentValues();
        navValues.put(LocalDatabase.NavigationLog.COLUMN_NAME_LONGITUDE, pharmacy.getLongitude());
        navValues.put(LocalDatabase.NavigationLog.COLUMN_NAME_LATITUDE, pharmacy.getLatitude());
        navValues.put(LocalDatabase.NavigationLog.COLUMN_NAME_PHARMACY_NAME, pharmacy.getName());
        navValues.put(LocalDatabase.NavigationLog.COLUMN_NAME_PHARMACY_ADDRESS, pharmacy.getAddress());
        db.insert(LocalDatabase.NavigationLog.TABLE_NAME, null, navValues);
    }


}
