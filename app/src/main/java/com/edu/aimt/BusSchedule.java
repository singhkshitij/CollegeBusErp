package com.edu.aimt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class BusSchedule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i =getIntent();
        if(i!=null && i.hasExtra("type"))
            setContentView(R.layout.activity_bus_schedule_student);
        else
        setContentView(R.layout.activity_bus_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setData();
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
    }
    private void setData(){
        ListView list=(ListView)findViewById(R.id.schedule_list);
        String stop[]={"Bus Stop","Krishna Nagar","Parag","Alambag","Charbag","MohanLalGanj","AIMT"};
        String times[]={"Bus Time","8:30 am","8:40 am","8:45 am","8:55 am","9:05 am","9:15 am"};
        ArrayList<HashMap<String,String>> aList=new ArrayList<>();
        for(int i=0;i<stop.length;i++){
            HashMap<String,String> hm=new HashMap<>();
            hm.put("main_text",stop[i]);
            hm.put("sub_text",times[i]);
            aList.add(hm);
        }
        list.setAdapter(new ScheduleAdapter(this, aList));
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_attendance) {
            // Handle the camera action
           Intent i = new Intent(this,AttendanceActivity.class);
           startActivity(i);
           supportFinishAfterTransition();
        }
        else if(id == R.id.nav_home) {
            supportFinishAfterTransition();
        }
        else if(id == R.id.nav_schedule) {
        }
        else if(id == R.id.nav_bunk){
            Intent i = new Intent(this,BunkActivity.class);
            startActivity(i);
            supportFinishAfterTransition();
        }
        else if(id==R.id.nav_send){
            BitmapDisplayer.sendEmail(this);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
    @Override
    public void onResume(){
        super.onResume();
        navigationView.getMenu().findItem(R.id.nav_schedule).setChecked(true);
    }
}
