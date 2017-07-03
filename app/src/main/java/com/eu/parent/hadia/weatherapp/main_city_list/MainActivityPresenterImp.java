package com.eu.parent.hadia.weatherapp.main_city_list;

import android.os.Bundle;
import android.widget.Toast;

import com.eu.parent.hadia.weatherapp.database.DeleteCityItem;
import com.eu.parent.hadia.weatherapp.database.GetAllCitiesData;
import com.eu.parent.hadia.weatherapp.database.SaveCityDataInteractor;
import com.eu.parent.hadia.weatherapp.database.SaveWeatherDataInteractor;
import com.eu.parent.hadia.weatherapp.network.get_weather_response.CityResponse;
import com.eu.parent.hadia.weatherapp.main_city_list.add_city.CitySearchAdapter;
import com.eu.parent.hadia.weatherapp.common.Interactor;
import com.eu.parent.hadia.weatherapp.common.InteractorSuccessListener;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.eu.parent.hadia.weatherapp.model.WeatherItemModel;
import com.eu.parent.hadia.weatherapp.network.GetCityWeatherOnlineInteractor;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;

import java.util.ArrayList;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 * Created by Hadia .
 * IBM
 *
 * @author Hadia
 *         on 6/12/17.
 */

public class MainActivityPresenterImp extends Presenter<MainActivity> implements MainActivityPresenter {

    private ArrayList<CityModel> allData;
    WeatherClient weatherclient = null;

    public MainActivityPresenterImp() {
        allData = new ArrayList<>();

    }

    @Override
    public void create(Bundle bundle) {
        super.create(bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onTakeView(MainActivity mainActivity) {
        super.onTakeView(mainActivity);
        //  if(getView()!=null)
        //    getView().bindLocations(cache.getGooglePlaces(),cache.getSquarePlaces());

    }

    public WeatherClient initWeatherClient() {

        WeatherClient.ClientBuilder builder = new WeatherClient.ClientBuilder();
        WeatherConfig config = new WeatherConfig();
        config.unitSystem = WeatherConfig.UNIT_SYSTEM.M;
        config.lang = "en"; // If you want to use english
        config.maxResult = 5; // Max number of cities retrieved
        config.numDays = 6; // Max num of days in the forecast
        config.ApiKey = "b090c7ab5e8d97e8c6bf124f569f6a36";

        try {
            if (getView() != null)
                weatherclient = builder.attach(getView())
                        .provider(new OpenweathermapProviderType())
                        .httpClient(WeatherClientDefault.class)
                        .config(config)
                        .build();

        } catch (Throwable t) {
            // we will handle it later
        }
        return weatherclient;
    }

    @Override
    public void searchCityList(String text) {
        weatherclient.searchCity(text, new WeatherClient.CityEventListener() {
            @Override
            public void onCityListRetrieved(List<City> cities) {
                if (getView() != null) {
                    CitySearchAdapter ca = new CitySearchAdapter(getView(), cities);
                    getView().bindSearchList(ca);
                }

            }

            @Override
            public void onWeatherError(WeatherLibException e) {
                e.printStackTrace();
            }

            @Override
            public void onConnectionError(Throwable throwable) {
            }
        });

    }

    @Override
    public void requetofflineWeatherData() {


        new GetAllCitiesData().setSuccessListener(new InteractorSuccessListener<List<CityModel>>() {
            @Override
            public void onSuccess(Interactor interactor, List<CityModel> result) {
                if (result != null && result.size() > 0) {
                    allData = new ArrayList<CityModel>();
                    allData.addAll(result);
                    if (getView() != null) {
                        getView().bindLocations(allData);
                        // Toast.makeText(getView(), "Nearby Restaurants", Toast.LENGTH_LONG).show();
                    }
                } else {


                }
            }
        }).execute();
    }


    @Override
    public void requetWeatherData(final String placeID, final double latitude, final double longitude) {


        new GetAllCitiesData().setSuccessListener(new InteractorSuccessListener<List<CityModel>>() {
            @Override
            public void onSuccess(Interactor interactor, List<CityModel> result) {
                if (result != null && result.size() > 0) {
                    allData = new ArrayList<CityModel>();
                    for (CityModel cityitem :
                            result) {
                        findCityData("", cityitem.getLat(), cityitem.getLon());
                        Toast.makeText(getView(), "Update Data", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (placeID != null && !placeID.isEmpty()) {
                        findCityData(placeID, 0, 0);

                    } else {
                        findCityData("", latitude, longitude);
                    }

                }
            }
        }).execute();
    }

    @Override
    public int getCityCount() {
        if(allData!=null)
        return allData.size();
        else
            return 0;
    }

    public void getCityData(final int placeID, final String placeName) {
        boolean foundPlace = false;
        for (CityModel tempCity :
                allData) {
            if (tempCity.getId() == placeID) {
                foundPlace = true;
            }
        }
        if (!foundPlace) {
            findCityData(placeName, 0, 0);
        }

    }

    private void findCityData(final String placeID, final double latitude, final double longitude) {
        new GetCityWeatherOnlineInteractor(placeID, latitude, longitude).setSuccessListener(new InteractorSuccessListener<CityResponse>() {
            @Override
            public void onSuccess(Interactor interactor, CityResponse result) {


                CityModel cityData = new CityModel();
                cityData.fill(result);
                new SaveCityDataInteractor(cityData).execute();
                for (int i = 0; i < result.getList().size(); i++) {
                    WeatherItemModel weatherItem = new WeatherItemModel(cityData).fill(result.getList().get(i));
                    weatherItem.setmId(cityData.getId() + "_" + result.getList().get(i).getDt());
                    new SaveWeatherDataInteractor(weatherItem).execute();
                    cityData.getmWeatherModel().add(weatherItem);
                }
//                    cityData.setName(result.get);
//                    getView().bind();
                allData.add(cityData);

                new GetAllCitiesData().execute();
                if (getView() != null) {
                    getView().bindLocations(allData);
                   // Toast.makeText(getView(), "Nearby Restaurants", Toast.LENGTH_LONG).show();
                }


            }
        }).execute();

    }

    @Override
    public void onDeleteitem(final int postion) {
        new DeleteCityItem(allData.get(postion).getId()).setSuccessListener(new InteractorSuccessListener<Integer>() {
            @Override
            public void onSuccess(Interactor interactor, Integer result) {
                if (result==1)
                {
                    allData.remove(postion)  ;
                    if(getView()!=null)
                     getView().bindRemoveLocations( postion, allData.size());
                }
            }
        }).execute();

    }

    @Override
    public void onItemClicked(int postion) {

    }
}
