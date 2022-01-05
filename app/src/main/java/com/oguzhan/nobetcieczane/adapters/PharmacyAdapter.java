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
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.view.RouteActivity;

import java.util.ArrayList;

import okhttp3.Route;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.ViewHolder>{

    private final Context context;
    private ArrayList<NosyPharmacy> pharmacies;

    public PharmacyAdapter(Context context, ArrayList<NosyPharmacy> pharmacies) {
        this.context = context;
        this.pharmacies = pharmacies;
    }


    @NonNull
    @Override
    public PharmacyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pharmacy_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyAdapter.ViewHolder holder, int position) {
        holder.nameTextview.setText(pharmacies.get(position).getName());
        holder.addressTextView.setText(pharmacies.get(position).getAddress());


        holder.navigationBtn.setText("Go");

        holder.navigationBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, RouteActivity.class);
            context.startActivity(intent);
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
