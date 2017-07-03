package com.eu.parent.hadia.weatherapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.eu.parent.hadia.weatherapp.model.WeatherItemModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 16;

    private Dao<CityModel, Integer> mCityDao = null;
    ;
    private Dao<WeatherItemModel, Integer> mWeatherlDao = null;

    private RuntimeExceptionDao<CityModel, ?> m;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CityModel.class);
            TableUtils.createTable(connectionSource, WeatherItemModel.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CityModel.class, true);
            TableUtils.dropTable(connectionSource, WeatherItemModel.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* CityModel */

    public Dao<CityModel, Integer> getCityDao() throws SQLException {
        if (mCityDao == null) {
            mCityDao = getDao(CityModel.class);
        }
        m = getRuntimeExceptionDao(CityModel.class);
        return mCityDao;
    }

   
    /* WeatherItemModel */

    public Dao<WeatherItemModel, Integer> getWeatherlDao() throws SQLException {
        if (mWeatherlDao == null) {
            mWeatherlDao = getDao(WeatherItemModel.class);
        }

        return mWeatherlDao;
    }


    @Override
    public void close() {
        mCityDao = null;
        mWeatherlDao = null;
        super.close();
    }

    private static DatabaseHelper sDatabaseHelper;

    public static DatabaseHelper getInstance(Context context) {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(context.getApplicationContext());
        }

        return sDatabaseHelper;
    }

    public static DatabaseHelper getInstance() {
        return sDatabaseHelper;
    }
}