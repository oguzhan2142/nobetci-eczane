package com.oguzhan.nobetcieczane.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.oguzhan.nobetcieczane.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        viewModel = new ViewModelProvider(this).get(HistoryActivityViewModel.class);

        visitedCountText = findViewById(R.id.visited_count_info_text);
        lastVisitedInfoText = findViewById(R.id.last_visited_date);

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


        viewModel.getEnterLogs();
    }
}