package com.oguzhan.nobetcieczane.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oguzhan.nobetcieczane.model.City;
import com.oguzhan.nobetcieczane.model.County;
import com.oguzhan.nobetcieczane.model.LocationData;
import com.oguzhan.nobetcieczane.model.Pharmacy;
import com.oguzhan.nobetcieczane.repositories.NosyRepository;
import com.oguzhan.nobetcieczane.repositories.Repository;
import com.oguzhan.nobetcieczane.utils.FetchingStatus;

public class MainActivityViewModel extends ViewModel {
    private final Repository repository = new NosyRepository();

    public MutableLiveData<Pharmacy[]> pharmacies = new MutableLiveData<>();
    public MutableLiveData<City[]> cities = new MutableLiveData<>();
    public MutableLiveData<County[]> counties = new MutableLiveData<>();


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
            Pharmacy[] pharmacies = repository.getPharmacies("istanbul", "avcilar");
            MainActivityViewModel.this.pharmacies.postValue(pharmacies);
            return null;
        }
    }

}
