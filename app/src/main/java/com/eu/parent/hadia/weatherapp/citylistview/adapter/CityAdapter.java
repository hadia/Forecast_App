package com.eu.parent.hadia.weatherapp.citylistview.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.eu.parent.hadia.weatherapp.R;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityListHolder> implements CompoundButton.OnCheckedChangeListener{

    private List<CityModel> locationObjects;

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

    @Override
    public void onBindViewHolder(final CityListHolder holder, final int position) {
        holder.cityName.setText(locationObjects.get(position).getName());
        holder.tempStatus.setText(locationObjects.get(position).getmWeatherModel().get(0).getStatus());
        if (!locationObjects.get(position).getmWeatherModel().get(0).getIcon().isEmpty()) {
            Picasso.with(holder.itemView.getContext())
                    .load("http://openweathermap.org/img/w/"+locationObjects.get(position).getmWeatherModel().get(0).getIcon()+".png")
                    .into(holder.imageView);
        }
        holder.weatherInformation.setText(locationObjects.get(position).getmWeatherModel().get(0).getDay_temp()+" "+ (char) 0x00B0 );
        holder.tempMaxMIN.setText(locationObjects.get(position).getmWeatherModel().get(0).getMax_temp()+" "+ (char) 0x00B0 +" / "+locationObjects.get(position).getmWeatherModel().get(0).getMin_temp()+" "+ (char) 0x00B0 );
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);


//        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mItemManger.removeShownLayouts(holder.swipeLayout);
//                locationObjects.remove(position);
//                notifyItemRemoved(position);
              // notifyItemRangeChanged(position, locationObjects.size());
//                mItemManger.closeAllItems();
//                Toast.makeText(view.getContext(), "Deleted " + holder.cityName.getText().toString() + "!", Toast.LENGTH_SHORT).show();
//            }
//        });

//        holder.deleteText.setTag(R.id.TAG_KEY, String.valueOf(locationObjects.get(position).getId()));
//
//        holder.deleteText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int databaseIndex = locationObjects.get(position).getId();
//                if(!holder.imageView.isChecked()){
////                    query.deleteLocation(databaseIndex);
////                    locationObjects.remove(position);
////                    notifyItemRemoved(position);
//                }
//            }
//        };

       // String buttonId = sharedPreference.getLocationInPreference();
    //    System.out.println("Stored id " + buttonId);

//        holder.imageView.setOnCheckedChangeListener(this);
//        setRadioButtonId(holder.imageView, position);
//        allRadioButton.add(new ViewEntityObject(holder.imageView, locationObjects.get(position).getLocationCity()));
//
//        String storedCityLocation = sharedPreference.getLocationInPreference();
//        if(allRadioButton.get(position).getRadioName().equals(storedCityLocation)){
//            holder.imageView.setChecked(true);
//        }

    }

    @Override
    public int getItemCount() {
        return this.locationObjects.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked){
            RadioButton radioButton = (RadioButton)compoundButton;
            int checkedRadioId = radioButton.getId();
//            for(int i = 0; i < allRadioButton.size(); i++){
////                if(allRadioButton.get(i).getRadioButton().getId() != checkedRadioId){
////                    allRadioButton.get(i).getRadioButton().setChecked(false);
////                }else{
////                    String name = allRadioButton.get(i).getRadioName();
////                    sharedPreference.setLocationInPreference(name);
////                }
//            }
        }

    }

    public OnCityListItemActionsListener getOnCityListItemActionsListener() {
        return onCityListItemActionsListener;
    }

    public void setOnCityListItemActionsListener(OnCityListItemActionsListener onCityListItemActionsListener) {
        this.onCityListItemActionsListener = onCityListItemActionsListener;
    }

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
        SwipeLayout swipeLayout;
        @BindView(R.id.fg_layout)
        LinearLayout frogroundLayout;
        @BindView(R.id.trash)
        ImageButton buttonDelete;
        private OnCityListItemActionsListener onCityListItemActionsListener;
        public CityListHolder(final View itemView,OnCityListItemActionsListener onCityListItemActionsListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onCityListItemActionsListener=onCityListItemActionsListener;
            //deleteText.setTextColor(Color.RED);

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
}
