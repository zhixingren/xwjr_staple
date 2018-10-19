package com.xwjr.xwjrstaple;

import android.app.Application;

import com.xwjr.staple.constant.StapleConfig;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StapleConfig.INSTANCE.setAppSource(StapleConfig.APPHUB);
        StapleConfig.INSTANCE.setDebug(true);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setAlias(this, 5233, "a0000000");
        Set<String> tags = new HashSet<>();
        tags.add("A");
        tags.add("B");
        JPushInterface.setTags(this, 5233, tags);
    }
}