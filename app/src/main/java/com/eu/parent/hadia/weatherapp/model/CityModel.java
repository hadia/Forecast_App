package com.eu.parent.hadia.weatherapp.model;

import com.eu.parent.hadia.weatherapp.Database.CustomDao;
import com.eu.parent.hadia.weatherapp.GetWeatherResponse.CityResponse;
import com.eu.parent.hadia.weatherapp.GetWeatherResponse.ListItem;
import com.eu.parent.hadia.weatherapp.GetWeatherResponse.WeatherItem;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by hadia on 7/2/17.
 */
@DatabaseTable(tableName = CityModel.TABLE_NAME_CITYS)
public class CityModel {
    public static final String TABLE_NAME_CITYS = "citys";

    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_NAME   = "name";
    public static final String FIELD_NAME_COUNTRY  = "country";
    public static final String FIELD_NAME_WEATHER = "weather";
    public static final String FIELD_NAME_LON = "lon";
    public static final String FIELD_NAME_LAT = "lat";
    public static final String FIELD_NAME_CODE = "code";
    public static final String FIELD_NAME_CNT = "cnt";

    @DatabaseField(columnName = FIELD_NAME_ID,id = true)
    private int id;
    @DatabaseField(columnName = FIELD_NAME_COUNTRY)
    private String country;
    @DatabaseField(columnName = FIELD_NAME_LON)
    private double lon;
    @DatabaseField(columnName = FIELD_NAME_LAT)
    private double lat;
    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String name;
    @DatabaseField(columnName = FIELD_NAME_CNT)
    private int cnt;
    @DatabaseField(columnName = FIELD_NAME_CODE)
    private String cod;

    // One-to-many
    @ForeignCollectionField(columnName = FIELD_NAME_WEATHER, eager = true)
    private ForeignCollection<WeatherItemModel> mWeather;
    private ArrayList<WeatherItemModel> mWeatherModel;

    public CityModel() {
        mWeatherModel= new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public ForeignCollection<WeatherItemModel> getmWeather() {
        return mWeather;
    }
    public void setmWeather(ForeignCollection<WeatherItemModel> mWeather) {
        this.mWeather = mWeather;
    }

    public ArrayList<WeatherItemModel> getmWeatherModel() {
        return mWeatherModel;
    }

    public void setmWeatherModel(ArrayList<WeatherItemModel> mWeatherModel) {
        this.mWeatherModel = mWeatherModel;
    }

    public CityModel fill(CityResponse result)
    {
        this.setId(result.getCity().getId());
        this.setName(result.getCity().getName());
        this.setCountry(result.getCity().getCountry());
        this.setLat(result.getCity().getCoord().getLat());
        this.setLon(result.getCity().getCoord().getLon());
        this.setCod(result.getCod());
        this.setCnt(result.getCnt());


        return this;
    }

}
