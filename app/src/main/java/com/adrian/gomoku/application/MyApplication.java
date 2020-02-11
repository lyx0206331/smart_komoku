package com.adrian.gomoku.application;

import android.app.Application;

import com.adrian.gomoku.BuildConfig;
import com.huawei.hms.ads.HwAds;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

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
        //初始化友盟SDK
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        //选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_AUTO);
        //集成测试
        if (BuildConfig.DEBUG) {
            // 打开统计SDK调试模式
            UMConfigure.setLogEnabled(true);
        }
        //华为广告初始化
        HwAds.init(this);
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
