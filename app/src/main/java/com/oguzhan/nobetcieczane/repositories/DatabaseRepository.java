package com.oguzhan.nobetcieczane.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.oguzhan.nobetcieczane.database.LocalDatabase;
import com.oguzhan.nobetcieczane.database.LocalDatabaseHelper;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.model.Pharmacy;

public class DatabaseRepository {

    private SQLiteDatabase db;
    public DatabaseRepository(Context applicationContext) {
        LocalDatabaseHelper helper = new LocalDatabaseHelper(applicationContext);
        db = helper.getWritableDatabase();
    }






    public void insertAppEntryLog(){
        db.execSQL("insert into " + LocalDatabase.EnterLog.TABLE_NAME + " DEFAULT VALUES");
    }


    public void insertNavigationLog(NosyPharmacy pharmacy){
        ContentValues navValues = new ContentValues();
        navValues.put(LocalDatabase.NavigationLog.COLUMN_NAME_LONGITUDE, pharmacy.getLongitude());
        navValues.put(LocalDatabase.NavigationLog.COLUMN_NAME_LATITUDE, pharmacy.getLatitude());
        navValues.put(LocalDatabase.NavigationLog.COLUMN_NAME_PHARMACY_NAME, pharmacy.getName());
        navValues.put(LocalDatabase.NavigationLog.COLUMN_NAME_PHARMACY_ADDRESS, pharmacy.getAddress());
        db.insert(LocalDatabase.NavigationLog.TABLE_NAME, null, navValues);
    }



}
