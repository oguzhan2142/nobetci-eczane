package com.oguzhan.nobetcieczane.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.oguzhan.nobetcieczane.model.DbAppEntryLog;
import com.oguzhan.nobetcieczane.repositories.DatabaseRepository;


public class HistoryActivityViewModel extends AndroidViewModel {

    private final DatabaseRepository databaseRepository = new DatabaseRepository(getApplication().getApplicationContext());

    public MutableLiveData<String> lastVisitedDate = new MutableLiveData<>();
    public MutableLiveData<Integer> visitedCount = new MutableLiveData<>();


    public HistoryActivityViewModel(@NonNull Application application) {
        super(application);
    }


    public void getEnterLogs() {
        DbAppEntryLog[] logs = databaseRepository.getEntryLogs();

        DbAppEntryLog lastLog = logs[logs.length - 1];
        lastVisitedDate.setValue(lastLog.getDate());
        visitedCount.setValue(logs.length);
    }
}
