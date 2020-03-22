package com.ale059.escapegame3monsters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ActivityMain extends AppCompatActivity {

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MobileAds.initialize(this );

        AdView mAdView = findViewById(R.id.adBannerTop);
        if (mAdView!=null)
            mAdView.loadAd( new AdRequest.Builder().build() );

        mAdView = findViewById(R.id.adBannerBottom);
        if (mAdView!=null)
            mAdView.loadAd( new AdRequest.Builder().build() );

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onResume()
    {
        if (app.IsMusicOn==1)
            startService(new Intent(this, ABgMusicService.class));

        super.onResume();
    }

    @Override
    public void onPause()
    {
        if (app.IsMusicOn==1)
            stopService(new Intent(this, ABgMusicService.class));

        super.onPause();
    }
}
