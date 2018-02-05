package com.edu.aimt;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BunkAdapter extends RecyclerView
        .Adapter<BunkAdapter.ViewHolder> {

    private ArrayList<DataObject> mDataset;
    private OnItemClickListener myClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView imageView;
        TextView name,branch;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.student_photo);
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.mainContent);
            branch = (TextView) itemView.findViewById(R.id.subContent);
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

    public BunkAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bunk_item, parent, false);
        ViewHolder dataObjectHolder = new ViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drawable drawable = BitmapDisplayer.buildArtist(position,holder.itemView.getContext());
        holder.imageView.setImageResource(0);
        holder.imageView.setImageDrawable(drawable);
        holder.name.setText(mDataset.get(position).getName());
        holder.branch.setText(mDataset.get(position).getBranch());
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