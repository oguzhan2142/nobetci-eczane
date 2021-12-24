package com.oguzhan.nobetcieczane.repositories;

import com.oguzhan.nobetcieczane.exceptions.ParseWebSiteException;
import com.oguzhan.nobetcieczane.model.EczanelerGenTrPharmacy;
import com.oguzhan.nobetcieczane.model.Pharmacy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * data from https://www.eczaneler.gen.tr/nobetci-istanbul.
 * we are not using this repository right now
 */
public class EczanelerGenTrRepository extends Repository {


    private static final String TAG = "EczanelerGenTrRepository";
    private String baseUrl = "https://www.eczaneler.gen.tr/";



    @Override
    public Pharmacy[] getPharmacies(String city, String county) {
        try {


            Document document = Jsoup.connect(baseUrl + "/nobetci-istanbul").get();

            Element tBodyElement = document.body().getElementsByTag("tbody").first();


            if (tBodyElement == null) throw new ParseWebSiteException();

            Elements rows = tBodyElement.getElementsByTag("tr");

            Pharmacy[] pharmacies = new Pharmacy[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Element row = rows.get(i);

                Element nameElement = row.getElementsByClass("isim").first();

                if (nameElement == null) throw new ParseWebSiteException();

                String pharmacyName = nameElement.text();
                Element addressElement = row.getElementsByClass("col-lg-6").first();

                if (addressElement == null) throw new ParseWebSiteException();


                Element addressDescElement = addressElement.getElementsByClass("text-secondary font-italic").first();

                String addressDesc;
                if (addressDescElement == null) {
                    addressDesc = null;
                } else {
                    addressDesc = addressDescElement.text();
                }

                String address = addressElement.text();


                Element countyAndNeighborhoodElement = row.getElementsByClass("my-2").first();

                if (countyAndNeighborhoodElement == null) throw new ParseWebSiteException();

                Element countyElement = countyAndNeighborhoodElement.getElementsByClass("px-2 py-1 rounded bg-info text-white font-weight-bold").first();

                if (countyElement == null) throw new ParseWebSiteException();

                String countyStr = countyElement.text();
                Element neighborhoodElement = countyAndNeighborhoodElement.getElementsByClass("ml-2 px-2 py-1 rounded bg-secondary text-light font-weight-bold").first();

                String neighborhood;
                if (neighborhoodElement == null) {
                    neighborhood = null;
                } else {
                    neighborhood = neighborhoodElement.text();
                }

                Element phoneElement = row.getElementsByClass("col-lg-3 py-lg-2").first();

                if (phoneElement == null) throw new ParseWebSiteException();

                String phone = phoneElement.text();

                Pharmacy pharmacy = new EczanelerGenTrPharmacy(pharmacyName, address, addressDesc, phone, neighborhood, countyStr);

                pharmacies[i] = pharmacy;
            }

            return pharmacies;
        } catch (ParseWebSiteException e) {
            //TODO:unimplemented yet
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO:unimplemented yet
        throw new UnsupportedOperationException();
    }
}
