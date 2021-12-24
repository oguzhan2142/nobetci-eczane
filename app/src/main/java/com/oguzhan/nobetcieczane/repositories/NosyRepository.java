package com.oguzhan.nobetcieczane.repositories;

import android.view.textclassifier.TextLinks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oguzhan.nobetcieczane.exceptions.ApiCommunicationException;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.model.Pharmacy;
import com.oguzhan.nobetcieczane.utils.Config;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * data coming from https://collectapi.com/tr/api/health/nobetci-eczane-api
 * I got permission to use this api. They define me a api key.
 * So this is our main repository when fetching pharmacies data
 */
public class NosyRepository extends Repository {

    private String baseUrl = "https://www.nosyapi.com/apiv2/pharmacy";
    private OkHttpClient client = new OkHttpClient();


    @Override
    public Pharmacy[] getPharmacies(String city, String county) {
        Pharmacy[] pharmacies = null;
        try {
            Request request = new Request.Builder()
                    .url(baseUrl + String.format("?city=%s&county=%s", city, county))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + Config.noisyAPIKey)
                    .build();
            Response response = client.newCall(request).execute();


            ResponseBody body = response.body();
            if (body == null) {
                throw new ApiCommunicationException();
            }
            String json = body.string();
            JsonElement jsonElement = JsonParser.parseString(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            pharmacies = new NosyPharmacy[dataArray.size()];

            for (int i = 0; i < dataArray.size(); i++) {
                JsonElement element = dataArray.get(i);
                Pharmacy pharmacy = NosyPharmacy.fromJson(element);
                pharmacies[i] = pharmacy;
            }




        } catch (IOException | ApiCommunicationException e) {
            // TODO implemented yet

        }


        return pharmacies;
    }
}
