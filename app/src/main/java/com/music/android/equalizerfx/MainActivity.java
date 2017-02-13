package com.music.android.equalizerfx;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.music.android.util.IabHelper;
import com.music.android.util.IabResult;
import com.music.android.util.Inventory;
import com.music.android.util.Purchase;
import com.sakuramomoko.searchinganimview.SearchingAnimView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import es.claucookie.miniequalizerlibrary.EqualizerView;

import static com.music.android.equalizerfx.R.id.alertTitle;
import static com.music.android.equalizerfx.R.id.textView;


/**
 * Recreated by Ronen Pinhasov on August 2016.
 */
public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private static InterstitialAd interstitial;
    private static final Equalizer mEQ = new Equalizer(0, 0);
    private static final BassBoost mBass = new BassBoost(0, 0);
    private static final Virtualizer mVirtualizer = new Virtualizer(0, 0);
    private Effects mEffects;
    private Dialog mPresentDialog;
    SeekBar seekbar;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    //PlusOneButton mPlusOneButton;
    TextView textView1;
    private int mId;
    private boolean isExit;
    private boolean isSmallScreen = false;
    private static final String TAG = "MainActivity";

    // Item name for premium status
    private static final String SKU_PREMIUM = "com.music.android.equalizerfx.subs";
    // Flag set to true when we have premium status

    // Advertising instance
    // private AdView mAdView;
    // In-app Billing helper
    private IabHelper mAbHelper;
    // Advertising placement layout


    // Item name for premium status
    // Flag set to true when we have premium status
    private boolean mIsPremium = false;

    // Advertising instance
    // private AdView mAdView;
    // In-app Billing helper
    // Advertising placement layout
    private RelativeLayout mAdLayout;
    MediaPlayer mediaPlayer;
    SharedPreferences pref; //= getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
    SharedPreferences.Editor editor;// = pref.edit();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        mediaPlayer = new MediaPlayer();
        // EqualizerView equalizer = (EqualizerView) findViewById(R.id.equalizer_view);
        //equalizer.animateBars(); // Whenever you want to tart the animation
        if (isSmallScreen()) {
            isSmallScreen = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        setContentView(R.layout.activity_main);
        initCustomActionBar();
        initViews();
        EqualizerView equalizer = (EqualizerView) findViewById(R.id.equalizer_view);
        equalizer.animateBars(); // Whenever you want to tart the animation
        // equalizer.stopBars(); // When you want equalizer stops animating
        //  initGetPro();
//        initShare();
        mEffects = new Effects(this, mEQ, mVirtualizer, mBass, audioManager);
        mEffects.disinit();
        initInterStitial();
        initPresentDialog();
        // initAnalytics();
        //createNotification("Flat");
        writeFileIfDoesntExist("isMember");
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmk24z9l+QZFeT+NgOCX6eK7qeKMXDrf65toBNhmkAPpxkQdyEF70LACLoF6a+SB+HlRPHhACNXn6VyavPMGO3NJwdb1D5VqPe0tQh4Jm6krbDbLkEhIyz7pS3X2rxk71aeR7ejZvNQHIe/fya49TtBwqsJCEQKi6CoxOcXfGglCZ679+Fv3bimpQZrwxsHaB7QgS78BCko2yAAnxn5aec1NCI0XuJm4QEGn0iC6KfjIJSaPXJPO/rXE/PnmR8N4Px5qfQu4cNN37wfNY5lV0sxgLvHPMYkTGAtONH9xQu328sKkqgKKHYeIsNoyTvtdGJ9ExC6/fJB6/OhkgeHbiJwIDAQAB";

        textView1 = (TextView) findViewById(R.id.textView4);
        //Create in-app billing helper
        mAbHelper = new IabHelper(this, base64EncodedPublicKey);
        // and start setup. If setup is successfull, query inventory we already own
        try {
            mAbHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) throws IabHelper.IabAsyncInProgressException {

                    if (!result.isSuccess()) {
                        return;
                    }

                    mAbHelper.queryInventoryAsync(mGotInventoryListener);
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }


        SpannableString content = new SpannableString("Enroll NOW!");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView1.setText(content);

        ImageView img = (ImageView) findViewById(R.id.fb);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/Sound.knowledge.il/"));
                startActivity(intent);

            }
        });


        if (pref.getBoolean("activated", Boolean.parseBoolean(null))) {
            // getting boolean
            final SearchingAnimView searchingAnimView = (SearchingAnimView) findViewById(R.id.searchinganimview);
            searchingAnimView.startAnimations();
            mEffects.init();
            ((TextView) findViewById(R.id.status)).setText("3D Sound is ON");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradclick);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            editor.clear();
            condactor = !condactor;
            AudioManager manager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mEffects.init();
                }
            });



        }
        if (!(pref.getBoolean("activated", Boolean.parseBoolean(null)))) {
            // getting boolean
            final SearchingAnimView searchingAnimView = (SearchingAnimView) findViewById(R.id.searchinganimview);
            searchingAnimView.stopAnimations();
            mEffects.disinit();
            ((TextView) findViewById(R.id.status)).setText("3D Sound is OFF");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradient);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            editor.clear();
            condactor = !condactor;
        }


    }


    public void onBtUpgradeClick(View view) throws IabHelper.IabAsyncInProgressException {
        mAbHelper.launchPurchaseFlow(this, SKU_PREMIUM, 0,
                mPurchaseFinishedListener, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mAbHelper == null)
            return;

        // Pass on the activity result to the helper for handling
        if (!mAbHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
        }

        //  findViewById(R.id.btUpgrade).setVisibility(View.INVISIBLE); //TODO check if
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void writeFileIfDoesntExist(String FILENAME) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        if (!fileExistance(FILENAME)) {
            try {
                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                fos.write(("n%" + String.valueOf(String.valueOf(cal.get(Calendar.DATE)) + "%" + String.valueOf(cal.get(Calendar.MONTH) + 1) + "%" + String.valueOf(cal.get(Calendar.YEAR)))).getBytes());
                fos.close();
            } catch (Exception e) {
            }
        } else {
            String data = getStringOfFile(FILENAME);
            String sourceString = "Your Trial will expire in: " + "<b>" + String.valueOf(numberOfDays(data)) + " days" + "</b> ";
            ((TextView) findViewById(R.id.textView2)).setText(Html.fromHtml(sourceString));
            //((TextView)findViewById(R.id.textView2)).setText("Your Trial will expire in: "+String.valueOf(numberOfDays(data))+" days");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES

            //  try{
            if (data.substring(0, 1).equals("n") && !underSevenDays(data)) {
                show_payment_dialog();
                //  afterPayView();
                //   Intent i = new Intent(this, TrialEnded.class);
                //  startActivity(i);
            }
            //    }catch(Exception e){
            // findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.trialendedbg);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            //   findViewById(R.id.searchinganimview).setBackgroundResource(R.drawable.ic_play_ended);
            //   findViewById(R.id.tv_searching_state).setBackgroundResource(R.drawable.ic_play_ended);
            //    }
            //show_payment_dialog();//TODO: PROGRAM SOME KIND OF MESSAGE LIKE "PAY OR EXIT"
        }
        // ((TextView)findViewById(R.id.textView)).setText(String.valueOf(String.valueOf(cal.get(Calendar.DATE))+"%"+String.valueOf(cal.get(Calendar.MONTH)+1)+"%"+String.valueOf(cal.get(Calendar.YEAR))));//SHOW THE DATE OF TODAY DDMMYYYY SEPERATED BY '%'
    }
