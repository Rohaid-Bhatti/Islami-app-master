package com.example.islamiapp;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.islamiapp.Base.BaseActivity;

public class Splash extends BaseActivity {
    //ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //splash = findViewById(R.id.splashImageID);
        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentHome = new Intent(Splash.this , HomeActivity.class);
                startActivity(intentHome);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
