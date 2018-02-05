package com.edu.aimt;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lylc.widget.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    int present=0, absent=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
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
        final AttendanceAdapter mAdapter = new AttendanceAdapter(res);
        mAdapter.setOnItemClickListener(new AttendanceAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(final int position, View v) {
                // TODO Auto-generated method stub
                int resid = v.getId();
                Log.i("Position", position + "");
                TextView name = (TextView) mLayoutManager.findViewByPosition(position).findViewById(R.id.mainContent);
                if (resid == R.id.present || resid == R.id.absent) {
                    if (resid == R.id.present) {
                        present = present + 1;
                        Snackbar.make(findViewById(R.id.drawer_layout), name.getText().toString() + " is present", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                mAdapter.addItem(res.get(position), position);
                                present = present - 1;
                            }
                        }).show();
                    } else {
                        absent = absent + 1;
                        Snackbar.make(findViewById(R.id.drawer_layout), name.getText().toString() + " is absent", Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        // TODO Auto-generated method stub
                                        mAdapter.addItem(res.get(position), position);
                                        absent = absent - 1;
                                    }
                                }).show();

                    }
                    mAdapter.deleteItem(position);
                    if (mAdapter.getItemCount() <= 0)
                        setStats();
                }
            }

        });
        mRecyclerView.setAdapter(mAdapter);
    }
    private void setStats(){
        findViewById(R.id.stats_container).setVisibility(View.VISIBLE);
        final CircularProgressBar progressBar = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
        progressBar.animateProgressTo(0, (int) (present * 10), new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                progressBar.setTitle(progress + "%");

            }

            @Override
            public void onAnimationFinish() {
                progressBar.setSubTitle("Present");

            }
        });
        final CircularProgressBar progressBar1 = (CircularProgressBar) findViewById(R.id.circularprogressbar2);
        progressBar1.animateProgressTo(0, 92, new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                progressBar1.setTitle(progress + "%");

            }

            @Override
            public void onAnimationFinish() {
                progressBar1.setSubTitle("Present");
            }
        });
        DefaulterAdapter defaulterAdapter = new DefaulterAdapter(this,getDefaulterSet());
        ExpandableHeightListView listView = (ExpandableHeightListView) findViewById(R.id.defaulter_list);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(defaulterAdapter);
        listView.setExpanded(true);
    }
    private ArrayList<DataObject> getDefaulterSet() {
        ArrayList<DataObject> results = new ArrayList<DataObject>();
        String attendance[]={"32%","28%","26%","34%","40%"};
        String branches[]=DetailsManager.getBranches(5);
        String names[] =DetailsManager.getNames(5);
        for (int index = 0; index < 5; index++) {
            DataObject obj = new DataObject(names[index],
                    branches[index]+" Year",
                    attendance[index],
                    "");
            results.add(index, obj);
        }
        return results;
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
            Intent i = new Intent(this,BunkActivity.class);
            startActivity(i);
            supportFinishAfterTransition();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
        navigationView.getMenu().findItem(R.id.nav_attendance).setChecked(true);
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
