package com.edu.aimt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class DefaulterAdapter extends BaseAdapter {

	//song list and layout
	private List<DataObject> stops;
	private Context MyContext;
	   static class ViewHolderItem {
 	      TextView tv;
 	      TextView atv;
		  TextView attendance;
		  ImageView student;
 	  }

	//constructor
	public DefaulterAdapter(Context context, List<DataObject> stop){
        this.stops=stop;
		this.MyContext=context;
	}




	@Override
	public int getCount() {
		return stops.size();
	}

	@Override
	public DataObject getItem(int arg0) {
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
  		  convertView= albumInf.inflate(R.layout.item_defaulters, parent, false);
  		  viewHolder = new ViewHolderItem();
          // get title and artist views
  		  viewHolder.tv = (TextView) convertView.findViewById(R.id.student_name);
  		  viewHolder.atv = (TextView)convertView.findViewById(R.id.student_branch);
		  viewHolder.attendance = (TextView) convertView.findViewById(R.id.student_attendance);
		  viewHolder.student = (ImageView) convertView.findViewById(R.id.student_photo);
  		  convertView.setTag(viewHolder);
  	  }
  	  else{
         viewHolder = (ViewHolderItem) convertView.getTag();
        }
		//get song using position
  	     DataObject currMap = stops.get(position);
		//get title and artist string
		viewHolder.tv.setText(currMap.getName());
		viewHolder.atv.setText(currMap.getBranch());
		viewHolder.attendance.setText(currMap.getPresent());
		viewHolder.student.setImageResource(0);
		Drawable drawable = BitmapDisplayer.buildArtist(position,convertView.getContext());
		viewHolder.student.setImageBitmap(new BitmapDisplayer().convertToBitmap(drawable,72,72));
		return convertView;
	}

}
