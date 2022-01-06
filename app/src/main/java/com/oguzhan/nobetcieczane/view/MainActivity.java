package com.oguzhan.nobetcieczane.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.adapters.PharmacyAdapter;
import com.oguzhan.nobetcieczane.components.AreaFilterDropdown;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.utils.Config;
import com.oguzhan.nobetcieczane.viewmodel.MainActivityViewModel;
import com.yandex.mapkit.MapKitFactory;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main activity";
    private static final long LOCATION_REFRESH_TIME = 60;
    private static final float LOCATION_REFRESH_DISTANCE = 1;


    private AreaFilterDropdown areaFilterDropdown;
    private RecyclerView pharmaciesRecyclerView;
    private ProgressBar circularProgressIndicator;
    private TextView noPharmacyTextview;
    private TextView resultInfoTextview;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapKitFactory.setApiKey(Config.yandexMapKitAPIKey);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


        areaFilterDropdown = findViewById(R.id.area_filter);
        circularProgressIndicator = findViewById(R.id.progress_indicator);
        pharmaciesRecyclerView = findViewById(R.id.pharmacies_list);
        noPharmacyTextview = findViewById(R.id.no_pharmacy_text);
        resultInfoTextview = findViewById(R.id.result_type_info_text);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        viewModel.createAppEnterLog();

        pharmaciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(this, viewModel.pharmacies,
                pharmacy -> viewModel.createNavigationLog(pharmacy));
        pharmaciesRecyclerView.setAdapter(pharmacyAdapter);


        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(findViewById(R.id.parent_layout), "Eczaneleri görebilmek için konum izni vermeniz gerekir", Snackbar.LENGTH_LONG).show();
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, location -> {
                    Log.d(TAG, "onCreate: location updated with " + location.getLatitude() + " : " + location.getLongitude());
                    viewModel.updateUserLocation(location.getLatitude(), location.getLongitude());
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

        fab.setOnClickListener(viewModel::onFabClicked);


        viewModel.pharmacies.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<NosyPharmacy>>() {
            @Override
            public void onChanged(ObservableList<NosyPharmacy> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<NosyPharmacy> sender, int positionStart, int itemCount) {
                onPharmaciesListChanged(pharmacyAdapter);
            }

            @Override
            public void onItemRangeInserted(ObservableList<NosyPharmacy> sender, int positionStart, int itemCount) {

                onPharmaciesListChanged(pharmacyAdapter);
            }

            @Override
            public void onItemRangeMoved(ObservableList<NosyPharmacy> sender, int fromPosition, int toPosition, int itemCount) {
                onPharmaciesListChanged(pharmacyAdapter);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<NosyPharmacy> sender, int positionStart, int itemCount) {
                onPharmaciesListChanged(pharmacyAdapter);

            }
        });

        viewModel.isPharmaciesLoading.observe(this, isLoading -> {
            circularProgressIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            pharmaciesRecyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);

        });


        viewModel.getCities();
    }


    void onPharmaciesListChanged(PharmacyAdapter pharmacyAdapter) {


        runOnUiThread(() -> {

            pharmacyAdapter.notifyDataSetChanged();
            if (viewModel.lastPharmaciesFetchWithFab) {
                String text = String.format(Locale.getDefault(), "Mevcut konuma göre eczaneler listeleniyor\n(Enlem:%f, Boylam:%f)", viewModel.getUserLatitude(), viewModel.getUserLongitude());
                resultInfoTextview.setText(text);
            } else {
                String text = String.format(Locale.getDefault(), "%s ili %s ilçesindeki eczaneler listeleniyor", viewModel.selectedCity.getName(), viewModel.selectedCounty.getName());
                resultInfoTextview.setText(text);
            }
            if (pharmacyAdapter.getItemCount() == 0) {

                pharmaciesRecyclerView.setVisibility(View.GONE);
                noPharmacyTextview.setVisibility(View.VISIBLE);

            } else {
                pharmaciesRecyclerView.setVisibility(View.VISIBLE);
                noPharmacyTextview.setVisibility(View.GONE);
            }

        });

    }


}