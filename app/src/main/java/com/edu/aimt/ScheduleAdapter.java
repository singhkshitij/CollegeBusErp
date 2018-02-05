package com.edu.aimt;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;





public class ScheduleAdapter extends BaseAdapter {
	
	//song list and layout
	private List<HashMap<String,String>> stops;
	private Context MyContext;
	   static class ViewHolderItem {
 	      TextView tv;
 	      TextView atv;
 	  }
 
	//constructor
	public ScheduleAdapter(Context context, List<HashMap<String, String>> stop){
        this.stops=stop;
		this.MyContext=context;
	}

  
  
   
	@Override
	public int getCount() {
		return stops.size();
	}

	@Override
	public HashMap<String,String> getItem(int arg0) {
		return stops.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//map to song layout
		ViewHolderItem viewHolder;
  	  if(convertView==null){
  		  LayoutInflater albumInf=LayoutInflater.from(MyContext);
  		  convertView= albumInf.inflate(R.layout.bus_schedule, parent, false);
  		  viewHolder = new ViewHolderItem();
          // get title and artist views	
  		  viewHolder.tv = (TextView) convertView.findViewById(R.id.stop_name);
  		  viewHolder.atv = (TextView)convertView.findViewById(R.id.stop_time);
  		  convertView.setTag(viewHolder);
  	  }
  	  else{
         viewHolder = (ViewHolderItem) convertView.getTag();
        }
		//get song using position
  	     HashMap<String,String> currMap = stops.get(position);
		//get title and artist string
		viewHolder.tv.setText(currMap.get("main_text"));
		viewHolder.atv.setText(currMap.get("sub_text"));
		if(position==0){
			viewHolder.tv.setTextColor(Color.BLACK);
			viewHolder.atv.setTextColor(Color.BLACK);
			viewHolder.tv.setTextSize(24);
			viewHolder.atv.setTextSize(24);
		}
			
		if(position%2==0)
			convertView.setBackgroundColor(Color.parseColor("#eae9e9"));
		return convertView;
	}

}
