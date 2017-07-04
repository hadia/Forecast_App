package com.eu.parent.hadia.weatherapp.model;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class FiveDaysModel {

    private String dayOfWeek;

    private String weatherIcon;

    private String weatherResult;

    public FiveDaysModel() {
    }

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

    public FiveDaysModel fill(WeatherItemModel itemModel) {

        long time = itemModel.getDt();
        String shortDay = convertTimeToDay(time);
        String temp = "" + itemModel.getDay_temp();
        int[] everyday = new int[]{0, 0, 0, 0, 0, 0, 0};

        this.weatherIcon = itemModel.getIcon();
        this.weatherResult = temp;
        if (convertTimeToDay(time).equals("Mon") && everyday[0] < 1) {
            this.dayOfWeek = shortDay;

            everyday[0] = 1;
        }
        if (convertTimeToDay(time).equals("Tue") && everyday[1] < 1) {
            this.dayOfWeek = shortDay;
            everyday[1] = 1;
        }
        if (convertTimeToDay(time).equals("Wed") && everyday[2] < 1) {
            this.dayOfWeek = shortDay;
            everyday[2] = 1;
        }
        if (convertTimeToDay(time).equals("Thu") && everyday[3] < 1) {
            this.dayOfWeek = shortDay;
            everyday[3] = 1;
        }
        if (convertTimeToDay(time).equals("Fri") && everyday[4] < 1) {
            this.dayOfWeek = shortDay;
            everyday[4] = 1;
        }
        if (convertTimeToDay(time).equals("Sat") && everyday[5] < 1) {
            this.dayOfWeek = shortDay;
            everyday[5] = 1;
        }
        if (convertTimeToDay(time).equals("Sun") && everyday[6] < 1) {
            this.dayOfWeek = shortDay;
            everyday[6] = 1;
        }
        return this;

    }

    private String convertTimeToDay(long timestamp) {
        String days = "";
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.setTimeInMillis(timestamp * 1000);
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        days = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        System.out.println("Our time " + days);

        return days;
    }


}
