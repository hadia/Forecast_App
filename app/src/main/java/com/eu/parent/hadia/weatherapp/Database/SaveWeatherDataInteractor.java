package com.eu.parent.hadia.weatherapp.Database;

import android.util.Log;

import com.eu.parent.hadia.weatherapp.common.Interactor;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.eu.parent.hadia.weatherapp.model.WeatherItemModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;

/**
 * Created by hadia on 7/2/17.
 */

public class SaveWeatherDataInteractor extends Interactor<Integer> {


    WeatherItemModel weatherModel;
    Dao<WeatherItemModel, Integer> weatherDao;
    public SaveWeatherDataInteractor(WeatherItemModel weatherModel) {

        this.weatherModel = weatherModel;
        DatabaseHelper     helper  =DatabaseHelper.getInstance();
        try {
            weatherDao  = helper.getWeatherlDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected Integer onTaskWork() {
        try {
            int result=0;

            QueryBuilder<WeatherItemModel, Integer> userPostQb = weatherDao.queryBuilder();
            userPostQb.selectColumns(WeatherItemModel.FIELD_NAME_ID);
            userPostQb.where().eq(WeatherItemModel.FIELD_NAME_ID, weatherModel.getmId());
            ;

        if(!weatherDao.query(userPostQb.prepare()).isEmpty())
            {
                result= weatherDao.update(weatherModel);
            }
            else
        {
            result=weatherDao.create(weatherModel);
        }
            weatherDao.refresh(weatherModel);
            Log.d("WeatherAPP", "Insert Or update"+result);
            return result;
        } catch (Exception e) {
            Log.d("WeatherAPP", e.toString());
        }
        return null;

    }



}
