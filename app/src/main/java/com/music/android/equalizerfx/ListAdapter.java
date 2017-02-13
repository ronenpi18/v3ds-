package com.music.android.equalizerfx;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Efe Avsar on 02/06/2015.
 */
public class ListAdapter  extends ArrayAdapter<String> {

    public ListAdapter (Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView tx = (TextView)view;
        Typeface font_small = Typeface.createFromAsset(getContext().getAssets(), "fonts/airstrea.ttf");
        tx.setTypeface(font_small);
        return view ;
    }

}
