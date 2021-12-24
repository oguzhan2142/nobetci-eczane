package com.oguzhan.nobetcieczane.repositories;

import com.oguzhan.nobetcieczane.model.Pharmacy;


/**
 * Active repository is <b>NosyRepository<b/>
 */
abstract public class Repository {


    public abstract Pharmacy[] getPharmacies(String city, String county);
}
