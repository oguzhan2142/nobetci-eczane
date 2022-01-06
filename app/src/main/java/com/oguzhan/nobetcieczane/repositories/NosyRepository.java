package com.oguzhan.nobetcieczane.repositories;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oguzhan.nobetcieczane.exceptions.ApiCommunicationException;
import com.oguzhan.nobetcieczane.exceptions.ParseWebSiteException;
import com.oguzhan.nobetcieczane.model.City;
import com.oguzhan.nobetcieczane.model.County;
import com.oguzhan.nobetcieczane.model.LocationData;
import com.oguzhan.nobetcieczane.model.NosyPharmacy;
import com.oguzhan.nobetcieczane.model.Pharmacy;
import com.oguzhan.nobetcieczane.utils.Config;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Headers;
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

    private Headers headers = new Headers.Builder()
            .add("Content-Type", "application/json")
            .add("Authorization", "Bearer " + Config.noisyAPIKey)
            .build();


    private Pharmacy[] getPharmaciesWithUrl(String url) {


        Pharmacy[] pharmacies = null;
        try {

            Request request = new Request.Builder()
                    .url(url)
                    .headers(headers)
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

    @Override
    public Pharmacy[] getPharmacies(String city, String county) {

        String url = baseUrl + String.format("?city=%s&county=%s", city, county);

        return getPharmaciesWithUrl(url);
    }

    @Override
    public Pharmacy[] getPharmaciesByGeoLocation(double latitude, double longitude) {
        String url = baseUrl + String.format(Locale.ENGLISH, "/distance?latitude=%f&longitude=%f", latitude, longitude);

        return getPharmaciesWithUrl(url);
    }

    @Override
    public City[] getCities() {
        try {
            JsonArray dataArray = getDataArray("/city");
            if (dataArray == null) {
                throw new ParseWebSiteException();

            }

            City[] cities = new City[dataArray.size()];
            for (int i = 0; i < dataArray.size(); i++) {
                try {
                    City locationData = City.fromJson(dataArray.get(i));
                    cities[i] = locationData;

                } catch (NullPointerException e) {
                    // TODO handle exception
                    e.printStackTrace();
                }
            }

            return cities;


        } catch (ParseWebSiteException e) {
            // TODO handle exception
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public County[] getCounties(City city) {


        try {
            JsonArray dataArray = getDataArray(String.format("/city?city=%s", city.getValue()));
            if (dataArray == null) {
                throw new ParseWebSiteException();

            }

            County[] counties = new County[dataArray.size()];
            for (int i = 0; i < dataArray.size(); i++) {
                try {
                    County locationData = County.fromJson(dataArray.get(i));
                    counties[i] = locationData;

                } catch (NullPointerException e) {
                    // TODO handle exception
                    e.printStackTrace();
                }
            }

            return counties;


        } catch (ParseWebSiteException e) {
            // TODO handle exception
            e.printStackTrace();
        }
        return null;

    }

    private JsonArray getDataArray(String additionAfterBaseUrl) {
        Request request = new Request.Builder()
                .url(baseUrl + additionAfterBaseUrl)
                .headers(headers)
                .build();
        try {

            Response response = client.newCall(request).execute();

            ResponseBody responseBody = response.body();

            if (responseBody == null) throw new ParseWebSiteException();

            String json = responseBody.string();

            JsonElement jsonElement = JsonParser.parseString(json);

            return jsonElement.getAsJsonObject().getAsJsonArray("data");


        } catch (IOException | ParseWebSiteException e) {
            e.printStackTrace();
            // TODO handle exception
        }

        return null;

    }
}
