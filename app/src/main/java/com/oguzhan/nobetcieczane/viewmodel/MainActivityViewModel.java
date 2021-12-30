package com.oguzhan.nobetcieczane.viewmodel;

import android.os.AsyncTask;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oguzhan.nobetcieczane.adapters.PharmacyAdapter;
import com.oguzhan.nobetcieczane.model.City;
import com.oguzhan.nobetcieczane.model.County;
import com.oguzhan.nobetcieczane.model.LocationData;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.model.Pharmacy;
import com.oguzhan.nobetcieczane.repositories.NosyRepository;
import com.oguzhan.nobetcieczane.repositories.Repository;
import com.oguzhan.nobetcieczane.utils.FetchingStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivityViewModel extends ViewModel {
    private final Repository repository = new NosyRepository();


    public MutableLiveData<City[]> cities = new MutableLiveData<>();
    public MutableLiveData<County[]> counties = new MutableLiveData<>();

    public ObservableArrayList<NosyPharmacy> pharmacies = new ObservableArrayList<>();

    public City selectedCity;
    public County selectedCounty;

    public void getCounties(City city) {
        new GetCountiesTask().execute(city);

    }

    public void getPharmacies() {
        new GetPharmaciesTask().execute();
    }

    public void getCities() {
        new GetCitiesTask().execute();
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

    private class GetPharmaciesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Pharmacy[] pharmacies = repository.getPharmacies(selectedCity.getValue(), selectedCounty.getValue());
            MainActivityViewModel.this.pharmacies.clear();

            for (int i = 0; i < pharmacies.length; i++) {
                MainActivityViewModel.this.pharmacies.add((NosyPharmacy) pharmacies[i]);
            }


            return null;
        }
    }

}
