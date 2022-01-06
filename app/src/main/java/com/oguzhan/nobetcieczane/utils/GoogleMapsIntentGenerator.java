package com.oguzhan.nobetcieczane.utils;

import android.content.Intent;
import android.net.Uri;

abstract public class GoogleMapsIntentGenerator {

    public static Intent create(double latitude,double longitude){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude + "&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }



}
