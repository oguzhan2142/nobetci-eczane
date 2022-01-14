package com.oguzhan.nobetcieczane.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.model.DbNavigationLog;
import com.oguzhan.nobetcieczane.utils.GoogleMapsIntentGenerator;

import java.util.ArrayList;
import java.util.Locale;

public class NavigationHistoryAdapter extends RecyclerView.Adapter<NavigationHistoryAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<DbNavigationLog> navigationLogs;


    public NavigationHistoryAdapter(Context context, ArrayList<DbNavigationLog> navigationLogs) {
        this.context = context;
        this.navigationLogs = navigationLogs;
    }

    @Override
    public NavigationHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_pharmacy_list_item, parent, false);
        return new NavigationHistoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NavigationHistoryAdapter.ViewHolder holder, int position) {
        DbNavigationLog model = navigationLogs.get(position);
        holder.nameText.setText(model.getName());
        holder.dateText.setText(String.format(new Locale("tr","TR"), "Son navigasyon: %s", model.getDate()));
        holder.navigateBtn.setText("Git");
        holder.navigateBtn.setOnClickListener(view -> {
            DbNavigationLog log = navigationLogs.get(position);
            Intent mapIntent = GoogleMapsIntentGenerator.create(log.getLatitude(), log.getLongitude());
            context.startActivity(mapIntent);
        });


    }

    @Override
    public int getItemCount() {
        return navigationLogs.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView dateText;
        public Button navigateBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name);
            dateText = itemView.findViewById(R.id.visit_date);
            navigateBtn = itemView.findViewById(R.id.navigation_btn);
        }
    }
}
