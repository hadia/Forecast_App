package com.eu.parent.hadia.weatherapp.main_city_list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eu.parent.hadia.weatherapp.R;
import com.eu.parent.hadia.weatherapp.model.FiveDaysModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FiveDaysViewAdapter extends RecyclerView.Adapter<FiveDaysViewHolders> {

    private List<FiveDaysModel> dailyWeather;

    protected Context context;

    public FiveDaysViewAdapter(Context context, List<FiveDaysModel> dailyWeather) {
        this.dailyWeather = dailyWeather;
        this.context = context;
    }

    @Override
    public FiveDaysViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        FiveDaysViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.five_days_layout, parent, false);
        viewHolder = new FiveDaysViewHolders(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FiveDaysViewHolders holder, int position) {

        holder.dayOfWeek.setText(dailyWeather.get(position).getDayOfWeek());
        if (!dailyWeather.get(position).getWeatherIcon().isEmpty()) {
            Picasso.with(holder.weatherIcon.getContext())
                    .load("http://openweathermap.org/img/w/" + dailyWeather.get(position).getWeatherIcon() + ".png")
                    .into(holder.weatherIcon);
        }


        double mTemp = Double.parseDouble(dailyWeather.get(position).getWeatherResult());
        holder.weatherResult.setText(String.valueOf(Math.round(mTemp)) + "°");

    }

    @Override
    public int getItemCount() {
        return dailyWeather.size();
    }

}
