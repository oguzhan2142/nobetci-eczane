package com.oguzhan.nobetcieczane.model;

abstract public class Pharmacy {

    private String name;
    private String address;
    private String addressDescription;
    private String phone;
    private String neighborhood;
    private String county;

    public Pharmacy(String name, String address, String addressDescription, String phone, String neighborhood, String county) {
        this.name = name;
        this.address = address;
        this.addressDescription = addressDescription;
        this.phone = phone;
        this.neighborhood = neighborhood;
        this.county = county;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
