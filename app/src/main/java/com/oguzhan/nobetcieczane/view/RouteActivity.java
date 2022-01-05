package com.oguzhan.nobetcieczane.view;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.utils.Config;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class RouteActivity extends AppCompatActivity {


    private MapView mapview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_route);




        mapview = (MapView)findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
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