package com.oguzhan.nobetcieczane.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oguzhan.nobetcieczane.R;
import com.oguzhan.nobetcieczane.interfaces.NavigationButtonTapListener;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.utils.GoogleMapsIntentGenerator;

import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.ViewHolder> {

    private static final String TAG = "PharmacyAdapter";
    private final Context context;
    private NavigationButtonTapListener navigationButtonTapListener;
    private ArrayList<NosyPharmacy> pharmacies;


    public PharmacyAdapter(Context context, ArrayList<NosyPharmacy> pharmacies, NavigationButtonTapListener navigationButtonTapListener) {
        this.context = context;
        this.pharmacies = pharmacies;
        this.navigationButtonTapListener = navigationButtonTapListener;


    }


    @NonNull
    @Override
    public PharmacyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pharmacy_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyAdapter.ViewHolder holder, int position) {
        holder.nameTextview.setText(pharmacies.get(position).getName());
        holder.addressTextView.setText(pharmacies.get(position).getAddress());


        holder.navigationBtn.setText(R.string.go);
        holder.navigationBtn.setOnClickListener(view -> {
            NosyPharmacy pharmacy = pharmacies.get(position);

            navigationButtonTapListener.onNavigationTapped(pharmacy);
            double latitude = pharmacy.getLatitude();
            double longitude = pharmacy.getLongitude();
            Intent mapIntent = GoogleMapsIntentGenerator.create(latitude, longitude);

            context.startActivity(mapIntent);


        });
    }

    @Override
    public int getItemCount() {
        return pharmacies.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nameTextview;
        protected TextView addressTextView;
        protected Button navigationBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextview = itemView.findViewById(R.id.name);
            addressTextView = itemView.findViewById(R.id.address);
            navigationBtn = itemView.findViewById(R.id.navigation_btn);

        }


    }
}
