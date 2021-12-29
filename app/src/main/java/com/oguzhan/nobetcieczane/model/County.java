package com.oguzhan.nobetcieczane.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class County extends LocationData{
    public County(String name, String value) {
        super(name, value);
    }

    static public County fromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String name = jsonObject.get("ilceAd").getAsString();

        String value = jsonObject.get("ilceSlug").getAsString();

        return new County(name, value);
    }
}
