package com.seangajjar.googeo4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private String mapURL = "http://maps.google.com/?q=";
    public final static String EXTRA_MESSAGE = "com.seangajjar.MESSAGE";
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        setUpMapIfNeeded();
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.toolbar_fragment,
                container, false);
        return view;
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
*/
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    public void sendMessage(View view){

        EditText editText =(EditText) findViewById(R.id.editText1);
        String message = editText.getText().toString();
        Log.v("tag", message);
        try{
            showMap(message);
        }catch (Exception e) {

        }


//        String message2 = "geo:0,0?";
//        Uri sender = Uri.parse(message2).buildUpon().appendQueryParameter("q",message).build();
//        showMap(sender);
    }
    public void showMap(String geoLocation) throws IOException {
        //Intent intent = new Intent(Intent.ACTION_VIEW, geoLocation);
        //intent.setData(geoLocation);
        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MapsActivity.EXTRA_MESSAGE);

        Geocoder gc = new Geocoder(this, Locale.getDefault());

        if(gc.isPresent()){

            List<Address> list = gc.getFromLocationName(geoLocation, 1);

            Address address = list.get(0);
            System.out.println("locat "+address.getLatitude() + address.getLongitude());
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            LatLng hereItIs = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude())).title(address.toString()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hereItIs, 5));
            System.out.println("country" + address.getCountryName());
// Create the text view
      /*      TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setText("You're in: " + address.getCountryName());
            setContentView(textView); */
        }



        // Set the text view as the activity layout
        //setContentView(textView);

        // intent.setPackage("com.seangajjar.googeo4.MapsActivity");
        // if (intent.resolveActivity(getPackageManager()) != null) {
        //     startActivity(intent);
        // }
    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        /*Location myLoc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(myLoc != null) {
            LatLng hereItIs = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
            mMap.addMarker(new MarkerOptions().position(hereItIs));
        }*/
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
