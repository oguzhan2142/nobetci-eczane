package com.oguzhan.nobetcieczane.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LocationData {
    private String name;
    private String value;

     public LocationData(String name, String value) {
         this.name = name;
         this.value = value;
     }






     public String getValue() {
         return value;
     }

     public void setValue(String value) {
         this.value = value;
     }

     public LocationData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
