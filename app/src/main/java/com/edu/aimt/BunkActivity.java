package com.edu.aimt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BunkActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunk);
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
        setDataSet();
    }
    public void setDataSet(){
        final ArrayList<DataObject> res;
        final RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.attendance_list);
        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        res=getDataSet();
        final BunkAdapter mAdapter = new BunkAdapter(res);
        mAdapter.setOnItemClickListener(new BunkAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(final int position, View v) {
                // TODO Auto-generated method stub
                final TextView name = (TextView) mLayoutManager.findViewByPosition(position).findViewById(R.id.mainContent);
           new AlertDialog.Builder(BunkActivity.this)
                   .setTitle("Confirm")
                   .setMessage("Are you sure that you want to mark "+name.getText().toString()+" as bunked ?")
                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                           Snackbar.make(findViewById(R.id.drawer_layout), name.getText().toString() + " has Bunked", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                               @Override
                               public void onClick(View arg0) {
                                   // TODO Auto-generated method stub
                                   mAdapter.addItem(res.get(position), position);
                               }
                           }).show();
                           mAdapter.deleteItem(position);
                       }
                   })
                   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   })
                  .show();
                }


        });
        mRecyclerView.setAdapter(mAdapter);


    }
    private ArrayList<DataObject> getDataSet() {
        ArrayList<DataObject> results = new ArrayList<DataObject>();
        String branches[]=DetailsManager.getBranches(10);
        String names[] =DetailsManager.getNames(10);
        for (int index = 0; index < 10; index++) {
            DataObject obj = new DataObject(names[index],
                    branches[index]+" Year",
                    ""+index,
                    ""+(20-index));
            results.add(index, obj);
        }
        return results;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_attendance) {
            // Handle the camera action
           Intent i =new Intent(this,AttendanceActivity.class);
           startActivity(i);
           supportFinishAfterTransition();
        }
        else if(id == R.id.nav_home) {
            supportFinishAfterTransition();
        }
        else if(id == R.id.nav_schedule){
            Intent i = new Intent(this,BusSchedule.class);
            startActivity(i);
            supportFinishAfterTransition();
        }
        else if(id == R.id.nav_bunk){

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
        navigationView.getMenu().findItem(R.id.nav_bunk).setChecked(true);
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

}
