package com.oguzhan.nobetcieczane.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.oguzhan.nobetcieczane.model.City;
import com.oguzhan.nobetcieczane.model.County;
import com.oguzhan.nobetcieczane.model.LocationData;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.model.Pharmacy;
import com.oguzhan.nobetcieczane.repositories.DatabaseRepository;
import com.oguzhan.nobetcieczane.repositories.NosyRepository;
import com.oguzhan.nobetcieczane.repositories.Repository;
import com.oguzhan.nobetcieczane.view.HistoryActivity;

public class MainActivityViewModel extends AndroidViewModel {
    private final Repository nosyRepository = new NosyRepository();
    private final DatabaseRepository databaseRepository = new DatabaseRepository(getApplication().getApplicationContext());


    public MutableLiveData<City[]> cities = new MutableLiveData<>();
    public MutableLiveData<County[]> counties = new MutableLiveData<>();
    public MutableLiveData<Boolean> isPharmaciesLoading = new MutableLiveData<>(true);

    public ObservableArrayList<NosyPharmacy> pharmacies = new ObservableArrayList<>();
    public boolean lastPharmaciesFetchWithFab = false;

    public City selectedCity;
    public County selectedCounty;

    private double userLongitude;
    private double userLatitude;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        createAppEnterLog();
    }


    public void createNavigationLog(NosyPharmacy pharmacy) {
        databaseRepository.insertNavigationLog(pharmacy);
    }

    public void createAppEnterLog() {
        databaseRepository.insertAppEntryLog();
    }

    public void getCounties(City city) {
        new GetCountiesTask().execute(city);
    }

    public void getPharmaciesWithDropdown() {
        new GetPharmaciesTask().execute(false);
    }

    public void getCities() {
        new GetCitiesTask().execute();
    }


    public void updateUserLocation(double latitude, double longitude) {

        userLongitude = longitude;
        userLatitude = latitude;
    }

    public void onFabClicked(View view) {
        new GetPharmaciesTask().execute(true);
    }

    public void onHistoryButtonClicked(View view) {
        Context context = getApplication().getApplicationContext();
        Intent intent = new Intent(context, HistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private class GetCountiesTask extends AsyncTask<City, Void, Void> {

        @Override
        protected Void doInBackground(City... city) {
            County[] counties = nosyRepository.getCounties(city[0]);
            MainActivityViewModel.this.counties.postValue(counties);
            return null;
        }
    }


    private class GetCitiesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            City[] cities = nosyRepository.getCities();
            MainActivityViewModel.this.cities.postValue(cities);
            return null;
        }
    }

    private class GetPharmaciesTask extends AsyncTask<Boolean, Void, Void> {


        @Override
        protected Void doInBackground(Boolean... booleans) {
            boolean fetchWithFab = booleans[0];

            isPharmaciesLoading.postValue(true);
            Pharmacy[] pharmacies;
            if (fetchWithFab) {

                pharmacies = nosyRepository.getPharmaciesByGeoLocation(userLatitude, userLongitude);
                lastPharmaciesFetchWithFab = true;
            } else {
                pharmacies = nosyRepository.getPharmacies(selectedCity.getValue(), selectedCounty.getValue());
                lastPharmaciesFetchWithFab = false;
            }

            MainActivityViewModel.this.pharmacies.clear();

            for (Pharmacy pharmacy : pharmacies) {
                MainActivityViewModel.this.pharmacies.add((NosyPharmacy) pharmacy);
            }

            isPharmaciesLoading.postValue(false);
            return null;
        }
    }


    public void onCountySelected(LocationData locationData) {

        if (locationData != null) {
            selectedCounty = (County) locationData;
            getPharmaciesWithDropdown();
        }
    }

    public void onCitySelected(LocationData locationData) {
        if (locationData != null) {

            selectedCity = (City) locationData;
            getCounties((City) locationData);
        }

    }


    public double getUserLongitude() {
        return userLongitude;
    }

    public double getUserLatitude() {
        return userLatitude;
    }
}
