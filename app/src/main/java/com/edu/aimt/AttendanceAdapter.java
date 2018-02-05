package com.edu.aimt;
import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.aimt.R;
import com.edu.aimt.DataObject;

import org.w3c.dom.Text;

public class AttendanceAdapter extends RecyclerView
        .Adapter<AttendanceAdapter.ViewHolder> {

    private ArrayList<DataObject> mDataset;
    private OnItemClickListener myClickListener;
    private String colors[]={"#2196F3","#3F51B5","#9C27B0"};
    int pos=0;
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView tv,stv,present,absent;
        ImageView student;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.absent).setOnClickListener(this);
            itemView.findViewById(R.id.present).setOnClickListener(this);
            tv = (TextView) itemView.findViewById(R.id.mainContent);
            stv = (TextView) itemView.findViewById(R.id.subContent);
            present = (TextView) itemView.findViewById(R.id.present_count);
            absent = (TextView) itemView.findViewById(R.id.absent_count);
            student = (ImageView)itemView.findViewById(R.id.circleView);
        }

        @Override
        public void onClick(View v) {

            if(myClickListener!=null){
                myClickListener.onItemClick(getAdapterPosition(), v);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public AttendanceAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_card, parent, false);

        ViewHolder dataObjectHolder = new ViewHolder(view);
        RelativeLayout layout=(RelativeLayout)view.findViewById(R.id.container);
        view.findViewById(R.id.present).setTag("Button");
        layout.setBackgroundColor(Color.parseColor(colors[pos]));

        ++pos;
        if(pos>=colors.length)
            pos=0;
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(mDataset.get(position).getName());
        holder.stv.setText(mDataset.get(position).getBranch());
        holder.present.setText("Present:"+ mDataset.get(position).getPresent());
        holder.absent.setText("Absent:"+mDataset.get(position).getAbsent());
        Drawable drawable =BitmapDisplayer.buildArtist(position,holder.itemView.getContext());
        holder.student.setImageResource(0);
        holder.student.setImageBitmap(new BitmapDisplayer().convertToBitmap(drawable,72,72));
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, View v);
    }
}