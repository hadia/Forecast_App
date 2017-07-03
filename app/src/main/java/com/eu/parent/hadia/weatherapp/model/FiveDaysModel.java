package com.eu.parent.hadia.weatherapp.model;


public class FiveDaysModel {

    private String dayOfWeek;

    private String weatherIcon;

    private String weatherResult;



    public FiveDaysModel(String dayOfWeek, String weatherIcon, String weatherResult) {
        this.dayOfWeek = dayOfWeek;
        this.weatherIcon = weatherIcon;
        this.weatherResult = weatherResult;

    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getWeatherResult() {
        return weatherResult;
    }



}
