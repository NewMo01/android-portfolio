package com.example.notes_test;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class BannarAd  {

    static AdRequest adRequest;

    public static void loadAd(Context context) {
        MobileAds.initialize(context, initializationStatus -> {
        });
        adRequest = new AdRequest.Builder().build();
    }

    public static void showAd(AdView mAdView) {
        mAdView.loadAd(adRequest);
    }
}
