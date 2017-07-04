package com.eu.parent.hadia.weatherapp.database;

import android.util.Log;

import com.eu.parent.hadia.weatherapp.common.Interactor;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.eu.parent.hadia.weatherapp.model.WeatherItemModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hadia on 7/4/17.
 */

public class GetCitieDataByID extends Interactor<CityModel> {

    int placeID;

    Dao<CityModel, Integer> cityDao;

    public GetCitieDataByID(int placeID) {

        DatabaseHelper helper = DatabaseHelper.getInstance();
        try {
            cityDao = helper.getCityDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.placeID = placeID;

    }

    @Override
    protected CityModel onTaskWork() {
        try {

            final CityModel cityData = cityDao.queryForId(placeID);

            System.out.println("city = " + cityData.getName());
            cityData.setmWeatherModel(new ArrayList<WeatherItemModel>());
            final ForeignCollection<WeatherItemModel> weatherList = cityData.getmWeather();
            for (WeatherItemModel weatherItemModel : weatherList) {
                System.out.println("weather = " + weatherItemModel.getmId());
                cityData.getmWeatherModel().add(weatherItemModel);
            }

            Log.d("WeatherAPP", "Get City Data ");
            return cityData;
        } catch (Exception e) {
            Log.d("WeatherAPP", e.toString());
        }
        return null;

    }


}
