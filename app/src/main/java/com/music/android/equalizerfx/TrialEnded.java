/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.music.android.equalizerfx;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.music.android.util.IabHelper;
import com.music.android.util.IabResult;
import com.music.android.util.Inventory;
import com.music.android.util.Purchase;

public class TrialEnded extends Activity {

    private static final String TAG = "MainActivity";

    // Item name for premium status
    private static final String SKU_PREMIUM = "premium";
    // Flag set to true when we have premium status
    private boolean mIsPremium = false;

    // Advertising instance
    // private AdView mAdView;
    // In-app Billing helper
    private IabHelper mAbHelper;
    // Advertising placement layout
    private RelativeLayout mAdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //     mAdLayout = (RelativeLayout) findViewById(R.id.adplacement);

        // your app's base64 encoded public key
        // TODO: place here your base64 encoded key
        String base64EncodedPublicKey = "";

        // Create in-app billing helper
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAbHelper != null)
            try {
                mAbHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        mAbHelper = null;
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
                updateInterface();
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

        Button btUpgrade = (Button) findViewById(R.id.btUpgrade);
        if (mIsPremium) {
            btUpgrade.setText(R.string.you_have_premium_status);
            btUpgrade.setEnabled(false);
            displayAd(false);
        } else {
            btUpgrade.setEnabled(true);
            displayAd(true);
        }
    }

    /**
     * Displays or hides the advertising
     *
     * @param state
     */
    private void displayAd(boolean state) {

        if (state) {
//            if (mAdView == null) {
//
//                // Google has dropped Google Play Services support for Froyo
//                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
//                    mAdLayout.setVisibility(View.VISIBLE);
//
//
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            RelativeLayout.LayoutParams.MATCH_PARENT,
//                            RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    //mAdLayout.addView(mAdView, params);
//                    //mAdView.loadAd(new AdRequest.Builder().build());
//                }
        }
//        } else {
//
//            mAdLayout.setVisibility(View.GONE);
//            if (mAdView != null) {
//                mAdView.destroy();
//                mAdView = null;
//            }

        //}
    }
}