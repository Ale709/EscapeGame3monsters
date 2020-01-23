package com.ale059.escapegameplasticineworld1;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    }

}
