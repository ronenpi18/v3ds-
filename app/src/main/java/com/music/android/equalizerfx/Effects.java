//package com.music.android.equalizerfx;
//
//import android.content.Context;
//import android.media.AudioManager;
//import android.media.audiofx.BassBoost;
//import android.media.audiofx.Equalizer;
//import android.media.audiofx.PresetReverb;
//import android.media.audiofx.Virtualizer;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Efe Avsar on July 2015.
// */
//
//public class Effects {
//
//    private Equalizer mEQ ;
//    private BassBoost mBassBoost ;
//    private Virtualizer mVirtualizer;
//    private AudioManager mAudioManager;
//    private MainActivity mainActivity;
//
//    private SeekBar seekbar_1 ;
//    private SeekBar seekbar_2 ;
//    private SeekBar seekbar_3 ;
//    private SeekBar seekbar_4 ;
//    private SeekBar seekbar_5 ;
//    private SeekBar seekbar_vol ;
//
//
//    private CheckBox checkBox_bass;
//    private CheckBox checkBox_vir;
//
//    public Effects(Context context,Equalizer equalizer, Virtualizer virtualizer,BassBoost bassBoost,AudioManager audioManager)
//    {
//        //this.context = context;
//        this.mVirtualizer= virtualizer;
//        this.mEQ=equalizer;
//        this.mBassBoost =bassBoost;
//        this.mAudioManager = audioManager;
//        mainActivity = (MainActivity)context;
//        //init();
//
//    }
//    boolean cona = false;
//    public void init()
//    {
//        initEQ();
//        //bypassSettings();
//        ImageView refresh = (ImageView)mainActivity.findViewById(R.id.imageView_refresh);
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cona = !cona;
//
//                bypassSettings();
//
//                if(cona){
//                    restart();
//                    //Toast.makeText(this, "Unpressed", Toast.LENGTH_SHORT).show();
//                    //searchingAnimView.stopAnimations();
//                   // findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradient);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
//                   // ((TextView)findViewById(R.id.status)).setText("3D Sound is OFF");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
//                }else{
//                    bypassSettings();
////
////                    searchingAnimView.startAnimations();
////                    findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradclick);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
////                    //Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show();
////                    ((TextView)findViewById(R.id.status)).setText("3D Sound is ON");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
//                }
//
//                mainActivity.refreshRotateAnimation();
//                mainActivity.createNotification("Flat");
//                if (mainActivity.isValidRefreshCount())mainActivity.showAds();
//
//            }
//        });
//
//       // checkBox_bass = (CheckBox)mainActivity.findViewById(R.id.checkBox_bass);
////        checkBox_bass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                try
////                {
////                    if (isChecked)
////                    {
////                        mBassBoost.setStrength((short) ((short) 1000));
////                        mBassBoost.setEnabled(true);
////                        if ( mainActivity.isValidBassCount()) mainActivity.showAds();
////                    }else mBassBoost.setEnabled(false);
////                }catch(Exception e)
////                {
////                    Log.d("Bass ERROR", e.toString());
////                }
////            }
////        });
//
////        checkBox_vir = (CheckBox)mainActivity.findViewById(R.id.checkBox_virtualizer);
////        checkBox_vir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                if (isChecked)
////                {
////                    try
////                    {
////                        mVirtualizer.setStrength((short)(1000));
////                        mVirtualizer.setEnabled(true);
////                        if ( mainActivity.isValidVirCount()) mainActivity.showAds();
////
////                    }catch (Exception e)
////                    {
////                        Log.d("Virt. ERROR", e.toString());
////                    }
////                   // Log.d("Virrrrrr", "checkkkk!!!");
////                }else
////                {
////                    //mVirtualizer.setStrength((short)0);
////                    mVirtualizer.setEnabled(false);
////                }
////            }
////        });
//
//    }
//
//
//    public SeekBar getSeekbar_vol()
//    {
//        return seekbar_vol;
//    }
//    public boolean mSeekFromKeyDown=false ;
//    public void setIsSeeKFromKeyDown(boolean seeKFromKeyDown)
//    {
//        mSeekFromKeyDown=seeKFromKeyDown;
//    }
//    public boolean ismIsSeekFromKeyDown()
//    {
//        return mSeekFromKeyDown;
//    }
//    public void initEQ()
//    {
//
//        try {
//            mEQ.setEnabled(true);
//            //final ImageView bassImage = (ImageView)mainActivity.findViewById(R.id.imageView);
//            //bassImage.setAlpha(50);
//            seekbar_vol = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_volume);
//            seekbar_vol.setProgress(Math.round(100 * mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
//                    / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)));
//            seekbar_vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onStopTrackingTouch(SeekBar arg0) {
//                    //              Log.d("STOP TRACKING","TEST123123123");
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar arg0) {
//
//                }
//
//                @Override
//                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
//                    Log.d("Bassboost Progress", progress + "");
//                    try {
//                        if (!ismIsSeekFromKeyDown()) {
//                            Log.d("Volume round:", Math.round(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * progress / 100) + "");
//                            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Math.round(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * progress / 100), 0);
//                        }
//                        //ImageView bassImage = (ImageView) mainActivity.findViewById(R.id.imageView);
//                        //bassImage.setAlpha(50 + progress);
//                        setIsSeeKFromKeyDown(false);
//                    } catch (Exception e) {
//                        Log.d("Bassboost HATA:", e.getMessage());
//                    }
//                }
//            });
//            seekbar_1 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_1);
//            seekbar_1.setProgress(50);
//
//            seekbar_1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onStopTrackingTouch(SeekBar arg0) {
//                    //              Log.d("STOP TRACKING","TEST123123123");
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar arg0) {
//                    mainActivity.createNotification("");
//                    //((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
//                }
//
//                @Override
//                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
//                    Log.d("Bassboost Progress", progress + "");
//                    try {
//                        if (progress < 50) {
//                            setBandLevel(4, progress, true);
//                        } else if (progress > 50) {
//                            setBandLevel(4, progress, false);
//                        } else {
//                            setBandLevel(4, 50, false);
//                        }
//                    } catch (Exception e) {
//                        Log.d("Bassboost HATA:", e.getMessage());
//                    }
//                }
//            });
//            seekbar_2 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_2);
//            seekbar_2.setProgress(50);
//            seekbar_2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onStopTrackingTouch(SeekBar arg0) {
//                    //              Log.d("STOP TRACKING","TEST123123123");
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar arg0) {
//                    mainActivity.createNotification("");
//                   /// ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
//                }
//
//                @Override
//                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
//                    //                  Log.d("Bassboost Progress", progress + "");
//                    try {
//
//                        if (progress < 50) {
//                            setBandLevel(3, progress, true);
//                        } else if (progress > 50) {
//                            setBandLevel(3, progress, false);
//                        } else {
//                            setBandLevel(3, 50, false);
//                        }
//                    } catch (Exception e) {
//                        Log.d("seekBar_mid HATA:", e.getMessage());
//                    }
//                }
//            });
//            seekbar_3 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_3);
//            seekbar_3.setProgress(50);
//            seekbar_3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onStopTrackingTouch(SeekBar arg0) {
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar arg0) {
//                    mainActivity.createNotification("");
//                   // ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
//                }
//
//                @Override
//                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
//                    //                  Log.d("Bassboost Progress", progress + "");
//                    try {
//
//                        if (progress < 50) {
//                            setBandLevel(2, progress, true);
//                        } else if (progress > 50) {
//                            setBandLevel(2, progress, false);
//                        } else {
//                            setBandLevel(2, 50, false);
//                        }
//                    } catch (Exception e) {
//                        Log.d("seekBar_mid HATA:", e.getMessage());
//                    }
//                }
//            });
//            seekbar_4 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_4);
//            seekbar_4.setProgress(50);
//            seekbar_4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onStopTrackingTouch(SeekBar arg0) {
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar arg0) {
//                    mainActivity.createNotification("");
//                  //  ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
//                }
//
//                @Override
//                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
//                    //                  Log.d("Bassboost Progress", progress + "");
//                    try {
//                        if (progress < 50) {
//                            setBandLevel(1, progress, true);
//
//                        } else if (progress > 50) {
//                            setBandLevel(1, progress, false);
//                        } else {
//                            setBandLevel(1, 50, false);
//                        }
//                    } catch (Exception e) {
//                        Log.d("highSeekbar HATA:", e.getMessage());
//                    }
//                }
//            });
//            seekbar_5 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_5);
//
//            seekbar_5.setProgress(50);
//            seekbar_5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onStopTrackingTouch(SeekBar arg0) {
//                }
//                @Override
//                public void onStartTrackingTouch(SeekBar arg0) {
//                    mainActivity.createNotification("");
//                   // ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
//                }
//                @Override
//                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
//                    //                  Log.d("Bassboost Progress", progress + "");
//                    try {
//
//                        //Log.d("HIGH ",((50-progress)*mEQrange/100)+"");
//
//                        if (progress<50)
//                        {
//                            setBandLevel(0,progress,true);
//
//                        } else if (progress>50)
//                        {
//                            setBandLevel(0,progress,false);
//                        } else
//                        {
//                            setBandLevel(0,50,false);
//                        }
//                    } catch (Exception e) {
//                        Log.d("highSeekbar HATA:", e.getMessage());
//                    }
//                }
//            });
//        } catch (Exception e) {
//            Log.d("highSeekbar HATA:", e.getMessage()+"");
//        }
//        seekbar_1.setProgress(0);                                   //////////////45
//        seekbar_2.setProgress(0);                                   //////////////30
//        seekbar_3.setProgress(100);                                 //////////////38
//        seekbar_4.setProgress(0);                                  //////////////35
//        seekbar_5.setProgress(0);                                  //////////////20
//        seekbar_vol.setProgress(100);
////
////        seekbar_1.setEnabled(false);                                //////////////
////        seekbar_vol.setEnabled(false);                              //////////////
////        seekbar_2.setEnabled(false);                                //////////////
////        seekbar_3.setEnabled(false);                                //////////////
////        seekbar_4.setEnabled(false);                                //////////////
//////        seekbar_5.setEnabled(false);
////        seekbar_1.setVisibility(View.INVISIBLE);
////        seekbar_2.setVisibility(View.INVISIBLE);
////        seekbar_3.setVisibility(View.INVISIBLE);
////        seekbar_4.setVisibility(View.INVISIBLE);
////        seekbar_5.setVisibility(View.INVISIBLE);
////        seekbar_vol.setVisibility(View.INVISIBLE);
//    }
//
//    public void setBandLevel (int band , int level,boolean isnegative)
//    {
//        int range ;
//
//        if(Math.abs(mEQ.getBandLevelRange()[0])== Math.abs(mEQ.getBandLevelRange()[1]))
//            range= (short) Math.abs(mEQ.getBandLevelRange()[0]);
//        else if(Math.abs(mEQ.getBandLevelRange()[0]) < Math.abs(mEQ.getBandLevelRange()[1]))
//            range= (short) Math.abs(mEQ.getBandLevelRange()[0]);
//        else range= (short) Math.abs(mEQ.getBandLevelRange()[1]);
//
//        Log.d("Range",range+"");
//
//        ///if (isnegative)
//        //{
//
//        short [] s = mEQ.getBandLevelRange() ;
//        for (int i=0 ; i<s.length;i++)
//        {
//            Log.d("BandLevelRange ", i+ "--"+s[i]);
//        }
//
//        Log.d("BAND ", band + "");
//        Log.d("getNumberOfBands:", mEQ.getNumberOfBands()+ "");
//        Log.d("LEVEL ",(short)(((level-50)*range )/50)+"");
//        mEQ.setBandLevel((short)(4-band),(short)(((level-50)*range )/50));
//        /*} else
//        {
//            Log.d("LEVEL pozitif",(short)(-(level-50)*range/50)+"");
//            mEQ.setBandLevel((short)(4-band),(short)((level-50)*range/50));
//        }*/
//    }
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    public void bypassSettings()                                    //////////////
//    {                                                               //////////////
//        //////////////
////        checkBox_vir.setChecked(false);                             //////////////
////        checkBox_bass.setChecked(false);                            //////////////
////        seekbar_1.setEnabled(false);                                //////////////
////        seekbar_vol.setEnabled(false);                              //////////////
////        seekbar_2.setEnabled(false);                                //////////////
////        seekbar_3.setEnabled(false);                                //////////////
////        seekbar_4.setEnabled(false);                                //////////////
////        seekbar_5.setEnabled(false);                                //////////////
//        // seekbar_2.setVisibility(View.GONE);                       //////////////
//        seekbar_1.setProgress(5);                                   //////////////
//        seekbar_2.setProgress(0);                                   //////////////
//        seekbar_3.setProgress(100);                                 //////////////
//        seekbar_4.setProgress(70);                                  //////////////
//        seekbar_5.setProgress(100);                                 //////////////
//        //mEQ.setEnabled(false);                                    //////////////
//        //////////////
//        Log.d("Test BYPASS", "1123123123123123");                   //////////////
//    }
//    public void restart()                                    //////////////
//    {                                                               //////////////
////        //////////////
////        checkBox_vir.setChecked(false);                             //////////////
////        checkBox_bass.setChecked(false);                            //////////////
////        seekbar_1.setEnabled(false);                                //////////////
////        seekbar_vol.setEnabled(false);                              //////////////
////        seekbar_2.setEnabled(false);                                //////////////
////        seekbar_3.setEnabled(false);                                //////////////
////        seekbar_4.setEnabled(false);                                //////////////
////        seekbar_5.setEnabled(false);                                //////////////
//        // seekbar_2.setVisibility(View.GONE);                       //////////////
//        seekbar_1.setProgress(50);                                   //////////////
//        seekbar_2.setProgress(50);                                   //////////////
//        seekbar_3.setProgress(50);                                 //////////////
//        seekbar_4.setProgress(50);                                  //////////////
//        seekbar_5.setProgress(50);                                 //////////////
//        //mEQ.setEnabled(false);                                    //////////////
//        //////////////
//        Log.d("Test BYPASS", "1123123123123123");                   //////////////
//    }
//
//
//    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public SeekBar getSeekBarByIndex(int index)
//    {
//         SeekBar sk ;
//         switch (index)
//         {
//             case 1 : sk = seekbar_1;
//                 return sk;
//             case 2 : sk = seekbar_2;
//                 return sk;
//             case 3 : sk = seekbar_3;
//                 return sk;
//             case 4 : sk = seekbar_4;
//                 return sk;
//             case 5 : sk = seekbar_5;
//                 return sk;
//             default:break;
//         }
//    return null;
//    }
//
//
//
//}
//
package com.music.android.equalizerfx;

import android.content.Context;
import android.media.AudioManager;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronen Pinhasov on July 2015.
 */

public class Effects {

    private Equalizer mEQ ;
    private BassBoost mBassBoost ;
    private Virtualizer mVirtualizer;
    private AudioManager mAudioManager;
    private MainActivity mainActivity;

    private SeekBar seekbar_1 ;
    private SeekBar seekbar_2 ;
    private SeekBar seekbar_3 ;
    private SeekBar seekbar_4 ;
    private SeekBar seekbar_5 ;
    private SeekBar seekbar_vol ;


    public Effects(Context context,Equalizer equalizer, Virtualizer virtualizer,BassBoost bassBoost,AudioManager audioManager)
    {
        //this.context = context;
        this.mVirtualizer= virtualizer;
        this.mEQ=equalizer;
        this.mBassBoost =bassBoost;
        this.mAudioManager = audioManager;
        mainActivity = (MainActivity)context;
        //init();
    }
    public void init()
    {
        mEQ.setEnabled(true);
        initEQ();
//
//
//        seekbar_vol.setMax(100);
//        seekbar_vol.setProgress(40);
//

        ImageView refresh = (ImageView)mainActivity.findViewById(R.id.imageView_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        bypassSettings();
       // ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Flat");
        mainActivity.refreshRotateAnimation();
        mainActivity.createNotification("3D Sound");
                //if (mainActivity.isValidRefreshCount())mainActivity.showAds();

            }
        });

        //checkBox_bass = (CheckBox)mainActivity.findViewById(R.id.checkBox_bass);
//        checkBox_bass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                try
//                {
//                    if (isChecked)
//                    {
//                        mBassBoost.setStrength((short) ((short) 1000));
//                        mBassBoost.setEnabled(true);
//                        if ( mainActivity.isValidBassCount()) mainActivity.showAds();
//                    }else mBassBoost.setEnabled(false);
//                }catch(Exception e)
//                {
//                    Log.d("Bass ERROR", e.toString());
//                }
//            }
//        });
//
//       // checkBox_vir = (CheckBox)mainActivity.findViewById(R.id.checkBox_virtualizer);
//        checkBox_vir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                {
//                    try
//                    {
//                        mVirtualizer.setStrength((short)(1000));
//                        mVirtualizer.setEnabled(true);
//                        if ( mainActivity.isValidVirCount()) mainActivity.showAds();
//
//                    }catch (Exception e)
//                    {
//                        Log.d("Virt. ERROR", e.toString());
//                    }
//                    // Log.d("Virrrrrr", "checkkkk!!!");
//                }else
//                {
//                    //mVirtualizer.setStrength((short)0);
//                    mVirtualizer.setEnabled(false);
//                }
//            }
//        });


        seekbar_1.setEnabled(false);                                //////////////
        seekbar_vol.setEnabled(false);                              //////////////
        seekbar_2.setEnabled(false);                                //////////////
        seekbar_3.setEnabled(false);                                //////////////
        seekbar_4.setEnabled(false);                                //////////////
        seekbar_5.setEnabled(false);                                //////////////
        seekbar_2.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_1.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_3.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_4.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_5.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_vol.setVisibility(View.INVISIBLE);                       //////////////

        seekbar_vol.setMax(100);
        // seekbar_vol.setProgress(40);
        seekbar_1.setProgress(90);
        seekbar_2.setProgress(50);
        seekbar_3.setProgress(100);
        seekbar_4.setProgress(45);
        seekbar_5.setProgress(25);
        seekbar_vol.setProgress(90);
    }


    public SeekBar getSeekbar_vol()
    {
        return seekbar_vol;
    }
    public boolean mSeekFromKeyDown=false ;
    public void setIsSeeKFromKeyDown(boolean seeKFromKeyDown)
    {
        mSeekFromKeyDown=seeKFromKeyDown;
    }
    public boolean ismIsSeekFromKeyDown()
    {
        return mSeekFromKeyDown;
    }
    public void initEQ()
    {
        try {
            mEQ.setEnabled(true);
            //final ImageView bassImage = (ImageView)mainActivity.findViewById(R.id.imageView);
            //bassImage.setAlpha(50);
            seekbar_vol = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_volume);
            seekbar_vol.setProgress(50);
            seekbar_vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                    //              Log.d("STOP TRACKING","TEST123123123");

                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {

                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    Log.d("Bassboost Progress", progress + "");
                    try {
                        if (!ismIsSeekFromKeyDown()) {
                            Log.d("Volume round:", Math.round(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * progress / 100) + "");
                            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Math.round(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * progress / 100), 0);
                        }
                        //ImageView bassImage = (ImageView) mainActivity.findViewById(R.id.imageView);
                        //bassImage.setAlpha(50 + progress);
                        setIsSeeKFromKeyDown(false);
                    } catch (Exception e) {
                        Log.d("Bassboost HATA:", e.getMessage());
                    }
                }
            });
            seekbar_1 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_1);
            seekbar_1.setProgress(100);
            seekbar_1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                    //              Log.d("STOP TRACKING","TEST123123123");

                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                    mainActivity.createNotification("");
                    ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    Log.d("Bassboost Progress", progress + "");
                    try {
                        if (progress < 50) {
                            setBandLevel(4, progress, true);
                        } else if (progress > 50) {
                            setBandLevel(4, progress, false);
                        } else {
                            setBandLevel(4, 50, false);
                        }
                    } catch (Exception e) {
                        Log.d("Bassboost HATA:", e.getMessage());
                    }
                }
            });
            seekbar_2 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_2);
            seekbar_2.setProgress(0);
            seekbar_2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                    //              Log.d("STOP TRACKING","TEST123123123");
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                    mainActivity.createNotification("");
                   // ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    //                  Log.d("Bassboost Progress", progress + "");
                    try {

                        if (progress < 50) {
                            setBandLevel(3, progress, true);
                        } else if (progress > 50) {
                            setBandLevel(3, progress, false);
                        } else {
                            setBandLevel(3, 50, false);
                        }
                    } catch (Exception e) {
                        Log.d("seekBar_mid HATA:", e.getMessage());
                    }
                }
            });
            seekbar_3 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_3);
            seekbar_3.setProgress(5);
            seekbar_3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                    mainActivity.createNotification("");
                 //   ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    //                  Log.d("Bassboost Progress", progress + "");
                    try {

                        if (progress < 50) {
                            setBandLevel(2, progress, true);
                        } else if (progress > 50) {
                            setBandLevel(2, progress, false);
                        } else {
                            setBandLevel(2, 50, false);
                        }
                    } catch (Exception e) {
                        Log.d("seekBar_mid HATA:", e.getMessage());
                    }
                }
            });
            seekbar_4 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_4);
            seekbar_4.setProgress(50);
            seekbar_4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                    mainActivity.createNotification("");
                 //   ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    //                  Log.d("Bassboost Progress", progress + "");
                    try {
                        if (progress < 50) {
                            setBandLevel(1, progress, true);

                        } else if (progress > 50) {
                            setBandLevel(1, progress, false);
                        } else {
                            setBandLevel(1, 50, false);
                        }
                    } catch (Exception e) {
                        Log.d("highSeekbar HATA:", e.getMessage());
                    }
                }
            });
            seekbar_5 = (SeekBar) mainActivity.findViewById(R.id.seekbar_vertical_5);
            seekbar_5.setProgress(0);
            seekbar_5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                    mainActivity.createNotification("");
                    //((TextView)mainActivity.findViewById(R.id.textView4)).setText("Custom");
                }
                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    //                  Log.d("Bassboost Progress", progress + "");
                    try {

                        //Log.d("HIGH ",((50-progress)*mEQrange/100)+"");

                        if (progress<50)
                        {
                            setBandLevel(0,progress,true);

                        } else if (progress>50)
                        {
                            setBandLevel(0,progress,false);
                        } else
                        {
                            setBandLevel(0,50,false);
                        }
                    } catch (Exception e) {
                        Log.d("highSeekbar HATA:", e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Log.d("highSeekbar HATA:", e.getMessage()+"");
        }


        seekbar_vol.setMax(100);
        seekbar_vol.setProgress(40);
    }

    public void setBandLevel (int band , int level,boolean isnegative)
    {
        int range ;

        if(Math.abs(mEQ.getBandLevelRange()[0])== Math.abs(mEQ.getBandLevelRange()[1]))
            range= (short) Math.abs(mEQ.getBandLevelRange()[0]);
        else if(Math.abs(mEQ.getBandLevelRange()[0]) < Math.abs(mEQ.getBandLevelRange()[1]))
            range= (short) Math.abs(mEQ.getBandLevelRange()[0]);
        else range= (short) Math.abs(mEQ.getBandLevelRange()[1]);

        Log.d("Range",range+"");

        ///if (isnegative)
        //{

        short [] s = mEQ.getBandLevelRange() ;
        for (int i=0 ; i<s.length;i++)
        {
            Log.d("BandLevelRange ", i+ "--"+s[i]);
        }

        Log.d("BAND ", band + "");
        Log.d("getNumberOfBands:", mEQ.getNumberOfBands()+ "");
        Log.d("LEVEL ",(short)(((level-50)*range )/50)+"");
        mEQ.setBandLevel((short)(4-band),(short)(((level-50)*range )/50));
        /*} else
        {
            Log.d("LEVEL pozitif",(short)(-(level-50)*range/50)+"");
            mEQ.setBandLevel((short)(4-band),(short)((level-50)*range/50));
        }*/



        seekbar_1.setEnabled(false);                                //////////////
        seekbar_vol.setEnabled(false);                              //////////////
        seekbar_2.setEnabled(false);                                //////////////
        seekbar_3.setEnabled(false);                                //////////////
        seekbar_4.setEnabled(false);                                //////////////
        seekbar_5.setEnabled(false);                                //////////////
        seekbar_2.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_1.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_3.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_4.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_5.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_vol.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_2.setVisibility(View.GONE);                       //////////////
        seekbar_1.setVisibility(View.GONE);                       //////////////
        seekbar_3.setVisibility(View.GONE);                       //////////////
        seekbar_4.setVisibility(View.GONE);                       //////////////
        seekbar_5.setVisibility(View.GONE);                       //////////////
        seekbar_vol.setVisibility(View.GONE);                       //////////////

    }


    public void bypassSettings()
    {

        //////////////
        seekbar_1.setEnabled(false);                                //////////////
        seekbar_vol.setEnabled(false);                              //////////////
        seekbar_2.setEnabled(false);                                //////////////
        seekbar_3.setEnabled(false);                                //////////////
        seekbar_4.setEnabled(false);                                //////////////
        seekbar_5.setEnabled(false);                                //////////////
        seekbar_2.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_1.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_3.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_4.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_5.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_vol.setVisibility(View.INVISIBLE);                       //////////////

        seekbar_vol.setMax(100);
       // seekbar_vol.setProgress(40);
        seekbar_1.setProgress(90);
        seekbar_2.setProgress(50);
        seekbar_3.setProgress(100);
        seekbar_4.setProgress(45);
        seekbar_5.setProgress(25);
        seekbar_vol.setProgress(90);
        //mEQ.setEnabled(false);
        Log.d("Test BYPASS", "1123123123123123");
    }

    public SeekBar getSeekBarByIndex(int index)
    {
        SeekBar sk ;
        switch (index)
        {
            case 1 : sk = seekbar_1;
                return sk;
            case 2 : sk = seekbar_2;
                return sk;
            case 3 : sk = seekbar_3;
                return sk;
            case 4 : sk = seekbar_4;
                return sk;
            case 5 : sk = seekbar_5;
                return sk;
            default:break;
        }
        return null;
    }


    public void disinit()
    {
        mEQ.setEnabled(true);
        initEQ();
//
//
//        seekbar_vol.setMax(100);
//        seekbar_vol.setProgress(40);
//

        ImageView refresh = (ImageView)mainActivity.findViewById(R.id.imageView_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bypassSettings();
                // ((TextView)mainActivity.findViewById(R.id.textView4)).setText("Flat");
                mainActivity.refreshRotateAnimation();
                mainActivity.createNotification("3D Sound");
                //if (mainActivity.isValidRefreshCount())mainActivity.showAds();

            }
        });

        seekbar_1.setEnabled(false);                                //////////////
        seekbar_vol.setEnabled(false);                              //////////////
        seekbar_2.setEnabled(false);                                //////////////
        seekbar_3.setEnabled(false);                                //////////////
        seekbar_4.setEnabled(false);                                //////////////
        seekbar_5.setEnabled(false);                                //////////////
        seekbar_2.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_1.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_3.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_4.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_5.setVisibility(View.INVISIBLE);                       //////////////
        seekbar_vol.setVisibility(View.INVISIBLE);                       //////////////

        seekbar_vol.setMax(100);
        // seekbar_vol.setProgress(40);
        seekbar_1.setProgress(50);
        seekbar_2.setProgress(50);
        seekbar_3.setProgress(50);
        seekbar_4.setProgress(50);
        seekbar_5.setProgress(50);
        seekbar_vol.setProgress(90);
    }
}

