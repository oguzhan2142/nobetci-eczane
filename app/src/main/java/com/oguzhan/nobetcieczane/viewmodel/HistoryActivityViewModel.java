package com.oguzhan.nobetcieczane.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.oguzhan.nobetcieczane.model.DbAppEntryLog;
import com.oguzhan.nobetcieczane.model.DbNavigationLog;
import com.oguzhan.nobetcieczane.repositories.DatabaseRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HistoryActivityViewModel extends AndroidViewModel {

    private final DatabaseRepository databaseRepository = new DatabaseRepository(getApplication().getApplicationContext());


    public MutableLiveData<Integer> visitedCount = new MutableLiveData<>();


    public ObservableArrayList<DbNavigationLog> navigationLogs = new ObservableArrayList<>();

    public HistoryActivityViewModel(@NonNull Application application) {
        super(application);
    }


    public void getNavigationLogs() {
        DbNavigationLog[] logs = databaseRepository.getNavigationLogs();
        navigationLogs.clear();
        navigationLogs.addAll(Arrays.asList(logs));
    }

    public void initiateVisitedCount() {
        DbAppEntryLog[] logs = databaseRepository.getEntryLogs();
        visitedCount.setValue(logs.length);
    }
}
