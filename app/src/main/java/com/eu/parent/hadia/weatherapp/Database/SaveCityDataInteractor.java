package com.eu.parent.hadia.weatherapp.Database;

import android.util.Log;

import com.eu.parent.hadia.weatherapp.GetWeatherResponse.CityResponse;
import com.eu.parent.hadia.weatherapp.common.DownloadUrl;
import com.eu.parent.hadia.weatherapp.common.Interactor;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by hadia on 7/2/17.
 */

public class SaveCityDataInteractor   extends Interactor<Integer> {


    CityModel cityModel;
    Dao<CityModel, Integer> cityDao;
    public SaveCityDataInteractor(CityModel cityModel) {

        this.cityModel = cityModel;
        DatabaseHelper     helper  =DatabaseHelper.getInstance();
        try {
            cityDao  = helper.getCityDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected Integer onTaskWork() {
        try {
           int result=cityDao.createOrUpdate(cityModel).getNumLinesChanged();
            cityDao.refresh(cityModel);
            Log.d("WeatherAPP", "Insert Or update "+result);
            return result;
        } catch (Exception e) {
            Log.d("WeatherAPP", e.toString());
        }
        return null;

    }



}
