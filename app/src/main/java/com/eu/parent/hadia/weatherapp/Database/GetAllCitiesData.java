package com.eu.parent.hadia.weatherapp.Database;

import android.util.Log;

import com.eu.parent.hadia.weatherapp.GetWeatherResponse.WeatherItem;
import com.eu.parent.hadia.weatherapp.common.Interactor;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.eu.parent.hadia.weatherapp.model.WeatherItemModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadia on 7/2/17.
 */

public class GetAllCitiesData extends Interactor<List<CityModel>> {



    Dao<CityModel, Integer> cityDao;
    public GetAllCitiesData() {

        DatabaseHelper     helper  =DatabaseHelper.getInstance();
        try {
            cityDao  = helper.getCityDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected List<CityModel> onTaskWork() {
        try {
            //int result=cityDao.createOrUpdate(cityModel).getNumLinesChanged();
            final List<CityModel> citiesList = cityDao.queryForAll();
            for (CityModel cityModel : citiesList) {
                System.out.println("city = " + cityModel.getName());
                cityModel.setmWeatherModel( new ArrayList<WeatherItemModel>());
                final ForeignCollection<WeatherItemModel> weatherList = cityModel.getmWeather();
                for (WeatherItemModel weatherItemModel : weatherList) {
                    System.out.println("weather = " + weatherItemModel.getmId());
                    cityModel.getmWeatherModel().add(weatherItemModel);
                }
            }
            Log.d("WeatherAPP", "Get City Data ");
            return citiesList;
        } catch (Exception e) {
            Log.d("WeatherAPP", e.toString());
        }
        return null;

    }



}
