package com.oguzhan.nobetcieczane.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.adapters.NavigationHistoryAdapter;
import com.oguzhan.nobetcieczane.model.DbNavigationLog;
import com.oguzhan.nobetcieczane.viewmodel.HistoryActivityViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private HistoryActivityViewModel viewModel;

    private TextView visitedCountText;


    private RecyclerView navigationLogsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        viewModel = new ViewModelProvider(this).get(HistoryActivityViewModel.class);

        visitedCountText = findViewById(R.id.visited_count_info_text);

        navigationLogsRecyclerView = findViewById(R.id.navigation_log_list);


        NavigationHistoryAdapter navigationHistoryAdapter = new NavigationHistoryAdapter(this, viewModel.navigationLogs);

        navigationLogsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationLogsRecyclerView.setAdapter(navigationHistoryAdapter);

        findViewById(R.id.back_btn).setOnClickListener(view -> {
            onBackPressed();
        });


        viewModel.visitedCount.observe(this, count -> {
            visitedCountText.setText(String.format(new Locale("tr","TR"), "Uygulamaya daha önce %d kez giriş yaptınız", count));
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

        viewModel.initiateVisitedCount();
        viewModel.getNavigationLogs();
    }
}