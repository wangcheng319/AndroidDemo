package com.example.wangc.app;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wangc on 2016/5/23.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(getApplicationContext());
    }
}
