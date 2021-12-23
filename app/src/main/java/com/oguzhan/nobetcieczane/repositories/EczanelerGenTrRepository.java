package com.oguzhan.nobetcieczane.repositories;

import com.oguzhan.nobetcieczane.model.Pharmacy;

import okhttp3.OkHttpClient;

public class EczanelerGenTrRepository {


    private String baseUrl = "https://www.eczaneler.gen.tr/";

    private OkHttpClient client = new OkHttpClient();


    public Pharmacy[] getPharmacies(){





        return new Pharmacy[0];
    }



}
