package com.example.nikhil.recyclerview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by nikhil on 5/5/17.
 */

public class HourActivity extends AppCompatActivity{
    private static final String USGS_REQUEST_URLEarthHour = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    private static final String USGS_REQUEST_URLEarthHour2 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_hour.geojson";
    private static final String USGS_REQUEST_URLEarthHour3 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_hour.geojson";
    private static final String USGS_REQUEST_URLEarthHour4 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_hour.geojson";
    private static final String USGS_REQUEST_URLEarthHour5 = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_hour.geojson";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    private NavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation);
        Navigation_View();
        execute1();
    }

    private void execute1(){

        final HourActivity.EathquakeAsyc task1 = new HourActivity.EathquakeAsyc();
        task1.execute(USGS_REQUEST_URLEarthHour);

    }
    private void execute2(){

        final HourActivity.EathquakeAsyc task2 = new HourActivity.EathquakeAsyc();
        task2.execute(USGS_REQUEST_URLEarthHour2);

    }
    private void execute3(){

        final HourActivity.EathquakeAsyc task3 = new HourActivity.EathquakeAsyc();
        task3.execute(USGS_REQUEST_URLEarthHour3);

    }
    private void execute4(){

        final HourActivity.EathquakeAsyc task4 = new HourActivity.EathquakeAsyc();
        task4.execute(USGS_REQUEST_URLEarthHour4);

    }
    private void execute5(){

        final HourActivity.EathquakeAsyc task5 = new HourActivity.EathquakeAsyc();
        task5.execute(USGS_REQUEST_URLEarthHour5);

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
                        Intent x=new Intent(HourActivity.this,MonthActivity.class);
                        startActivity(x);
                        break;
                    case R.id.weekEarthNavig:
                        Intent x2=new Intent(HourActivity.this,WeekActivity.class);
                        startActivity(x2);

                        break;
                    case R.id.dayEarthNavig:
                        Intent x3=new Intent(HourActivity.this,DayActivity.class);
                        startActivity(x3);

                        break;
                    case R.id.hourEarthNavig:
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
