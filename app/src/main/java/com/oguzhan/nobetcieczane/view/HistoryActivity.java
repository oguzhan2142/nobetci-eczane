package com.oguzhan.nobetcieczane.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.adapters.NavigationHistoryAdapter;
import com.oguzhan.nobetcieczane.model.DbNavigationLog;
import com.oguzhan.nobetcieczane.viewmodel.HistoryActivityViewModel;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private HistoryActivityViewModel viewModel;

    private TextView visitedCountText;
    private TextView lastVisitedInfoText;

    private RecyclerView navigationLogsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        viewModel = new ViewModelProvider(this).get(HistoryActivityViewModel.class);

        visitedCountText = findViewById(R.id.visited_count_info_text);
        lastVisitedInfoText = findViewById(R.id.last_visited_date);
        navigationLogsRecyclerView = findViewById(R.id.navigation_log_list);


        NavigationHistoryAdapter navigationHistoryAdapter = new NavigationHistoryAdapter(this, viewModel.navigationLogs);

        navigationLogsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationLogsRecyclerView.setAdapter(navigationHistoryAdapter);

        findViewById(R.id.back_btn).setOnClickListener(view -> {
            onBackPressed();
        });


        viewModel.visitedCount.observe(this, count -> {
            visitedCountText.setText(String.format(Locale.getDefault(), "Uygulamaya daha önce %d kez giriş yaptınız", count));
        });

        viewModel.lastVisitedDate.observe(this, date -> {

            SimpleDateFormat fromDb = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy hh:mm");
            String formattedDate = null;
            try {
                formattedDate = dateFormat.format(fromDb.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            lastVisitedInfoText.setText(String.format(Locale.getDefault(), "Son giriş tarihi: %s", formattedDate));
        });


        viewModel.navigationLogs.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<DbNavigationLog>>() {
            @Override
            public void onChanged(ObservableList<DbNavigationLog> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<DbNavigationLog> sender, int positionStart, int itemCount) {
                navigationHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeInserted(ObservableList<DbNavigationLog> sender, int positionStart, int itemCount) {
                navigationHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeMoved(ObservableList<DbNavigationLog> sender, int fromPosition, int toPosition, int itemCount) {
                navigationHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<DbNavigationLog> sender, int positionStart, int itemCount) {
                navigationHistoryAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getEnterLogs();
        viewModel.getHistory();
    }
}