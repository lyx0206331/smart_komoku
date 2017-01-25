package com.adrian.gomoku.application;

import android.app.Application;

import net.youmi.android.AdManager;

/**
 * Created by ranqing on 2017/1/2.
 */

public class MyApplication extends Application {

    private boolean isDownloading = false;

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化SDK
        AdManager.getInstance(this).init("d04d2ef88fffe41b", "ea11dd60a66886f9", false, true);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }
}
