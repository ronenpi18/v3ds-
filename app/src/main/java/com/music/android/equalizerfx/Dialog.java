package com.music.android.equalizerfx;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.music.android.equalizerfx.MainActivity;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Efe Avsar on July 2015.
 */

public class Dialog extends MainActivity {
    private Context context;
    private RelativeLayout mainLayout;
    private boolean show=false;
    private android.app.Dialog progress_dialog;
    private Effects mEffects;

    public Dialog(Context context, RelativeLayout mainLayout, Effects effects) {
        this.context = context;
        this.mainLayout = mainLayout;
        this.mEffects = effects;
       // ((MainActivity)context).requestNewBanner();
    }

    public void showDialog(){
        if(!show){
            show=true;
            final android.app.Dialog dialog = new android.app.Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            //dialog.getWindow().setLayout((int) (mainLayout.getWidth() * 1 ), (int) (mainLayout.getHeight()* 1));
            //dialog.getWindow().setLayout((int) (mainLayout.getWidth() * 0.8), (int) (mainLayout.getHeight() * 0.75));
            // relativeLayout_list.setBackgroundResource(android.R.color.transparent);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialog.dismiss();
                    show = false;
                }
            });

            ListView listView = (ListView) dialog.findViewById(R.id.listView_present);
            // listView.setLayoutParams(new RelativeLayout.LayoutParams((int) (mainLayout.getWidth() * 0.75), (int) (mainLayout.getHeight() * 0.8)));

            final Present presents = new Present();
            final ListAdapter listAdapter = new com.music.android.equalizerfx.ListAdapter(context,android.R.layout.simple_list_item_1,presents.getPresentNames());
            //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,android.R.id.text1,presents.getPresentNames());
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("POSITION", position + "");
                    String[] presentValues = presents.getPresentValues(position);
                    for (int i = 1; i < presentValues.length; i++) {
                        Log.d("i ", i + "");
                        mEffects.getSeekBarByIndex(i).setProgress(new Integer(presentValues[i]));
                    }
                   // ((TextView) mainLayout.findViewById(R.id.textView4)).setText(presentValues[0] + "");
                    ((MainActivity)context).createNotification(presentValues[0] + "");

                    tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("EQ Preset")
                            .setAction(presentValues[0] + "")
                            //.setLabel(presentValues[0] + "")
                            .build());

                    dialog.dismiss();
                    show = false;
                }
            });
            dialog.show();

        }
    }

}
