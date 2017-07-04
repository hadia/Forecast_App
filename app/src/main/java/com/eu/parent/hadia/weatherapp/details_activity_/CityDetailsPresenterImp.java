package com.eu.parent.hadia.weatherapp.details_activity_;

import android.os.Bundle;

import com.eu.parent.hadia.weatherapp.common.Interactor;
import com.eu.parent.hadia.weatherapp.common.InteractorSuccessListener;
import com.eu.parent.hadia.weatherapp.database.GetCitieDataByID;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.eu.parent.hadia.weatherapp.model.FiveDaysModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import nucleus.presenter.Presenter;

/**
 * Created by hadia on 7/4/17.
 */

public class CityDetailsPresenterImp extends Presenter<CityDetailsActivity> implements CityDetailsPresenter {
    @Override
    public void create(Bundle bundle) {
        super.create(bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onTakeView(CityDetailsActivity mainActivity) {
        super.onTakeView(mainActivity);


    }
    @Override
    public void requetWeatherData(int placeID) {
        new GetCitieDataByID(placeID).setSuccessListener(new InteractorSuccessListener<CityModel>() {
            @Override
            public void onSuccess(Interactor interactor, CityModel result) {
                if (result != null) {
                    if (getView() != null) {
                        getView().bindCity(result.getName() + "," + result.getCountry());
                        getView().bindHumidityResult(result.getmWeatherModel().get(0).getHumidity()+"%");
                        getView().bindWindResult( result.getmWeatherModel().get(0).getSpeed()+"km/h");
                        getView().bindWeatherImage("" + result.getmWeatherModel().get(0).getIcon());
                        getView().bindWeatherImage("" + result.getmWeatherModel().get(0).getIcon());
                        getView().bindCityTemp("" + result.getmWeatherModel().get(0).getDay_temp());
                        final List<FiveDaysModel> daysOfTheWeek = new ArrayList<FiveDaysModel>();
                        for (int i = 0; i < 5; i++) {
                            daysOfTheWeek.add(new FiveDaysModel().fill(result.getmWeatherModel().get(i)) );

                        }
                        getView().bindRecycleView(daysOfTheWeek);
                        getView().bindDate(convertTimeToDay());
                    }
                }
            }
        }).execute();
    }
    private String convertTimeToDay() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }
}
