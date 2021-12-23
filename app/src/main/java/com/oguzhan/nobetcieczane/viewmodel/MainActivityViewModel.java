package com.oguzhan.nobetcieczane.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oguzhan.nobetcieczane.exceptions.ParseWebSiteException;
import com.oguzhan.nobetcieczane.repositories.IstanbulEczaneOdasiRepository;
import com.oguzhan.nobetcieczane.utils.FetchingStatus;

import java.io.IOException;

public class MainActivityViewModel extends ViewModel {
    private IstanbulEczaneOdasiRepository repository = new IstanbulEczaneOdasiRepository();

    public MutableLiveData<String[]> counties = new MutableLiveData<String[]>();


    public MutableLiveData<FetchingStatus> countriesStatus = new MutableLiveData<>(FetchingStatus.idleStatus());

    public void getCounties() {
        new GetCountiesTask().execute();
    }


    private class GetCountiesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String[] fetchedCounties = new String[0];
            try {
                countriesStatus.postValue(FetchingStatus.loadingStatus());
                fetchedCounties = repository.getCounties();
            }  catch (IOException e) {
                countriesStatus.postValue(FetchingStatus.errorStatus("Beklenmeyen bir hata meydana geldi"));
                e.printStackTrace();
            }
            counties.postValue(fetchedCounties);
            countriesStatus.postValue(FetchingStatus.successStatus(null));
            return null;
        }
    }

}
