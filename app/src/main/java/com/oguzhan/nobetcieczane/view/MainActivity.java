package com.oguzhan.nobetcieczane.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.adapters.PharmacyAdapter;
import com.oguzhan.nobetcieczane.components.AreaFilterDropdown;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.utils.Config;
import com.oguzhan.nobetcieczane.viewmodel.MainActivityViewModel;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main activity";
    private static final long LOCATION_REFRESH_TIME = 3;
    private static final float LOCATION_REFRESH_DISTANCE = 1;


    private AreaFilterDropdown areaFilterDropdown;
    private RecyclerView pharmaciesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapKitFactory.setApiKey(Config.yandexMapKitAPIKey);
        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


        areaFilterDropdown = findViewById(R.id.area_filter);
        pharmaciesRecyclerView = findViewById(R.id.pharmacies_list);
        pharmaciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(this, viewModel.pharmacies);
        pharmaciesRecyclerView.setAdapter(pharmacyAdapter);


        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, location -> {
                    Log.d(TAG, "onCreate: location updated with " + location.getLatitude() + " : " + location.getLongitude());
                    viewModel.updateUserLocation(location.getLatitude(), location.getLongitude());
                    pharmacyAdapter.updateLocation(location.getLatitude(), location.getLongitude());
                });

        viewModel.cities.observe(this, cities -> {
            if (cities != null) {
                areaFilterDropdown.setCities(cities);
            }

        });

        viewModel.counties.observe(this, counties -> {
            if (counties != null) {
                areaFilterDropdown.setCounties(counties);
            }
        });

        areaFilterDropdown.setOnCitySelectedListener(viewModel::onCitySelected);
        areaFilterDropdown.setOnCountySelectedListener(viewModel::onCountySelected);

        viewModel.pharmacies.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<NosyPharmacy>>() {
            @Override
            public void onChanged(ObservableList<NosyPharmacy> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<NosyPharmacy> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<NosyPharmacy> sender, int positionStart, int itemCount) {
                Log.d(TAG, "onItemRangeInserted: itemler iserted");
                runOnUiThread(() -> pharmacyAdapter.notifyItemRangeChanged(positionStart, itemCount));
            }

            @Override
            public void onItemRangeMoved(ObservableList<NosyPharmacy> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<NosyPharmacy> sender, int positionStart, int itemCount) {

            }
        });


        viewModel.getCities();
    }


}