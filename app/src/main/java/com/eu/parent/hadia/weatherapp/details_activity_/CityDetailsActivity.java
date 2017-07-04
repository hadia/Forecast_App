package com.eu.parent.hadia.weatherapp.details_activity_;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eu.parent.hadia.weatherapp.R;
import com.eu.parent.hadia.weatherapp.main_city_list.adapter.FiveDaysViewAdapter;
import com.eu.parent.hadia.weatherapp.model.FiveDaysModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by hadia on 7/4/17.
 */
@RequiresPresenter(CityDetailsPresenterImp.class)
public class CityDetailsActivity extends NucleusAppCompatActivity<CityDetailsPresenterImp> {

    @BindView(R.id.weather_daily_list)
    public RecyclerView recyclerView;

    private FiveDaysViewAdapter recyclerViewAdapter;
    @BindView(R.id.city_country)
    public TextView cityCountry;
    @BindView(R.id.current_date)
    public TextView currentDate;
    @BindView(R.id.weather_icon)
    public ImageView weatherImage;
    @BindView(R.id.wind_result)
    public TextView windResult;
    @BindView(R.id.humidity_result)
    public TextView humidityResult;
    @BindView(R.id.current_temp)
    public TextView cityTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_detail_activity);
        ButterKnife.bind(this);
        getPresenter().requetWeatherData(getIntent().getIntExtra("int_value", 0));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public void bindCityTemp(String city) {
        cityTemp.setText(city);
    }

    public void bindCity(String city) {
        cityCountry.setText(city);
    }

    public void bindDate(String date) {
        currentDate.setText(date);
    }

    public void bindWindResult(String windResultText) {
        windResult.setText(windResultText);
    }

    public void bindHumidityResult(String humidityResultText) {
        humidityResult.setText(humidityResultText);
    }

    public void bindWeatherImage(String weatherImageURL) {
        if (!weatherImageURL.isEmpty()) {
            Picasso.with(this)
                    .load("http://openweathermap.org/img/w/" + weatherImageURL + ".png")
                    .into(weatherImage);
        }

    }

    public void bindRecycleView(List<FiveDaysModel> daysOfTheWeek) {

        recyclerViewAdapter = new FiveDaysViewAdapter(this, daysOfTheWeek);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.requestFocus();
        recyclerView.setVisibility(View.VISIBLE);
    }
}
