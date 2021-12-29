package com.oguzhan.nobetcieczane.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.components.AreaFilterDropdown;
import com.oguzhan.nobetcieczane.components.SearchEdittext;

import com.oguzhan.nobetcieczane.interfaces.LocationDataSelectedListener;
import com.oguzhan.nobetcieczane.model.City;
import com.oguzhan.nobetcieczane.model.LocationData;
import com.oguzhan.nobetcieczane.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main activity";




    private AreaFilterDropdown areaFilterDropdown;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);



        areaFilterDropdown = findViewById(R.id.area_filter);





        viewModel.cities.observe(this, cities -> {
            areaFilterDropdown.setCities(cities);

        });

        viewModel.counties.observe(this, counties -> {
            areaFilterDropdown.setCounties(counties);
        });

        areaFilterDropdown.setOnCitySelectedListener(city->{
            if (city != null){
                viewModel.getCounties((City)city);
            }

        });

        areaFilterDropdown.setOnCountySelectedListener(county -> {
            if (county != null) {
                Log.d(TAG, "onCreate: secildi" + county.getName());

            }
        });

        viewModel.pharmacies.observe(this, pharmacies -> {

            for (int i = 0; i < pharmacies.length; i++) {
                Log.d(TAG, "onCreate:  " + pharmacies[i].getName());
            }

        });


//        viewModel.getCounties();
        viewModel.getCities();
    }


}