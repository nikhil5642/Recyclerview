package com.example.nikhil.recyclerview;

import android.app.Dialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class DayActivity extends AppCompatActivity {
    private static final String USGS_REQUEST_URLEarthDay = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";
    private static final String USGS_REQUEST_URLEarthDay2 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
    private static final String USGS_REQUEST_URLEarthDay3 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson";
    private static final String USGS_REQUEST_URLEarthDay4 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_day.geojson";
    private static final String USGS_REQUEST_URLEarthDay5 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_day.geojson";

    private AdView mAdView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    private NavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);

        MobileAds.initialize(this,"ca-app-pub-2763801433251562~4584842932");

        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("3CE0E5B1588C6255E7843522E5096BA5").build();
      //  AdRequest adRequest=new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

        Navigation_View();
        execute1();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.i("Ads", "onAdClosed");
            }
        });

   }

    private void execute1(){

        final EathquakeAsyc task1 = new EathquakeAsyc();
        task1.execute(USGS_REQUEST_URLEarthDay);

    }
    private void execute2(){

        final EathquakeAsyc task2 = new EathquakeAsyc();
        task2.execute(USGS_REQUEST_URLEarthDay2);

    }
    private void execute3(){

        final EathquakeAsyc task3 = new EathquakeAsyc();
        task3.execute(USGS_REQUEST_URLEarthDay3);

    }
    private void execute4(){

        final EathquakeAsyc task4 = new EathquakeAsyc();
        task4.execute(USGS_REQUEST_URLEarthDay4);

    }
    private void execute5(){

        final EathquakeAsyc task5 = new EathquakeAsyc();
        task5.execute(USGS_REQUEST_URLEarthDay5);

    }

    public void Navigation_View() {
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mtoggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);

        mdrawerlayout.addDrawerListener(mtoggle);

        mtoggle.syncState();

        navigation = (NavigationView) findViewById(R.id.slider);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.monthEarthNavig:
                        Intent x=new Intent(DayActivity.this,MonthActivity.class);
                        startActivity(x);
                        break;
                    case R.id.weekEarthNavig:
                        Intent x2=new Intent(DayActivity.this,WeekActivity.class);
                        startActivity(x2);

                        break;
                    case R.id.dayEarthNavig:
                        break;
                    case R.id.hourEarthNavig:
                        Intent x4=new Intent(DayActivity.this,HourActivity.class);
                        startActivity(x4);

                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mtoggle.onOptionsItemSelected(item)) {
            switch (item.getItemId()) {
                case R.id.all:
                    execute1();
                    break;
                case R.id.mag4_5:
                    execute2();
                    break;
                case R.id.mag2_5:
                    execute3();
                    break;
                case R.id.mag1_0:
                    execute4();
                    break;
                case R.id.significant:
                    execute5();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        switch (item.getItemId()) {
            case R.id.all:
                execute1();
                break;
            case R.id.mag4_5:
                execute2();
                break;
            case R.id.mag2_5:
                execute3();
                break;
            case R.id.mag1_0:
                execute4();
                break;
            case R.id.significant:
                execute5();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    static HttpURLConnection connection;

    public class EathquakeAsyc extends AsyncTask<String, Void, ArrayList<object>> {
        ArrayList<object> earth = new ArrayList<object>();

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
        }
    }

    private void set(ArrayList<object> earth) {
        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new recycler_adapter(earth);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}


