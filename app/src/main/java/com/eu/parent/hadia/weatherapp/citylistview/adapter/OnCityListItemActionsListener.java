package com.eu.parent.hadia.weatherapp.citylistview.adapter;

import android.view.View;

/**
 * Created by hadia on 7/3/17.
 */

public interface OnCityListItemActionsListener {
    void onDeleteClicked(View v, int position);

    void onItemClicked(View v, int position);
}
