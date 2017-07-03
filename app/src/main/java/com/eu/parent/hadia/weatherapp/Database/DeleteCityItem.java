package com.eu.parent.hadia.weatherapp.Database;

import android.util.Log;

import com.eu.parent.hadia.weatherapp.common.Interactor;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by hadia on 7/3/17.
 */

public class DeleteCityItem extends Interactor<Integer> {


    int cityID;
    Dao<CityModel, Integer> cityDao;
    public DeleteCityItem(int cityID) {

        this. cityID = cityID;
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
            int result=cityDao.deleteById(cityID);

            Log.d("WeatherAPP", "delete "+result);
            return result;
        } catch (Exception e) {
            Log.d("WeatherAPP", e.toString());
        }
        return null;

    }



}
