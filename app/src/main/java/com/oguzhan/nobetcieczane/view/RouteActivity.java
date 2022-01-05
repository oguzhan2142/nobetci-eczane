package com.oguzhan.nobetcieczane.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.utils.Config;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.PointOfView;
import com.yandex.mapkit.mapview.MapView;



public class RouteActivity extends AppCompatActivity {

    public final static String latitudeBundleTag = "Latitude";
    public final static String longitudeBundleTag = "longitude";
    private static final String TAG = "RouteActivity";
    private MapView mapview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_route);


        double latitudeOfPharmacy = getIntent().getDoubleExtra(latitudeBundleTag, 0);
        double longitudeOfPharmacy = getIntent().getDoubleExtra(longitudeBundleTag,0);;

        Log.d(TAG, "onCreate:  " + latitudeOfPharmacy);
        Log.d(TAG, "onCreate:  " + longitudeOfPharmacy);


        mapview = (MapView)findViewById(R.id.mapview);




        mapview.getMap().addTapListener(new GeoObjectTapListener() {
            @Override
            public boolean onObjectTap(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
                String name = geoObjectTapEvent.getGeoObject().getName();


                Log.d("TAG", "onObjectTap: " + name);
                return true;
            }
        });
        mapview.setFocusPoint(new ScreenPoint((float)latitudeOfPharmacy,(float)longitudeOfPharmacy));

        mapview.getMap().move(
                new CameraPosition(new Point(latitudeOfPharmacy, longitudeOfPharmacy), 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }

    @Override
    protected void onDestroy() {
        MapKitFactory.getInstance().onStop();
        Log.d("TAG", "onDestroy: destroyed");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }
}