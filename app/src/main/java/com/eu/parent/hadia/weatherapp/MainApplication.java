
package com.eu.parent.hadia.weatherapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.eu.parent.hadia.weatherapp.Database.DatabaseHelper;


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
