package com.eu.parent.hadia.weatherapp.main_city_list;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eu.parent.hadia.weatherapp.R;
import com.eu.parent.hadia.weatherapp.main_city_list.adapter.CityAdapter;
import com.eu.parent.hadia.weatherapp.main_city_list.adapter.OnCityListItemActionsListener;
import com.eu.parent.hadia.weatherapp.main_city_list.add_city.CitySearchAdapter;
import com.eu.parent.hadia.weatherapp.model.CityModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.lapism.searchview.SearchView;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.model.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(MainActivityPresenterImp.class)
public class MainActivity extends NucleusAppCompatActivity<MainActivityPresenterImp> implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, SearchView.OnQueryTextListener {
    private final int LOCATION_PERMISSION_DENINED = 0;
    private final int LOCATION_PERMISSION_GRANTED = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    public CityAdapter cityAdapter;
    @BindView(R.id.city_list)
    public RecyclerView locationRecyclerView;
    private SearchView searchView;
    private static WeatherClient weatherclient;
    CitySearchAdapter ca;
    private ListView cityListView;
    private City currentCity;
    Toolbar toolbar;
    @BindView(R.id.pbHeaderProgress)
    ProgressBar pbHeaderProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isNetworkAvailable())
                checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        } else {
            Log.d("onCreate", "Google Play Services available.");
        }

        if (mGoogleApiClient == null) {
            buildGoogleApiClient();

        }
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void bindLocations(List<CityModel> cityModelList) {

        cityAdapter = new CityAdapter(this, cityModelList, new OnCityListItemActionsListener() {
            @Override
            public void onDeleteClicked(View v, int position) {
                pbHeaderProgress.setVisibility(View.VISIBLE);
                getPresenter().onDeleteitem(position);
            }

            @Override
            public void onItemClicked(View v, int position) {
                getPresenter().onItemClicked(position);
            }
        });
        locationRecyclerView.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();
        locationRecyclerView.requestFocus();
        locationRecyclerView.setVisibility(View.VISIBLE);
        if (pbHeaderProgress != null)
            pbHeaderProgress.setVisibility(View.GONE);

    }

    /**
     * RemoveLocation
     *
     * @param postion
     * @param listSize
     */
    public void bindRemoveLocations(int postion, int listSize) {
        cityAdapter.notifyItemRemoved(postion);
        cityAdapter.notifyItemRangeChanged(postion, listSize);
        pbHeaderProgress.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_search) {
            // We show the dialog
            if (getPresenter().getCityCount() < 5) {
                Dialog d = createDialog();
                d.show();
            } else {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder
                        .setMessage(R.string.stored_location)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.dismiss();
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("LocationFaild", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //Place current location marker
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", latitude, longitude));
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");


    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {


            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                            if (mGoogleApiClient != null&&mGoogleApiClient.isConnected()) {
                                mGoogleApiClient.disconnect();
                                mGoogleApiClient.connect();

                            } else {
                                buildGoogleApiClient();
                            }
                    }

                } else {
                    requestWeatherData(LOCATION_PERMISSION_DENINED);
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    protected synchronized void buildGoogleApiClient() {
//
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (mGoogleApiClient != null && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleApiClient.connect();
        }
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    /*
    * onStart : Called when the activity is becoming visible to the user.
    * */
    @Override
    protected void onStart() {
        super.onStart();

    }

    /*
    * onStop : Called when the activity is no longer visible to the user
    * */
    @Override
    protected void onStop() {
        super.onStop();

        //Disconnect the google client api connection.
        if (mGoogleApiClient != null && (mGoogleApiClient.isConnected())) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && !(mGoogleApiClient.isConnected())) {
            mGoogleApiClient.connect();

        }
    }


    @Override
    protected void onPause() {
        try {
            super.onPause();

            if (mGoogleApiClient != null && (mGoogleApiClient.isConnected())) {
                stopLocationUpdates();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient != null)
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    /*
       * Callback method of GoogleApiClient.ConnectionCallbacks
       * */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                requestWeatherData(LOCATION_PERMISSION_GRANTED);
            }
//            if (mLastLocation != null) {
//                Toast.makeText(MainActivity.this, "Your Current Location connected", Toast.LENGTH_LONG).show();
//            }

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    /**
     * request weatherData based on network avialabilty
     *
     * @param status
     */
    private void requestWeatherData(int status) {
        if (!isNetworkAvailable()) {
            //app is offline

            getPresenter().requetofflineWeatherData();
        } else {
            //app is online
            switch (status) {
                case LOCATION_PERMISSION_DENINED:
                    getPresenter().requetWeatherData("London", 0, 0);
                    break;
                case LOCATION_PERMISSION_GRANTED:
                    getPresenter().requetWeatherData("", mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }


        }
        // pbHeaderProgress.setVisibility(View.VISIBLE);
    }


    public void bindSearchList(CitySearchAdapter citySearchAdapter) {
        ca = citySearchAdapter;
        cityListView.setAdapter(ca);
    }

    private Dialog createDialog() {
        getPresenter().initWeatherClient();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.select_city_dialog, null);
        builder.setView(v);

        final EditText et = (EditText) v.findViewById(R.id.ptnEdit);
        cityListView = (ListView) v.findViewById(R.id.cityList);
        cityListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentCity = (City) parent.getItemAtPosition(position);

                et.setText(currentCity.getName() + "," + currentCity.getCountry());
                cityListView.setAdapter(new CitySearchAdapter(MainActivity.this, new ArrayList<City>()));

            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 3) {
                    // We start searching
                    getPresenter().searchCityList(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(currentCity!=null) {
                    dialog.dismiss();
                    // We update toolbar

                    getPresenter().getCityData(Integer.parseInt(currentCity.getId()), currentCity.getName());
                }
                // toolbar.setTitle( + "," + currentCity.getCountry());
                // Start getting weather
                //getWeather();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ca.getFilter().filter(newText);
        return true;
    }
}

