package com.oguzhan.nobetcieczane.repositories;


import android.net.ParseException;
import android.util.Log;

import com.oguzhan.nobetcieczane.exceptions.ParseWebSiteException;
import com.oguzhan.nobetcieczane.model.Pharmacy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * the data coming from [https://www.istanbuleczaciodasi.org.tr] website
 * we are not using this repository right now
 */
public class IstanbulEczaneOdasiRepository extends Repository {
    private static final String TAG = "asd";
    private String baseUrl = "https://www.istanbuleczaciodasi.org.tr/";

    private OkHttpClient client = new OkHttpClient();


    public String[] getCounties() throws IOException {
        // jx=1&islem=get_ilce&il=34&h=497a346e46306135355737354e513d3d
        FormBody formBody = new FormBody.Builder()
                .add("jx", "1")
                .add("islem","get_ilce")
                .add("il","34")
                .add("h","497a346e46306135355737354e513d3d")

                .build();

        Headers headers = new Headers.Builder()
                .add("authority","www.istanbuleczaciodasi.org.tr")
                .add("sec-ch-ua","\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"")
                .add("accept","application/json, text/javascript, */*; q=0.01")
                .add("content-type","application/x-www-form-urlencoded; charset=UTF-8")
                .add("x-requested-with","XMLHttpRequest")
                .add("sec-ch-ua-mobile","?0")
                .add("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36")
                .add("sec-ch-ua-platform","\"macOS\"")
                .add("origin","https://www.istanbuleczaciodasi.org.tr")
                .add("sec-fetch-site","same-origin")
                .add("sec-fetch-mode","cors")
                .add("sec-fetch-dest","empty")
                .add("referer","https://www.istanbuleczaciodasi.org.tr/nobetci-eczane/index.php")
                .add("accept-language","tr-TR,tr;q=0.9,en-US;q=0.8,en;q=0.7")
                .add("cookie","COOKIE_DEVICE=desktop; PHPSESSID=f2j3o4koultprk6ofa8o4cvgu8; _ga=GA1.3.100787392.1640201868; _gid=GA1.3.1455518294.1640201868; _gat=1")
    .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/nobetci-eczane/index.php")
                .headers(headers)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();


        String s = response.body().string();


        return new String[0];
    }

    public String[] getCountiesWithParse() throws ParseWebSiteException, IOException {
        String exceptionalCounty = "İlçe Seçiniz";


        Document document = Jsoup.connect(baseUrl + "/nobetci-eczane/").get();

        Element element = document.body();

        Element countySelectTag = element.getElementById("ilce");

        if (countySelectTag == null) throw new ParseWebSiteException();

        Elements options = countySelectTag.getElementsByTag("option");


        String[] results = new String[options.size()];
        for (int i = 0; i < options.size() - 1; i++) {
            String parsedCounty = options.get(i).text();

            if (parsedCounty.equals(exceptionalCounty))
                continue;

            results[i] = parsedCounty;
        }

        return results;


    }



    @Override
    public Pharmacy[] getPharmacies(String city, String county) {
        return new Pharmacy[0];
    }
}
