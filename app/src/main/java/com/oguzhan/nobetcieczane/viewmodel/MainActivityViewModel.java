package com.oguzhan.nobetcieczane.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oguzhan.nobetcieczane.exceptions.ParseWebSiteException;
import com.oguzhan.nobetcieczane.model.Pharmacy;
import com.oguzhan.nobetcieczane.repositories.EczanelerGenTrRepository;
import com.oguzhan.nobetcieczane.repositories.NosyRepository;
import com.oguzhan.nobetcieczane.repositories.Repository;
import com.oguzhan.nobetcieczane.utils.FetchingStatus;

import java.io.IOException;

public class MainActivityViewModel extends ViewModel {
    private Repository repository = new NosyRepository();

    public MutableLiveData<Pharmacy[]> pharmacies = new MutableLiveData<Pharmacy[]>();


    public MutableLiveData<FetchingStatus> countriesStatus = new MutableLiveData<>(FetchingStatus.idleStatus());

    public void getCounties() {
        new GetCountiesTask().execute();
    }


    private class GetCountiesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Pharmacy[] pharmacies = new Pharmacy[0];

            countriesStatus.postValue(FetchingStatus.loadingStatus());
            pharmacies = repository.getPharmacies("istanbul", "avcilar");

            MainActivityViewModel.this.pharmacies.postValue(pharmacies);
            countriesStatus.postValue(FetchingStatus.successStatus(null));
            return null;
        }
    }

}