/////////////////////////////////////////////////////////////////////////

    boolean underMonth(String data) {
        return numberOfDays(data) > 0;
    }


    String getStringOfFileMonth(String FILENAME) {
        if (fileExistance(FILENAME)) {
            try {
                FileInputStream fis = openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    public boolean fileExistanceMonth(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    int numberOfDaysMonth(String data) {
        Calendar cal = GregorianCalendar.getInstance();
        for (int i = 0; i > -31; i--) {
            cal.add(Calendar.DAY_OF_YEAR, i);
            boolean day = data.split("\\%")[1].equals(String.valueOf(cal.get(Calendar.DATE))),
                    month = data.split("\\%")[2].equals(String.valueOf(cal.get(Calendar.MONTH) + 1)),
                    year = data.split("\\%")[3].equals(String.valueOf(cal.get(Calendar.YEAR)));
            if (day && month && year)
                return 31 + i;
            cal.setTime(new Date());
        }
        return -1;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void writeFileIfDoesntExistMonth(String FILENAME) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        if (!fileExistanceMonth(FILENAME)) {
            try {
                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                fos.write(("n%" + String.valueOf(String.valueOf(cal.get(Calendar.DATE)) + "%" + String.valueOf(cal.get(Calendar.MONTH) + 1) + "%" + String.valueOf(cal.get(Calendar.YEAR)))).getBytes());
                fos.close();
            } catch (Exception e) {
            }
        } else {
            String data = getStringOfFileMonth(FILENAME);
            ((TextView) findViewById(R.id.textView2)).setText("You're Membership will expire in: " + String.valueOf(numberOfDaysMonth(data)) + " days");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES

            //  try{
            if (data.substring(0, 1).equals("n") && !underMonth(data)) {
                show_payment_dialog();
                //  afterPayView();
                //   Intent i = new Intent(this, TrialEnded.class);
                //  startActivity(i);
            }
            //    }catch(Exception e){
            // findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.trialendedbg);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            //   findViewById(R.id.searchinganimview).setBackgroundResource(R.drawable.ic_play_ended);
            //   findViewById(R.id.tv_searching_state).setBackgroundResource(R.drawable.ic_play_ended);
            //    }
            //show_payment_dialog();//TODO: PROGRAM SOME KIND OF MESSAGE LIKE "PAY OR EXIT"
        }
        // ((TextView)findViewById(R.id.textView)).setText(String.valueOf(String.valueOf(cal.get(Calendar.DATE))+"%"+String.valueOf(cal.get(Calendar.MONTH)+1)+"%"+String.valueOf(cal.get(Calendar.YEAR))));//SHOW THE DATE OF TODAY DDMMYYYY SEPERATED BY '%'
    }

    private void become_member_month() {
        try {
            FileOutputStream fos = openFileOutput("isMon", Context.MODE_PRIVATE);
            fos.write("y".getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    private void show_payment_dialog() {
        ((TextView) findViewById(R.id.textView2)).setText("Your Trial Has been Expired\n Purchase 3D Sound for $3 per month!");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
        findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.trialendedbg);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
        findViewById(R.id.searchinganimview).setVisibility(View.INVISIBLE);
        findViewById(R.id.searchinganimview).setVisibility(View.GONE);
        findViewById(R.id.tv_searching_state).setVisibility(View.INVISIBLE);
        findViewById(R.id.tv_searching_state).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.textView4)).setText("Purchase 3D Sound for $3 per month!");
        findViewById(R.id.frameLayout).setBackgroundResource(R.drawable.ic_play_ended);
        findViewById(R.id.frameLayout).setClickable(false);
        EqualizerView equalizer = (EqualizerView) findViewById(R.id.equalizer_view);
        equalizer.stopBars();
        findViewById(R.id.tv_searching_state).setClickable(false);
        //findViewById(R.id.imageView).setBackgroundResource(R.drawable.gradsucc);
        findViewById(R.id.searchinganimview).setClickable(false);
        //  findViewById(R.id.tv_searching_state).setVisibility(View.INVISIBLE);


        //   ((TextView)findViewById(R.id.textView2)).setText("Your trial was expired.. click to enroll now!");
        // Intent i = new Intent(this, TrialEnded.class);
        //startActivity(i);
//        Toast.makeText(MainActivity.this, "expired", Toast.LENGTH_SHORT).show();
        // onDestroy();
    }

    private void afterPayView() {
        ((TextView) findViewById(R.id.textView2)).setText("Thank You For Purchasing!");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
        findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradsucc);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
        findViewById(R.id.searchinganimview).setVisibility(View.INVISIBLE);
        findViewById(R.id.searchinganimview).setVisibility(View.GONE);
        findViewById(R.id.tv_searching_state).setVisibility(View.INVISIBLE);
        findViewById(R.id.tv_searching_state).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.textView4)).setText("Tap to 3D Sound");
        findViewById(R.id.frameLayout).setBackgroundResource(R.drawable.ic_play_suc);
        findViewById(R.id.frameLayout).setClickable(false);
        findViewById(R.id.tv_searching_state).setClickable(false);
        findViewById(R.id.imageView).setBackgroundResource(R.drawable.gradsucc);
        findViewById(R.id.searchinganimview).setClickable(false);
        deleteFile("isMember");

    }


    private void become_member() {
        try {
            FileOutputStream fos = openFileOutput("isMember", Context.MODE_PRIVATE);
            fos.write("y".getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    boolean underSevenDays(String data) {
        return numberOfDays(data) > 0;
    }


    String getStringOfFile(String FILENAME) {
        if (fileExistance(FILENAME)) {
            try {
                FileInputStream fis = openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public boolean deleteFile(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        file.delete();
        return true;
    }

    int numberOfDays(String data) {
        Calendar cal = GregorianCalendar.getInstance();
        for (int i = 0; i > -8; i--) {
            cal.add(Calendar.DAY_OF_YEAR, i);
            boolean day = data.split("\\%")[1].equals(String.valueOf(cal.get(Calendar.DATE))),
                    month = data.split("\\%")[2].equals(String.valueOf(cal.get(Calendar.MONTH) + 1)),
                    year = data.split("\\%")[3].equals(String.valueOf(cal.get(Calendar.YEAR)));
            if (day && month && year)
                return 7 + i;
            cal.setTime(new Date());
        }
        return -1;
    }


    /**
     * Listener that is called when we finish purchase flow.
     */
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            if (result.isFailure()) {
                return;
            }

            // Purchase was successfull, set premium flag and update interface
            if (purchase.getSku().equals(SKU_PREMIUM)) {
                mIsPremium = true;
                writeFileIfDoesntExistMonth("isMon");
                afterPayView();
                become_member_month();
            }
        }
    };

    /**
     * Listener that's called when we finish querying the items and
     * subscriptions we own
     */
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            // Have we been disposed of in the meantime? If so, quit.
            if (mAbHelper == null)
                return;

            // Is it a failure?
            if (result.isFailure()) {
                return;
            }

            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            mIsPremium = premiumPurchase != null;
            updateInterface();
        }
    };

    /**
     * Updates interface
     */
    private void updateInterface() {

        //Button btUpgrade = (Button) findViewById(R.id.btUpgrade);
        if (mIsPremium) {
            // btUpgrade.setText(R.string.you_have_premium_status);
            // btUpgrade.setEnabled(false);
            displayAd(false);
            deleteFile("isMember");
        } else {
            //  btUpgrade.setEnabled(true);
            displayAd(true);
        }
    }

    /**
     * Displays or hides the advertising
     *
     * @param state
     */
    private void displayAd(boolean state) {
    }


    public void initInterStitial() {
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
        requestNewInterstitial();
//        interstitial.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                //super.onAdClosed();
//                if (isExit) {
//                    finishActivity();
//                } else requestNewInterstitial();
//            }
//        });
    }

    private int mAdCount = 1;

    public boolean isValidAdCount() {
        if ((mAdCount % 2) == 0) {
            mAdCount = 1;
            return true;
        } else {
            mAdCount++;
            return false;
        }
    }

    private int mAdpPresetCount = 1;

    public boolean isAdpPresetCount() {
        if ((mAdpPresetCount % 5) == 0) {
            mAdpPresetCount = 1;
            return true;
        } else {
            mAdpPresetCount++;
            return false;
        }
    }

    private int mAdpBassCount = 1;

    public boolean isValidBassCount() {
        if ((mAdpBassCount % 6) == 0) {
            mAdpBassCount = 1;
            return true;
        } else {
            mAdpBassCount++;
            return false;
        }
    }

    private int mAdRefreshCount = 1;

    public boolean isValidRefreshCount() {
        if ((mAdRefreshCount % 4) == 0) {
            mAdRefreshCount = 1;
            return true;
        } else {
            mAdRefreshCount++;
            return false;
        }
    }

    private int mAdpVirCount = 1;

    public boolean isValidVirCount() {
        if ((mAdpVirCount % 6) == 0) {
            mAdpVirCount = 1;
            return true;
        } else {
            mAdpVirCount++;
            return false;
        }
    }

    public void showAds() {
        try {
            //requestNewInterstitial();
            if (interstitial.isLoaded()) {
                interstitial.show();
            } else {
                if (isExit) {
                    finishActivity();
                    //android.os.Process.killProcess(android.os.Process.myPid());
                }
                requestNewInterstitial();
            }
        } catch (Exception e) {
            Log.d("ERROR showAds", e.getMessage() + "");
        }
    }

    public void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("0A3F2A664AA0AD56D388AF1DD436A4CC")
                .build();
        interstitial.loadAd(adRequest);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        if (isSmallScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onConfigurationChanged(newConfig);
    }

    public boolean isSmallScreen() {
        return (getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                <= Configuration.SCREENLAYOUT_SIZE_NORMAL;
    }

    private void initCustomActionBar() {
        //if (Build.VERSION.SDK_INT>=11)
        ActionBar ab = getSupportActionBar();
        //ab.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setDisplayShowHomeEnabled(false);
        ab.hide();
        ab.setCustomView(R.layout.action_bar);
    }

    @Override
    public void onDestroy() {
        //  clearNotification();
        super.onDestroy();
        if (mAbHelper != null)
            try {
                mAbHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        mAbHelper = null;
        mEffects = new Effects(this, mEQ, mVirtualizer, mBass, audioManager);
        // mEffects.init();

        checkIfOnGoing();

    }

    public boolean checkIfOnGoing() {
        final SearchingAnimView searchingAnimView = (SearchingAnimView) findViewById(R.id.searchinganimview);

        if (((TextView) findViewById(R.id.status)).getText() == "3D Sound is ON") {
            searchingAnimView.startAnimations();
            mEffects.init();
            findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradclick);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            return true;

        }
        if (((TextView) findViewById(R.id.status)).getText() == "3D Sound is OFF") {
            searchingAnimView.stopAnimations();
            mEffects.disinit();
            createNotification("3D Sound is OFF");
            findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradient);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES

            return false;
        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkIfOnGoing();
    }

    public void clearNotification() {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancel(mId);
    }

    private boolean isGooglePlayAvailable() {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {
            Log.d("xxPlayservices", "exists");
            return true;
        } else {
            Log.d("xxPlayervices", "not exists");
            return false;
        }
    }

    public void createNotification(String desc) {
        Intent intent = new Intent(this, MainActivity.class);
        //PendingIntent.FLAG_CANCEL_CURRENT will bring the app back up again
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, PendingIntent.FLAG_CANCEL_CURRENT, intent, 0);
        /*NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notif)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(desc)
                        .setContentIntent(pIntent)
                        .setOngoing(true);*/

        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v7.app.NotificationCompat.Builder(this)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_suc))
                        .setSmallIcon(R.drawable.equ_mini)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(desc)
                        .setContentIntent(pIntent)
                        .setOngoing(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }

    public void initViews() {
        Typeface font_small = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/airstrea.ttf");
        Typeface font_big = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/neonthick.ttf");

        ((TextView) findViewById(textView)).setTypeface(font_small);
        // ((TextView) findViewById(R.id.textView3)).setTypeface(font_small);
        // ((TextView) findViewById(R.id.textView4)).setTypeface(font_big);
        ((TextView) findViewById(R.id.textView5)).setTypeface(font_small);
        ((TextView) findViewById(R.id.textView6)).setTypeface(font_small);
        ((TextView) findViewById(R.id.textView7)).setTypeface(font_small);
        ((TextView) findViewById(R.id.textView8)).setTypeface(font_small);
        ((TextView) findViewById(R.id.textView9)).setTypeface(font_small);
//
        // ((TextView)findViewById(R.id.textView9)).setTextSize(20F);
        // (findViewById(R.id.textView2)).setVisibility(View.INVISIBLE);
        findViewById(textView).setVisibility(View.INVISIBLE);
        // findViewById(R.id.textView3).setVisibility(View.INVISIBLE);
        //   ((TextView) findViewById(R.id.textView4)).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView5).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView6).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView7).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView8).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView9).setVisibility(View.INVISIBLE);
        EqualizerView equalizer = (EqualizerView) findViewById(R.id.equalizer_view);
        equalizer.animateBars();


    }

    public void initShare() {
        findViewById(R.id.imageView_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLikeButton();
                if (isGooglePlayAvailable()) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")));
                } else {
                    tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Like Link")
                            .setAction("Clicked")
                            .setLabel("")
                            .build());
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, " ");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            }
        });
    }

    public void initGetPro() {
//
    }
