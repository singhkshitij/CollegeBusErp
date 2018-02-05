package com.edu.aimt;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by PRAV on 05-04-2016.
 */
public class SubtitleView extends LinearLayout {

    public SubtitleView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        TypedArray a = context.obtainStyledAttributes(attributeSet,
                R.styleable.SubtitleView, 0, 0);
        String titleText = a.getString(R.styleable.SubtitleView_titleText);
        String subText = a.getString(R.styleable.SubtitleView_subText);
        a.recycle();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.subtitle_view, this, true);
        setOrientation(LinearLayout.VERTICAL);
        TextView title = (TextView) getChildAt(0);
        title.setText(titleText);
        TextView subtitle = (TextView) getChildAt(1);
        subtitle.setText(subText);
    }
}
