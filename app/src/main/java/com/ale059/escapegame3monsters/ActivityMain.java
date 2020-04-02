package com.ale059.escapegame3monsters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
//import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
//import com.google.android.gms.ads.rewarded.RewardedAd;
//import com.google.android.gms.ads.rewarded.RewardedAdCallback;
//import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class ActivityMain extends AppCompatActivity implements RewardedVideoAdListener {

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
    }

    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        app.mMainActivity = this;

        MobileAds.initialize(this );

        AdView mAdView = findViewById(R.id.adBannerTop);
        if (mAdView!=null)
            mAdView.loadAd( new AdRequest.Builder().build() );

        mAdView = findViewById(R.id.adBannerBottom);
        if (mAdView!=null)
            mAdView.loadAd( new AdRequest.Builder().build() );

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadRewardedVideoAd();
    }
    private void loadRewardedVideoAd() {
        //mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build()); // TestAd
        mRewardedVideoAd.loadAd("ca-app-pub-5519702986389997/4365041564", new AdRequest.Builder().build());
        // ca-app-pub-5519702986389997/4365041564
    }


    @Override
    public void onRewarded(RewardItem reward) {
        //app.egPlaySound( R.raw.snd_cat); // Reward the user.
        app.egOpenMenuScene( app.SCENE_MENU_HINT );
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }

    @Override public void onRewardedVideoAdLeftApplication() {
        //Toast.makeText(this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
    }
    @Override public void onRewardedVideoAdFailedToLoad(int errorCode) {
        //Toast.makeText(this, "onRewardedVideoAdFailedToLoad "+errorCode, Toast.LENGTH_SHORT).show();
        if (app.egGetProgressEventValue("have_game_progress")==1)
            app.egOpenMenuScene( app.SCENE_MENU_ADS_FAILED );

    }
    @Override public void onRewardedVideoAdLoaded() {
        //Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }
    @Override public void onRewardedVideoAdOpened() {
        //Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }
    @Override public void onRewardedVideoStarted() {
        //Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }
    @Override public void onRewardedVideoCompleted() {
        //Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
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


    public void showRewardedVideo() {
        //showVideoButton.setVisibility(View.INVISIBLE);
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
        else
            app.egOpenMenuScene( app.SCENE_MENU_ADS_FAILED );

    }
}
