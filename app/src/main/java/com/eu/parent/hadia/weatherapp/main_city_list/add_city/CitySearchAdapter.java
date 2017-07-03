package com.eu.parent.hadia.weatherapp.main_city_list.add_city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.eu.parent.hadia.weatherapp.R;
import com.survivingwithandroid.weather.lib.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadia on 7/3/17.
 */


public class CitySearchAdapter extends ArrayAdapter<City> implements Filterable {

    private List<City> cityList;
    private Context ctx;

    public CitySearchAdapter(Context ctx, List<City> cityList) {
        super(ctx, R.layout.city_row);
        this.cityList = cityList;
        this.ctx = ctx;
        getFilter();
    }

    @Override
    public City getItem(int position) {
        if (cityList != null)
            return cityList.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if (cityList == null)
            return 0;

        return cityList.size();
    }

    @Override
    public long getItemId(int position) {
        if (cityList == null)
            return -1;

        return cityList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.city_row, null, false);
        }

        TextView tv = (TextView) v.findViewById(R.id.descrCity);

        tv.setText(cityList.get(position).getName() + "," + cityList.get(position).getCountry());

        return v;
    }

    private CityFilter cityFilter;

    /**
     * Get custom filter
     *
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (cityFilter == null) {
            cityFilter = new CityFilter();
        }

        return cityFilter;
    }

    /**
     * Custom filter for city
     * Filter content in city list according to the search text
     */
    private class CityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<City> tempList = new ArrayList<City>();

                // search content in friend list
                for (City city_item : cityList) {
                    if (city_item.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(city_item);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = cityList.size();
                filterResults.values = cityList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cityList = (ArrayList<City>) results.values;
            notifyDataSetChanged();
        }
    }


}

