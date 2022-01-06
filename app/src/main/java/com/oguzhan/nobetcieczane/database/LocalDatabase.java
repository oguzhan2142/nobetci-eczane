package com.oguzhan.nobetcieczane.database;

import android.provider.BaseColumns;

public final class LocalDatabase {




    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private LocalDatabase() {}

    /* Inner class that defines the table contents */
    public static class EnterLog implements BaseColumns{
        public static final String TABLE_NAME = "enter_log";
        public static final String COLUMN_NAME_TIMESTAMP = "Timestamp";


    }



    public static class NavigationLog implements BaseColumns{
        public static final String TABLE_NAME = "navigation_log";
        public static final String COLUMN_NAME_PHARMACY_NAME = "name";
        public static final String COLUMN_NAME_PHARMACY_ADDRESS = "address";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";

    }





}

