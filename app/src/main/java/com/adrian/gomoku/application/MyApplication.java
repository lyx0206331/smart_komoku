package com.adrian.gomoku.application;

import android.app.Application;

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
