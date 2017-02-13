package com.music.android.equalizerfx;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Efe Avsar on 01/06/2015.
 */
public class Present {
//    private final HashMap mPresents = new HashMap(15) ;

    private final List mPresents = new ArrayList<String[]>();
    public Present () {
    //    mPresents.put("Classic",new String[]{"75","60","37","70","81"});
   //     mPresents.put("Pop",new String[]{"75","60","37","70","81"});
        mPresents.add(new String[]{"Classic","100","0","0","0","0"});
        mPresents.add(new String[]{"Pop","45","55","50","45","40"});
        mPresents.add(new String[]{"Jazz","65","55","45","55","60"});
        mPresents.add(new String[]{"Rock","60","40","40","50","60"});
        mPresents.add(new String[]{"Metal","75","35","40","65","75"});
        mPresents.add(new String[]{"Ska","45","40","50","60","65"});
        mPresents.add(new String[]{"Reggae","50","45","60","55","50"});
        mPresents.add(new String[]{"Club","50","60","75","55","50"});
        mPresents.add(new String[]{"Dance","75","60","45","40","50"});
        mPresents.add(new String[]{"Techno","65","35","55","60","60"});
        mPresents.add(new String[]{"Party","65","35","60","50","75"});
        mPresents.add(new String[]{"Soft","60","55","47","60","65"});
        mPresents.add(new String[]{"Full Bass","80","35","50","40","30"});
        //mPresents.add(new String[]{"Full Bass & Treble","85","75","35","60","75"});
        mPresents.add(new String[]{"Full Treble", "30","45","50","60","90"});
        mPresents.add(new String[]{"Large Hall","75", "60", "40", "45", "50"});
        mPresents.add(new String[]{"Live","40","50","60","65","55"});
        mPresents.add(new String[]{"Vocal","55","45","75","60","45"});
        mPresents.add(new String[]{"Drum","80","65","30","30","90"});
    }
    public List getPresents()
    {
        return mPresents;
    }
    public String[] getPresentNames()
    {
        String[] names = new String[mPresents.size()];
        int i =0 ;
        for (Object s : mPresents)
        {
             Log.d("present0000000", ((String[]) s)[0] + "");
             Log.d("present1111111", ((String[]) s)[1] + "");
             names[i] = ((String[])s)[0];
             i++;
        }
        return names ;
    }
    public String[] getPresentValues(int position)
    {
        String[] values = new String[6];
        int i =0 ;
        for (Object s : mPresents)
        {
            if (i==position)
            {
                Log.d("getPresentValues 0", ((String[]) s)[0] + "");
                Log.d("getPresentValues 5", ((String[]) s)[5] + "");
                return (String[])s ;
            }
            i++;
        }
        return values ;
    }


}
