package com.edu.aimt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.LineDataProvider;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     LineChart mChart;
    NavigationView navigationView;
    GpsTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view  = navigationView.getHeaderView(0);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(0);
        imageView.setImageBitmap(new BitmapDisplayer().convertToBitmap(BitmapDisplayer.buildArtist(9, this), 54, 54));
        if(Build.VERSION.SDK_INT>=21)
            getWindow().setStatusBarColor(Color.parseColor("#371B6B"));
        mChart = (LineChart) findViewById(R.id.chart1);
        registerReceiver(broadcastReceiver, new IntentFilter("com.edu.aimt.location"));
        setupChart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_attendance) {
            // Handle the camera action

          Intent intent = new Intent(this,AttendanceActivity.class);
          startActivity(intent);
        }
        else if(id == R.id.nav_home){

        }
        else if(id == R.id.nav_schedule){
            Intent i = new Intent(this,BusSchedule.class);
            startActivity(i);
        }
        else if(id == R.id.nav_bunk){
            Intent i = new Intent(this,BunkActivity.class);
            startActivity(i);
         }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onDestroy(){
        unregisterReceiver(broadcastReceiver);
        if(gps!=null)
            gps.stopUsingGPS();
        super.onDestroy();
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "com.edu.aimt.location".equals(intent.getAction())) {
                 if(gps!=null && gps.canGetLocation()){
                     double latitude = gps.getLatitude();
                     double longitude = gps.getLongitude();
                     TextView stop = (TextView)findViewById(R.id.stop_covered);
                     if(latitude>0.0 && longitude>0.0){
                         Toast.makeText(getBaseContext(),"Latitude:"+latitude+"\n Longitude:"+longitude,Toast.LENGTH_SHORT).show();
                         if(distance(latitude,longitude,26.6405736,80.9349062)<0.2) {
                             Toast.makeText(getBaseContext(), "Location detected as AIMT", Toast.LENGTH_LONG).show();
                             if (stop != null) {
                                 stop.setText("10/10");
                             }
                         }
                     }
                 }
            }
        }
    };
    public void showGps(View view){
        gps = new GpsTracker(this);
        // check if GPS enabled
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            TextView stop = (TextView)findViewById(R.id.stop_covered);
            if(latitude>0.0 && longitude>0.0){
                if(distance(latitude,longitude,26.6405736,80.9349062)<0.2) {
                    Log.i("Location", "lat:" + latitude + " long:" + longitude);
                    Toast.makeText(this,"Latitude:"+latitude+"\n Longitude:"+longitude,Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Location detected as AIMT", Toast.LENGTH_LONG).show();
                    if (stop != null) {
                        stop.setText("10/10");
                    }
                }
            }
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
    /** calculates the distance between two locations in MILES */
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return earthRadius * c; // output distance, in MILES
    }
    @Override
    public void onResume(){
        super.onResume();
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }
    private void setupChart(){

        mChart.setViewPortOffsets(0, 20, 0, 0);
        mChart.setBackgroundColor(Color.TRANSPARENT);
        // no description text
        mChart.setDescription("");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        XAxis x = mChart.getXAxis();
        x.setEnabled(true);
        x.setTextColor(Color.WHITE);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x.setDrawGridLines(false);
        YAxis y = mChart.getAxisLeft();

        y.setLabelCount(6, false);
        y.setStartAtZero(false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.TRANSPARENT);

        mChart.getAxisRight().setEnabled(false);
        // add data
        setData(45, 20);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);
        mChart.setHighlightEnabled(true);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener(){
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                if (e == null)
                    return;


                PointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

                Entry a=mChart.getEntryByTouchPoint(position.x, position.y);

                Toast.makeText(getBaseContext(),"Day: "+Math.round(a.getXIndex())+" Attendance: "+Math.round(a.getVal()),
                        Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected() {
            };
        });
        // dont forget to refresh the drawing
        mChart.invalidate();

    }
    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 1; i < 31; i++) {
            xVals.add(i + "");
        }

        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        for (int i = 1; i < 31; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *

            vals1.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1, "DataSet 1");
        set1.setDrawCubic(true);
        set1.setCubicIntensity(0.2f);
        //set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setLineWidth(1.8f);
        set1.setCircleSize(4f);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.WHITE);
        set1.setFillColor(Color.TRANSPARENT);
        set1.setFillAlpha(100);
        set1.setDrawFilled(false);
        set1.setDrawHorizontalHighlightIndicator(true);
        set1.setFillFormatter(new FillFormatter() {
            @Override
            public float getFillLinePosition(LineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        data.setValueTextSize(9f);
        data.setDrawValues(false);

        // set data
        mChart.setData(data);

    }

}
