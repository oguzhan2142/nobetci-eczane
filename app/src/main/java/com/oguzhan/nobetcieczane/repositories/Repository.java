package com.oguzhan.nobetcieczane.repositories;

import com.oguzhan.nobetcieczane.model.City;
import com.oguzhan.nobetcieczane.model.County;
import com.oguzhan.nobetcieczane.model.LocationData;
import com.oguzhan.nobetcieczane.model.Pharmacy;


/**
 * Active repository is <b>NosyRepository<b/>
 */
abstract public class Repository {


    public abstract Pharmacy[] getPharmacies(String city, String county);

    public abstract City[] getCities();

    public abstract County[] getCounties(City city);
}
