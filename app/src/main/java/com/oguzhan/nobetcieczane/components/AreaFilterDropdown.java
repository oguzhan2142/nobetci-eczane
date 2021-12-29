package com.oguzhan.nobetcieczane.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.interfaces.LocationDataSelectedListener;
import com.oguzhan.nobetcieczane.model.LocationData;

public class AreaFilterDropdown extends LinearLayout {

    private Spinner countySpinner;
    private Spinner citySpinner;

    private LocationData[] cities;
    private LocationData[] counties;


    public AreaFilterDropdown(Context context) {
        super(context);
        init();
    }

    public AreaFilterDropdown(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AreaFilterDropdown(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AreaFilterDropdown(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewGroup.LayoutParams equalsAreaParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);



        LinearLayout citySection = new LinearLayout(getContext());
        citySection.setOrientation(VERTICAL);
        TextView cityLabel = new TextView(getContext());

        cityLabel.setText("Şehir");
        cityLabel.setPadding(20,10,0,2);
        cityLabel.setTextSize(12);




        citySpinner = new Spinner(getContext());



        citySection.addView(cityLabel);
        citySection.addView(citySpinner);

        citySection.setLayoutParams(equalsAreaParams);
        addView(citySection);


        View verticalDivider = new View(getContext());
        verticalDivider.setBackgroundColor(getResources().getColor(R.color.grey));
        addView(verticalDivider, new ViewGroup.LayoutParams(2,ViewGroup.LayoutParams.MATCH_PARENT));


        LinearLayout countySection = new LinearLayout(getContext());
        countySection.setOrientation(VERTICAL);
        TextView countyLabel = new TextView(getContext());

        countyLabel.setText("İlçe");
        countyLabel.setPadding(20,10,0,2);
        countyLabel.setTextSize(12);


        countySection.addView(countyLabel);

        countySpinner = new Spinner(getContext());
        countySection.addView(countySpinner);
        countySection.setLayoutParams(equalsAreaParams);
        addView(countySection);


        Drawable btnShape = ContextCompat.getDrawable(getContext(), R.drawable.button_shape);
        setBackground(btnShape);
    }

    private String[] getNamesAsStringArray(LocationData[] locationData) {

        String[] cityNames = new String[locationData.length];

        for (int i = 0; i < cityNames.length; i++) {
            cityNames[i] = locationData[i].getName();
        }
        return cityNames;
    }


    public void setCities(LocationData[] data) {
        this.cities = data;
        citySpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getNamesAsStringArray(data)));

    }

    public void setCounties(LocationData[] data) {
        this.counties = data;
        countySpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getNamesAsStringArray(data)));
    }


    public void setOnCountySelectedListener(LocationDataSelectedListener locationDataSelectedListener) {
        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locationDataSelectedListener.onLocationDataSelected(counties[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void setOnCitySelectedListener(LocationDataSelectedListener locationDataSelectedListener) {

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locationDataSelectedListener.onLocationDataSelected(cities[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
