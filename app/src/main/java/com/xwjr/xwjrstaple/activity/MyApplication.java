package com.xwjr.xwjrstaple.activity;

import android.app.Application;

import com.xwjr.staple.constant.StapleConfig;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StapleConfig.INSTANCE.setAppSource(StapleConfig.WWXHB);
    }
}
