package com.example.notes_test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.notes_test.adapters.MyPagerAdapter;
import com.example.notes_test.fragments.Goals;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class MainScreen extends AppCompatActivity implements Goals.con , NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ViewPager viewPager;
    MyPagerAdapter pagerAdapter;
    TabLayout tabLayout;
    NavigationView navigationView ;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        // initialize components
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager() , tabLayout.getTabCount() );


        // Ads
        loadAd();
        showAd();

        setSupportActionBar(toolbar);
        createToggleMenu();
        changeTabs();

        // drawable menu layout on item click
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void loadAd(){
        MobileAds.initialize(this, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-5642944910710670/2172707629", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }
    public void showAd() {
        new Handler().postDelayed(() -> {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainScreen.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }, 5000);
    }


    private void createToggleMenu(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout
                ,toolbar,R.string.open , R.string.close);

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void changeTabs(){
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void config() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

            getWindow().setSharedElementsUseOverlay(false);
            getWindow().setExitTransition(null);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // close menu
        drawerLayout.closeDrawer(GravityCompat.START);
        // check id
        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(this , MainScreen.class));
                finish();
                break;
            case R.id.feedback:
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:loliisliman3@gmail.com"));
                startActivity(intent);
                break;
        }
        return false;
    }
}