package com.edu.aimt;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LineChart mChart;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mChart = (LineChart) findViewById(R.id.chart1);
        setupChart();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id == R.id.nav_schedule){
            Intent i = new Intent(this,BusSchedule.class);
            i.putExtra("type",true);
            startActivity(i);
        }
        else if(id == R.id.nav_home){

        }
        else if(id==R.id.nav_send){
            BitmapDisplayer.sendEmail(this);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
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
    private void setupChart(){
        mChart.setViewPortOffsets(0, 20, 0, 0);
        mChart.setBackgroundColor(Color.TRANSPARENT);
        // no description text
        mChart.setDescription("");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);

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
        setData(45, 80);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);
        mChart.setHighlightEnabled(true);

        // dont forget to refresh the drawing
        mChart.invalidate();

    }
    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        final String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for (int i = 1; i < months.length; i++) {
            xVals.add(months[i]);
        }

        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        for (int i = 1; i < months.length; i++) {
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
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                if (e == null)
                    return;


                PointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

                Entry a = mChart.getEntryByTouchPoint(position.x, position.y);

                Toast.makeText(getBaseContext(), "Month: " + months[Math.round(a.getXIndex())] + " Attendance: " + Math.round(a.getVal())+"%",
                        Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected() {
            }

            ;
        });
        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        data.setValueTextSize(9f);
        data.setDrawValues(false);

        // set data
        mChart.setData(data);

    }
}
