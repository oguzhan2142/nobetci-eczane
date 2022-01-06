package com.oguzhan.nobetcieczane.viewmodel;

import android.os.AsyncTask;
import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oguzhan.nobetcieczane.model.City;
import com.oguzhan.nobetcieczane.model.County;
import com.oguzhan.nobetcieczane.model.LocationData;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.model.Pharmacy;
import com.oguzhan.nobetcieczane.repositories.NosyRepository;
import com.oguzhan.nobetcieczane.repositories.Repository;

public class MainActivityViewModel extends ViewModel {
    private final Repository repository = new NosyRepository();


    public MutableLiveData<City[]> cities = new MutableLiveData<>();
    public MutableLiveData<County[]> counties = new MutableLiveData<>();
    public MutableLiveData<Boolean> isPharmaciesLoading = new MutableLiveData<Boolean>(true);

    public ObservableArrayList<NosyPharmacy> pharmacies = new ObservableArrayList<>();

    public City selectedCity;
    public County selectedCounty;

    private double userLongitude;
    private double userLatitude;


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

    private class GetCountiesTask extends AsyncTask<City, Void, Void> {

        @Override
        protected Void doInBackground(City... city) {
            County[] counties = repository.getCounties(city[0]);
            MainActivityViewModel.this.counties.postValue(counties);
            return null;
        }
    }


    private class GetCitiesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            City[] cities = repository.getCities();
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

                pharmacies = repository.getPharmaciesByGeoLocation(userLatitude, userLongitude);
            } else {
                pharmacies = repository.getPharmacies(selectedCity.getValue(), selectedCounty.getValue());
            }

            MainActivityViewModel.this.pharmacies.clear();

            for (int i = 0; i < pharmacies.length; i++) {
                MainActivityViewModel.this.pharmacies.add((NosyPharmacy) pharmacies[i]);
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
