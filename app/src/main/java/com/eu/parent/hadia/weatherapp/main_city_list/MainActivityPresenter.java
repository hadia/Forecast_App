package com.eu.parent.hadia.weatherapp.main_city_list;

import com.survivingwithandroid.weather.lib.WeatherClient;

/**
 * Created by hadia on 7/2/17.
 */

public interface MainActivityPresenter {
    public WeatherClient initWeatherClient();
    public void searchCityList(String query);
    public void requetWeatherData(final String placeID, final double latitude, final double longitude);
    public void getCityData(  int placeID, String placeName);
    public int getCityCount(  );
    public void requetofflineWeatherData() ;
    public void onDeleteitem(int postion);
    public void onItemClicked(int postion);

}
