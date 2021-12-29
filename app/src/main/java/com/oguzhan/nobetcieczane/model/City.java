package com.oguzhan.nobetcieczane.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class City extends LocationData{

    public City(String name, String value) {
        super(name, value);
    }

    static public City fromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String name = jsonObject.get("SehirAd").getAsString();

        String value = jsonObject.get("SehirSlug").getAsString();

        return new City(name, value);
    }
}
