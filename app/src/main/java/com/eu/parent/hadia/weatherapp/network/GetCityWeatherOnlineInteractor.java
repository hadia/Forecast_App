package com.eu.parent.hadia.weatherapp.network;

import android.util.Log;

import com.eu.parent.hadia.weatherapp.GetWeatherResponse.CityResponse;
import com.eu.parent.hadia.weatherapp.common.DownloadUrl;
import com.eu.parent.hadia.weatherapp.common.Interactor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;



public class GetCityWeatherOnlineInteractor extends Interactor<CityResponse> {


    String cityDataResponse;

    String url;

    String placeID;
    double lat;
    double lng;


    public GetCityWeatherOnlineInteractor(String placeID, double lat, double lng) {

        this.placeID = placeID;
        this.lat=lat;
        this.lng=lng;
    }

    @Override
    protected CityResponse onTaskWork() {
        try {

            //mMap = (GoogleMap) params[0];
            url = getUrl(placeID);
            DownloadUrl downloadUrl = new DownloadUrl();
            cityDataResponse = downloadUrl.readUrl(url);
            Log.d("WeatherAPP", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("WeatherAPP", e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CityResponse response = null;
        try {
            response = objectMapper.readValue(cityDataResponse, CityResponse.class);
            if (response != null) {
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("JSon error", "onPostExecute Exit");
        }

        return null;

    }


    private String getUrl(String placeID) {
        StringBuilder sb = new StringBuilder("http://api.openweathermap.org/data/2.5/forecast/daily?");

        if (placeID != null && !placeID.isEmpty()) {
            sb.append("q=" + placeID);
        } else {
            sb.append("lat=" + lat);
            sb.append("&lon=" + lng);
        }
        sb.append("&units=metric&APPID=b090c7ab5e8d97e8c6bf124f569f6a36");
System.out.println(sb.toString());
        return (sb.toString());
    }

}
