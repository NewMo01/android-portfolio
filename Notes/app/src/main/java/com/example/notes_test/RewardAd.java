package com.example.notes_test;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class RewardAd  {

    private static final int adTime = 4 ;
    public static int counter = 0 ;


    private static RewardedAd mRewardedAd;

    public static void loadAd(Context context ){
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, "ca-app-pub-5642944910710670/8766036132", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
                mRewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
                loadAd(context);
            }
        });
    }
    public static void showAd(Context context){
        if (mRewardedAd != null && counter== adTime) {
            Activity activityContext = (Activity) context;
            mRewardedAd.show(activityContext, rewardItem -> {
            });
            counter=0;
        }
    }


}
