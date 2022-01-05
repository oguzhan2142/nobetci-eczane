package com.oguzhan.nobetcieczane.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class NosyPharmacy extends Pharmacy {


    private String city;
    private String phone2;
    private double latitude;
    private double longitude;


    public NosyPharmacy(String name, String address, String addressDescription, String phone, String neighborhood, String county, String city, double latitude, double longitude, String phone2) {
        super(name, address, addressDescription, phone, neighborhood, county);
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone2 = phone2;


    }



    public static NosyPharmacy fromJson(JsonElement jsonElement) {

        JsonObject jsonObject = jsonElement.getAsJsonObject();


        String name = jsonObject.get("EczaneAdi").getAsString();
        String address = jsonObject.get("Adresi").getAsString();
        String neighborhood = jsonObject.get("Semt").getAsString();
        String desc = jsonObject.get("YolTarifi").getAsString();
        String phone = jsonObject.get("Telefon").getAsString();
        String phone2 = jsonObject.get("Telefon2").getAsString();
        String city = jsonObject.get("Sehir").getAsString();
        String county = jsonObject.get("ilce").getAsString();
        double latitude = jsonObject.get("latitude").getAsDouble();
        double longitude = jsonObject.get("longitude").getAsDouble();

        return new NosyPharmacy(name, address, desc, phone, neighborhood, county, city, latitude, longitude, phone2);

    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
