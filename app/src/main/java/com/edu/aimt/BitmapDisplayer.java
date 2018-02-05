package com.edu.aimt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;



public class BitmapDisplayer{
    private static String colors[]={"#009688","#9C27B0","#673AB7","#E91E63","#2196F3","#00BCD4"};

    public static Drawable buildArtist(long id,Context mContext){
        String val=String.valueOf(id);
        int n=(int)Math.ceil(Integer.parseInt(String.valueOf(val.charAt(val.length()-1)))/2);
        Drawable drawable=ContextCompat.getDrawable(mContext, R.drawable.default_artist).mutate();
        drawable.setColorFilter(Color.parseColor(colors[n]), PorterDuff.Mode.SCREEN);
        return drawable;
    }
    public static int getColor(long id){
        String val=String.valueOf(id);
        int n=(int)Math.ceil(Integer.parseInt(String.valueOf(val.charAt(val.length()-1)))/2);
        return Color.parseColor(colors[n]);
    }
    public static void sendEmail(Context mContext){
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"aimt_grieviance@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Troubles regarding the bus");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        mContext.startActivity(Intent.createChooser(emailIntent, "Email To"));
    }
    public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);
        return mutableBitmap;
    }
}