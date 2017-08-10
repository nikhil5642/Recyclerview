package com.example.nikhil.recyclerview;

import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

public class MonthActivity extends AppCompatActivity {
    private static final String USGS_REQUEST_URLEarthMonth = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";
    private static final String USGS_REQUEST_URLEarthMonth2 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_month.geojson";
    private static final String USGS_REQUEST_URLEarthMonth3 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_month.geojson";
    private static final String USGS_REQUEST_URLEarthMonth4 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_month.geojson";
    private static final String USGS_REQUEST_URLEarthMonth5 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    private NavigationView navigation;

    private dbhandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
dbhandler=new dbhandler(this);
        setContentView(R.layout.navigation);
        Navigation_View();
        execute1();
    }

    private void execute1() {

        final MonthActivity.EathquakeAsyc task1 = new MonthActivity.EathquakeAsyc();
        task1.execute(USGS_REQUEST_URLEarthMonth);

    }

    private void execute2() {

        final MonthActivity.EathquakeAsyc task2 = new MonthActivity.EathquakeAsyc();
        task2.execute(USGS_REQUEST_URLEarthMonth2);

    }

    private void execute3() {

        final MonthActivity.EathquakeAsyc task3 = new MonthActivity.EathquakeAsyc();
        task3.execute(USGS_REQUEST_URLEarthMonth3);

    }

    private void execute4() {

        final MonthActivity.EathquakeAsyc task4 = new MonthActivity.EathquakeAsyc();
        task4.execute(USGS_REQUEST_URLEarthMonth4);

    }

    private void execute5() {

        final MonthActivity.EathquakeAsyc task5 = new MonthActivity.EathquakeAsyc();
        task5.execute(USGS_REQUEST_URLEarthMonth5);

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
            dbhandler.getdata_for_sqlite(earth);
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
                        break;
                    case R.id.weekEarthNavig:
                        Intent x2 = new Intent(MonthActivity.this, WeekActivity.class);
                        startActivity(x2);
                        break;
                    case R.id.dayEarthNavig:
                        Intent x3 = new Intent(MonthActivity.this, DayActivity.class);
                        startActivity(x3);

                        break;
                    case R.id.hourEarthNavig:
                        Intent x4 = new Intent(MonthActivity.this, HourActivity.class);
                        startActivity(x4);

                        break;
                }
                return false;
            }
        });

    }
}