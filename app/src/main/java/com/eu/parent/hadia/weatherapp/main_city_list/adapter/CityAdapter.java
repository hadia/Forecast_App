package com.eu.parent.hadia.weatherapp.main_city_list.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.eu.parent.hadia.weatherapp.R;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.eu.parent.hadia.weatherapp.model.FiveDaysModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityListHolder> {

    private List<CityModel> locationObjects;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private Context mContext;
    private OnCityListItemActionsListener onCityListItemActionsListener;



    public CityAdapter(Context context, List<CityModel> locationObjects,OnCityListItemActionsListener onCityListItemActionsListener) {
        this.locationObjects = locationObjects;
        this.mContext = context;
        this.onCityListItemActionsListener=onCityListItemActionsListener;


    }

    @Override
    public CityListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CityListHolder holder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list, parent, false);
        holder = new CityListHolder(layoutView,onCityListItemActionsListener);

        return holder;
    }
    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }
    @Override
    public void onBindViewHolder(final CityListHolder holder, final int position) {
        holder.cityName.setText(locationObjects.get(position).getName());
        holder.tempStatus.setText(locationObjects.get(position).getmWeatherModel().get(0).getStatus());
        if (!locationObjects.get(position).getmWeatherModel().get(0).getIcon().isEmpty()) {
            Picasso.with(holder.itemView.getContext())
                    .load("http://openweathermap.org/img/w/" + locationObjects.get(position).getmWeatherModel().get(0).getIcon() + ".png")
                    .into(holder.imageView);
        }
        holder.weatherInformation.setText(locationObjects.get(position).getmWeatherModel().get(0).getDay_temp() + " " + (char) 0x00B0);
        holder.tempMaxMIN.setText(locationObjects.get(position).getmWeatherModel().get(0).getMax_temp() + " " + (char) 0x00B0 + " / " + locationObjects.get(position).getmWeatherModel().get(0).getMin_temp() + " " + (char) 0x00B0);
// Save/restore the open/close state.
        // You need to provide a String id which uniquely defines the data object.
        viewBinderHelper.bind(holder.swipeLayout, locationObjects.get(position).getId()+"");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
        holder.recyclerView.setLayoutManager(gridLayoutManager);
        holder.recyclerView.setHasFixedSize(true);
        final List<FiveDaysModel> daysOfTheWeek = new ArrayList<FiveDaysModel>();
        for (int i = 0; i < 5; i++) {
            daysOfTheWeek.add(new FiveDaysModel().fill(locationObjects.get(position).getmWeatherModel().get(i)) );

        }
        holder.  recyclerViewAdapter = new FiveDaysViewAdapter(mContext, daysOfTheWeek);
        holder. recyclerView.setAdapter( holder.  recyclerViewAdapter );
        holder.recyclerViewAdapter.notifyDataSetChanged();

        holder. recyclerView.requestFocus();
          holder. recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return this.locationObjects.size();
    }



}
