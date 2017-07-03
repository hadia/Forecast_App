package com.eu.parent.hadia.weatherapp.main_city_list.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.eu.parent.hadia.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hadia on 7/3/17.
 */

public class CityListHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.city_name)
    public TextView cityName;
    @BindView(R.id.temp_info)
    public TextView weatherInformation;
    @BindView(R.id.temp_status)
    public TextView tempStatus;
    @BindView(R.id.temp_Max_min)
    public TextView tempMaxMIN;
    @BindView(R.id.weather_icon)
    public ImageView imageView;
    @BindView(R.id.swipe)
    SwipeRevealLayout swipeLayout;
    @BindView(R.id.fg_layout)
    FrameLayout frogroundLayout;
    @BindView(R.id.trash)
    ImageButton buttonDelete;
    @BindView(R.id.weather_daily_list)
    public RecyclerView recyclerView;

    public FiveDaysViewAdapter recyclerViewAdapter;
    private OnCityListItemActionsListener onCityListItemActionsListener;
    public CityListHolder(final View itemView,  OnCityListItemActionsListener onCityListItemActionsListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.onCityListItemActionsListener=onCityListItemActionsListener;

    }
    @OnClick(R.id.trash)
    public void onDeleteClicked(View view) {
        onCityListItemActionsListener.onDeleteClicked(view, getAdapterPosition());
    }

    @OnClick(R.id.fg_layout)
    public void onItemClicked(View view) {
        onCityListItemActionsListener.onItemClicked(view, getAdapterPosition());
    }
}