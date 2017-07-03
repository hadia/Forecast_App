package com.eu.parent.hadia.weatherapp.main_city_list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eu.parent.hadia.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hadia on 7/3/17.
 */

public class FiveDaysViewHolders extends RecyclerView.ViewHolder {

    private static final String TAG = FiveDaysViewHolders.class.getSimpleName();
    @BindView(R.id.day_of_week)
    public TextView dayOfWeek;
    @BindView(R.id.weather_icon)
    public ImageView weatherIcon;
    @BindView(R.id.weather_result)
    public TextView weatherResult;


    public FiveDaysViewHolders(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
