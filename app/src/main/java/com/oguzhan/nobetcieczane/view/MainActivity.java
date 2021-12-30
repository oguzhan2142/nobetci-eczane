package com.oguzhan.nobetcieczane.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.adapters.PharmacyAdapter;
import com.oguzhan.nobetcieczane.components.AreaFilterDropdown;
import com.oguzhan.nobetcieczane.components.SearchEdittext;

import com.oguzhan.nobetcieczane.interfaces.LocationDataSelectedListener;
import com.oguzhan.nobetcieczane.model.City;
import com.oguzhan.nobetcieczane.model.County;
import com.oguzhan.nobetcieczane.model.LocationData;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main activity";


    private AreaFilterDropdown areaFilterDropdown;
    private RecyclerView pharmaciesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


        areaFilterDropdown = findViewById(R.id.area_filter);
        pharmaciesRecyclerView = findViewById(R.id.pharmacies_list);
        pharmaciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(this, viewModel.pharmacies);
        pharmaciesRecyclerView.setAdapter(pharmacyAdapter);


        viewModel.cities.observe(this, cities -> {
            areaFilterDropdown.setCities(cities);

        });

        viewModel.counties.observe(this, counties -> {
            areaFilterDropdown.setCounties(counties);
        });

        areaFilterDropdown.setOnCitySelectedListener(locationData -> {
            if (locationData != null) {

                viewModel.selectedCity = (City) locationData;
                viewModel.getCounties((City) locationData);
            }

        });

        areaFilterDropdown.setOnCountySelectedListener(locationData -> {
            if (locationData != null) {
                viewModel.selectedCounty = (County) locationData;

                viewModel.getPharmacies();

                Log.d(TAG, "onCreate: secildi " + locationData.getName());

            }
        });
        viewModel.pharmacies.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<NosyPharmacy>>() {
            @Override
            public void onChanged(ObservableList<NosyPharmacy> sender) {
                Log.d(TAG, "onChanged: itemler degisti");
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



//        viewModel.getCounties();
        viewModel.getCities();
    }


}