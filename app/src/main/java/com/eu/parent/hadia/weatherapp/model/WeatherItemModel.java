package com.eu.parent.hadia.weatherapp.model;

import com.eu.parent.hadia.weatherapp.GetWeatherResponse.ListItem;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hadia on 7/2/17.
 */
@DatabaseTable(tableName = WeatherItemModel.TABLE_NAME_WEATHER)
public class WeatherItemModel {
    public static final String TABLE_NAME_WEATHER = "weather";
    public static final String FIELD_NAME_ID    = "id";

    public static final String FIELD_NAME_CITY  = "city";
    public static final String FIELD_NAME_DATE_TIME  = "dt";
    public static final String FIELD_NAME_DEG  = "deg";
    public static final String FIELD_NAME_HUMIDITY = "humidity";
    public static final String FIELD_NAME_PRESSURE = "pressure";
    public static final String FIELD_NAME_CLOUDS = "clouds";
    public static final String FIELD_NAME_SPEED = "speed";
    public static final String FIELD_NAME_ICON = "icon";
    public static final String FIELD_NAME_DESCRIPTION = "description";
    public static final String FIELD_NAME_STATUS = "status";
    public static final String FIELD_NAME_WEATHER_ID = "weather_id";
    public static final String FIELD_NAME_MIN_TEMP = "min_temp";
    public static final String FIELD_NAME_MAX_TEMP = "max_temp";
    public static final String FIELD_NAME_EVE_TEMP = "eve_temp";
    public static final String FIELD_NAME_NIGHT_TEMP = "night_temp";
    public static final String FIELD_NAME_DAY_TEMP = "day_temp";
    public static final String FIELD_NAME_MORN_TEMP = "morn_temp";

    public WeatherItemModel(final CityModel cityModel) {
        mCity = cityModel;
    }

    @DatabaseField(columnName = FIELD_NAME_ID,id = true)
    private String mId;
    @DatabaseField(columnName = FIELD_NAME_DATE_TIME)
    private long dt;
    @DatabaseField(columnName = FIELD_NAME_DEG)
    private int deg;
    @DatabaseField(columnName = FIELD_NAME_HUMIDITY)
    private int humidity;
    @DatabaseField(columnName = FIELD_NAME_PRESSURE)
    private double pressure;
    @DatabaseField(columnName = FIELD_NAME_CLOUDS)
    private int clouds;
    @DatabaseField(columnName = FIELD_NAME_SPEED)
    private double speed;
    @DatabaseField(columnName = FIELD_NAME_ICON)
    private String icon;
    @DatabaseField(columnName = FIELD_NAME_DESCRIPTION)
    private String description;
    @DatabaseField(columnName = FIELD_NAME_STATUS)
    private String status;
    @DatabaseField(columnName = FIELD_NAME_WEATHER_ID)
    private int weather_id;
    @DatabaseField(columnName = FIELD_NAME_MIN_TEMP)
    private int min_temp;
    @DatabaseField(columnName = FIELD_NAME_MAX_TEMP)
    private int max_temp;
    @DatabaseField(columnName = FIELD_NAME_EVE_TEMP)
    private int eve_temp;
    @DatabaseField(columnName = FIELD_NAME_NIGHT_TEMP)
    private int night_temp;
    @DatabaseField(columnName = FIELD_NAME_DAY_TEMP)
    private int day_temp;
    @DatabaseField(columnName = FIELD_NAME_MORN_TEMP)
    private int morn_temp;
    @DatabaseField(columnName = FIELD_NAME_CITY, foreign = true, foreignAutoRefresh = true)
    private CityModel mCity;

    public WeatherItemModel() {
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(int weather_id) {
        this.weather_id = weather_id;
    }

    public int getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(int min_temp) {
        this.min_temp = min_temp;
    }

    public int getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(int max_temp) {
        this.max_temp = max_temp;
    }

    public int getEve_temp() {
        return eve_temp;
    }

    public void setEve_temp(int eve_temp) {
        this.eve_temp = eve_temp;
    }

    public int getNight_temp() {
        return night_temp;
    }

    public void setNight_temp(int night_temp) {
        this.night_temp = night_temp;
    }

    public int getDay_temp() {
        return day_temp;
    }

    public void setDay_temp(int day_temp) {
        this.day_temp = day_temp;
    }

    public int getMorn_temp() {
        return morn_temp;
    }

    public void setMorn_temp(int morn_temp) {
        this.morn_temp = morn_temp;
    }

    public CityModel getmCity() {
        return mCity;
    }

    public void setmCity(CityModel mCity) {
        this.mCity = mCity;
    }


    public WeatherItemModel fill(ListItem result)
    {
        this.setDt(result.getDt());
        this.setClouds(result.getClouds());
        this.setHumidity(result.getHumidity());
        this.setClouds(result.getClouds());
        this.setPressure(result.getPressure());
        this.setSpeed(result.getSpeed());
        this.setDeg(result.getDeg());

        this.setEve_temp((int) Math.round(Math.floor(result.getTemp().getEve())));
        this.setMax_temp((int)Math.round(Math.floor(result.getTemp().getMax())));
        this.setMin_temp((int)Math.round(Math.floor(result.getTemp().getMin())));
        this.setMorn_temp((int)Math.round(Math.floor(result.getTemp().getMorn())));
        this.setDay_temp((int)Math.round(Math.floor(result.getTemp().getDay())));

        if(result.getWeather().size()>0&&result.getWeather().get(0)!=null)
        {
            this.setWeather_id(result.getWeather().get(0).getId());
            this.setDescription(result.getWeather().get(0).getDescription());
            this.setIcon(result.getWeather().get(0).getIcon());
            this.setStatus(result.getWeather().get(0).getMain());


        }

        return this;
    }
}