//
//    public void initAnalytics() {
//        analytics = GoogleAnalytics.getInstance(this);
//        analytics.setLocalDispatchPeriod(1800); //will upload data to server every 1 minutes
//        tracker = analytics.newTracker(getResources().getString(R.string.admob_tracking_id)); // Replace with actual tracker/property Id
//
//        tracker.enableExceptionReporting(true);
//        tracker.enableAdvertisingIdCollection(true);
//        tracker.enableAutoActivityTracking(true);
//        //tracker.setSampleRate(90);
//
//        tracker.send(new HitBuilders.AppViewBuilder().build());
//    }

    private void finishActivity() {
        Toast.makeText(getApplicationContext(), "Closing ...", Toast.LENGTH_LONG).show();

        final int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                (int) Math.floor((float) volume - (float) volume * ((float) 1.5 / 5)),
                //  (int) Math.floor( (float) volume - (float) ( mMaxVolume - mCurrentVolume) * (bassSeekbar.getProgress() / 100) * 3 / 5),
                0);

        mEffects.disinit();
        if (mVirtualizer.getEnabled()) mVirtualizer.setEnabled(false);
        editor.putBoolean("activated", false); // Storing boolean - true/false
        editor.commit();
        if (mBass.getEnabled()) mBass.setEnabled(false);
        mEQ.release();
        //Toast.makeText(getApplicationContext(), "Bass Booster has been closed", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                clearNotification();
                finish();
                System.exit(0);
                // android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, 2000);
    }

    public void initPresentDialog() {
        mPresentDialog = new Dialog(this, (RelativeLayout) findViewById(R.id.relativeLayout), mEffects);
        ImageView present = (ImageView) findViewById(R.id.imageView_present);
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPresetBtn();
               // if (isAdpPresetCount()) {
                  //  showAds();
                //}
                mPresentDialog.showDialog();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        //analytics.reportActivityStart(this);
        if (!interstitial.isLoaded()) requestNewInterstitial();
        if (isValidAdCount()) {
            showAds();
        }
    }

    @Override
    public void onBackPressed() {
        showDialog();

        //Toast.makeText(getApplicationContext(), "Sound Mixer is running on background", Toast.LENGTH_SHORT).show();


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //if one of the volume keys were pressed
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Log.d("Volume value:", Math.round(100 * audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                    / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)) + "");
            mEffects.setIsSeeKFromKeyDown(true);
            mEffects.getSeekbar_vol().setProgress(Math.round(100 * audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                    / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)));

            if(!condactor){
                mEffects.init();
            }
        }
        //propagate the key event
        return super.onKeyDown(keyCode, event);
    }

    public RotateAnimation refreshRotateAnimation() {
        ImageView imageView_refresh = (ImageView) findViewById(R.id.imageView_refresh);
        RotateAnimation animation = new RotateAnimation(360f, 0f, imageView_refresh.getWidth() / 2, imageView_refresh.getHeight() / 2);
        animation.setRepeatCount(0);
        animation.setDuration(1000);
        imageView_refresh.startAnimation(animation);
        return animation;
    }

    public RotateAnimation refreshPresetBtn() {
        ImageView imageView_refresh = (ImageView) findViewById(R.id.imageView_present);
        RotateAnimation animation = new RotateAnimation(180f, 0f, imageView_refresh.getWidth() / 2, imageView_refresh.getHeight() / 2);
        animation.setRepeatCount(0);
        animation.setDuration(1000);
        imageView_refresh.startAnimation(animation);
        return animation;
    }


    public RotateAnimation refreshLikeButton() {
//        ImageView imageView_refresh = (ImageView) findViewById(R.id.imageView_like);
//        RotateAnimation animation = new RotateAnimation(360f, 0f, imageView_refresh.getWidth() / 2, imageView_refresh.getHeight() / 2);
//        animation.setRepeatCount(1);
//        animation.setDuration(500);
//        imageView_refresh.startAnimation(animation);
        return null;
    }


    public void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setIcon(R.drawable.ic_notif);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ((AlertDialog) dialog).setTitle("Please select action\nPress run to \ncontinue the virtual 3D\n sound on the background :)\"");
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(20F);
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20F);
            }
        });

        class alertDialogOnClickListener implements DialogInterface.OnClickListener {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        moveTaskToBack(true);
                        break;
                    }
                    case DialogInterface.BUTTON_NEGATIVE: {
                        clearNotification();
                        //android.os.Process.killProcess(android.os.Process.myPid());
                        //moveTaskToBack(true);
                        isExit = true;
                        //showAds();
                        break;
                    }
                }
            }
        }
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Run", new alertDialogOnClickListener());
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new alertDialogOnClickListener());
        alertDialog.show();
    }


    boolean condactor = false;

    public void onClickAnim(View v) {

        mEffects = new Effects(this, mEQ, mVirtualizer, mBass, audioManager);
        final SearchingAnimView searchingAnimView = (SearchingAnimView) findViewById(R.id.searchinganimview);
//        Intent i = new Intent(this, empty.class);
//        startActivity(i);
        assert searchingAnimView != null;
        searchingAnimView.startAnimations();
        condactor = !condactor;
        if (condactor) {
            //Toast.makeText(this, "Unpressed", Toast.LENGTH_SHORT).show();
            searchingAnimView.stopAnimations();
            createNotification("3D Sound is OFF");
            mEffects.disinit();
            editor.putBoolean("activated", false); // Storing boolean - true/false
            editor.commit();
            findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradient); //VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            ((TextView) findViewById(R.id.status)).setText("3D Sound is OFF"); //VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            final int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    (int) Math.floor((float) volume - (float) volume * ((float) 1.5 / 5)),
                    //  (int) Math.floor( (float) volume - (float) ( mMaxVolume - mCurrentVolume) * (bassSeekbar.getProgress() / 100) * 3 / 5),
                    0);
        } else {
            searchingAnimView.startAnimations();
            mEffects.init();
            editor.putBoolean("activated", true); // Storing boolean - true/false
            editor.commit();

            findViewById(R.id.relativeLayout).setBackgroundResource(R.drawable.gradclick);//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            //Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show();
            createNotification("3D Sound is ON");
            ((TextView) findViewById(R.id.status)).setText("3D Sound is ON");//VISUALLY SHOW THE NUMBER OF DAYS UNTIL IT EXPIRES
            // Toast.makeText(this, mEffects.getSeekbar_vol().getProgress(), Toast.LENGTH_SHORT).show();
            final int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    (int) Math.floor((float) volume + (float) volume * ((float) 2.5 / 5)),
                    //  (int) Math.floor( (float) volume - (float) ( mMaxVolume - mCurrentVolume) * (bassSeekbar.getProgress() / 100) * 3 / 5),
                    0);
        }
    }

    public void onClickActivityPay(View v) {

//        Intent i = new Intent(this, ActivityPay.class);
//        startActivity(i);
        // onDestroy();

    }


}

