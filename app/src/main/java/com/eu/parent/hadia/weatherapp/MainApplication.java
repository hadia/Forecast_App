
package com.eu.parent.hadia.weatherapp;

import android.app.Application;

import com.eu.parent.hadia.weatherapp.database.DatabaseHelper;


public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper helper  =DatabaseHelper.getInstance(getApplicationContext());

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        DatabaseHelper.getInstance().close();

    }
}
