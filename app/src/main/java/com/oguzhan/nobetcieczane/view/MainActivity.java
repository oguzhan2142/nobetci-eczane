package com.oguzhan.nobetcieczane.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main activity";


    private ImageButton searchBtn, filterBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = findViewById(R.id.searchBtn);
        filterBtn = findViewById(R.id.filterButton);
        Spinner countySpinner = findViewById(R.id.county_spinner);
        String[] items = new String[0];
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        countySpinner.setAdapter(spinnerAdapter);

        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);



        viewModel.pharmacies.observe(this, pharmacies -> {

            for (int i = 0; i < pharmacies.length; i++) {
                Log.d(TAG, "onCreate:  " + pharmacies[i].getName());
            }

        });

        viewModel.countriesStatus.observe(this, fetchingStatus -> {



        });


        viewModel.getCounties();
    }
}