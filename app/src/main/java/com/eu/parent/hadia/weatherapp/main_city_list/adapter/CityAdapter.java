package com.eu.parent.hadia.weatherapp.main_city_list.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.eu.parent.hadia.weatherapp.R;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.eu.parent.hadia.weatherapp.model.FiveDaysModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
            long time = locationObjects.get(position).getmWeatherModel().get(i).getDt();
            String shortDay = convertTimeToDay(time);
            String temp =""+ locationObjects.get(position).getmWeatherModel().get(i).getDay_temp();
            int[] everyday = new int[]{0,0,0,0,0,0,0};
            if (convertTimeToDay(time).equals("Mon") && everyday[0] < 1) {
                daysOfTheWeek.add(new FiveDaysModel(shortDay, locationObjects.get(position).getmWeatherModel().get(i).getIcon(), temp));
                everyday[0] = 1;
            }
            if (convertTimeToDay(time).equals("Tue") && everyday[1] < 1) {
                daysOfTheWeek.add(new FiveDaysModel(shortDay, locationObjects.get(position).getmWeatherModel().get(i).getIcon(), temp));
                everyday[1] = 1;
            }
            if (convertTimeToDay(time).equals("Wed") && everyday[2] < 1) {
                daysOfTheWeek.add(new FiveDaysModel(shortDay, locationObjects.get(position).getmWeatherModel().get(i).getIcon(), temp));
                everyday[2] = 1;
            }
            if (convertTimeToDay(time).equals("Thu") && everyday[3] < 1) {
                daysOfTheWeek.add(new FiveDaysModel(shortDay, locationObjects.get(position).getmWeatherModel().get(i).getIcon(), temp));
                everyday[3] = 1;
            }
            if (convertTimeToDay(time).equals("Fri") && everyday[4] < 1) {
                daysOfTheWeek.add(new FiveDaysModel(shortDay, locationObjects.get(position).getmWeatherModel().get(i).getIcon(), temp));
                everyday[4] = 1;
            }
            if (convertTimeToDay(time).equals("Sat") && everyday[5] < 1) {
                daysOfTheWeek.add(new FiveDaysModel(shortDay, locationObjects.get(position).getmWeatherModel().get(i).getIcon(), temp));
                everyday[5] = 1;
            }
            if (convertTimeToDay(time).equals("Sun") && everyday[6] < 1) {
                daysOfTheWeek.add(new FiveDaysModel(shortDay, locationObjects.get(position).getmWeatherModel().get(i).getIcon(), temp));
                everyday[6] = 1;
            }

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

    private String convertTimeToDay(long timestamp){
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
