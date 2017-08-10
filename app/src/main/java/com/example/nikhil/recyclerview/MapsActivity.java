package com.example.nikhil.recyclerview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String USGS_REQUEST_URLEarthDay = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.e("pro","ready");
        // Add a marker in Sydney and move the camera
        final MapsActivity.EathquakeAsyc task1 = new MapsActivity.EathquakeAsyc();
        task1.execute(USGS_REQUEST_URLEarthDay);
    }

    public class EathquakeAsyc extends AsyncTask<String, Void, ArrayList<object>> {
        ArrayList<object> earth = new ArrayList<object>();
        ProgressDialog progressDialog=new ProgressDialog(MapsActivity.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected ArrayList<object> doInBackground(String... params) {
            earth = Utils.data(params[0]);
            return earth;
        }

        @Override
        protected void onPostExecute(ArrayList<object> data) {
            if (earth == null) {
                return;
            }
            set(earth);
            progressDialog.dismiss();
        }
    }
    private void set(ArrayList<object> earth) {


        for(int i=0;i<earth.size();i++) {
            LatLng mark = new LatLng(earth.get(i).getLat(),earth.get(i).getLon());
            MarkerOptions marker = new MarkerOptions().position(mark).title(earth.get(i).getLocation());
            marker.icon(getMagnitudeColor(Double.parseDouble(earth.get(i).getRange())));
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mark));
        }
    }
    private BitmapDescriptor getMagnitudeColor(double magnitude) {
      BitmapDescriptor bitmapDescriptor;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                break;
            case 2:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                break;
            case 3:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                break;
            case 4:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                break;
            case 5:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                break;
            case 6:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
            case 7:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
            case 8:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
            case 9:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
            default:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
        }
        return bitmapDescriptor;
    }

}
