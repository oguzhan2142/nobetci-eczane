package com.oguzhan.nobetcieczane.repositories;

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


    @Override
    public Pharmacy[] getPharmacies(String city, String county) {
        Pharmacy[] pharmacies = null;
        try {
            Request request = new Request.Builder()
                    .url(baseUrl + String.format("?city=%s&county=%s", city, county))
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
    public City[] getCities() {
        try {
            JsonArray dataArray = getDataArray("");
            if (dataArray == null) {
                throw new ParseWebSiteException();

            }

            City[] cities = new City[dataArray.size()];
            for (int i = 0; i < dataArray.size(); i++) {
                try {
                    City locationData = City.fromJson(dataArray.get(i));
                    cities[i] = locationData;

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            return cities;


        } catch (ParseWebSiteException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public County[] getCounties(City city) {


        try {
            JsonArray dataArray = getDataArray(String.format("?city=%s", city.getValue()));
            if (dataArray == null) {
                throw new ParseWebSiteException();

            }

            County[] counties = new County[dataArray.size()];
            for (int i = 0; i < dataArray.size(); i++) {
                try {
                    County locationData = County.fromJson(dataArray.get(i));
                    counties[i] = locationData;

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            return counties;


        } catch (ParseWebSiteException e) {
            e.printStackTrace();
        }
        return null;

    }

    private JsonArray getDataArray(String cityQueryParameter) {
        Request request = new Request.Builder()
                .url(baseUrl + "/city" + cityQueryParameter)
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
        }

        return null;

    }
}
