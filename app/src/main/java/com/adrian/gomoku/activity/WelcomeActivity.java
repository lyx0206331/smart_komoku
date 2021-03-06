package com.adrian.gomoku.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.adrian.gomoku.R;
import com.adrian.gomoku.tools.CommUtil;
import com.adrian.gomoku.tools.ImageUtil;
import com.adrian.gomoku.tools.PermissionHelper;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAd;
import com.huawei.hms.ads.reward.RewardAdLoadListener;
import com.huawei.hms.ads.reward.RewardAdStatusListener;

public class WelcomeActivity extends BaseActivity {

    private static final String AD_ID = "testx9dtjwj8hp";
    private RewardAd rewardAd;

    private ImageView mBgIV;
    private PermissionHelper mPermissionHelper;
    private LinearLayout mBottomLL;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    startActivity(MainActivity.class);
                    finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {
        loadjHuaweiRewordAd();
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_welcome);
        DisplayMetrics dm = CommUtil.getScreenInfo();
        mBgIV = (ImageView) findViewById(R.id.iv_bg);
        mBgIV.setImageBitmap(ImageUtil.getImageFromResource(R.drawable.nav_0, dm.widthPixels, dm.heightPixels));
        mBottomLL = (LinearLayout) findViewById(R.id.ll_bottom);
//        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void loadData() {
        // 当系统为6.0以上时，需要申请权限
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
//                Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
                runApp();
            }
        });
        if (Build.VERSION.SDK_INT < 23) {
            // 如果系统版本低于23，直接跑应用的逻辑
//            Log.d(TAG, "The api level of system is lower than 23, so run app logic directly.");
            runApp();
        } else {
            // 如果权限全部申请了，那就直接跑应用逻辑
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
//                Log.d(TAG, "All of requested permissions has been granted, so run app logic directly.");
                runApp();
            } else {
                // 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
//                Log.i(TAG, "Some of requested permissions hasn't been granted, so apply permissions first.");
                mPermissionHelper.applyPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 跑应用的逻辑
     */
    private void runApp() {
//        //初始化SDK
//        AdManager.getInstance(this).init("d04d2ef88fffe41b", "ea11dd60a66886f9", false, true);
        //设置开屏
        setupSplashAd();
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd() {
        // 创建开屏容器
        final RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.rl_splash);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.view_divider);

//        handler.sendEmptyMessageDelayed(0, 3000);
    }

    /**
     * 加载华为广告
     */
    private void loadjHuaweiRewordAd() {
        if (rewardAd == null) {
            rewardAd = new RewardAd(this, AD_ID);
        }
        RewardAdLoadListener loadListener = new RewardAdLoadListener() {
            @Override
            public void onRewardAdFailedToLoad(int i) {
                super.onRewardAdFailedToLoad(i);
                showShortToast("广告加载失败");
                handler.sendEmptyMessageDelayed(0, 3000);
            }

            @Override
            public void onRewardedLoaded() {
                super.onRewardedLoaded();
                showShortToast("广告加载成功");
                rewardAdShow();
            }
        };
        rewardAd.loadAd(new AdParam.Builder().build(), loadListener);
    }

    private void rewardAdShow() {
        if (rewardAd.isLoaded()) {
            rewardAd.show(this, new RewardAdStatusListener() {
                @Override
                public void onRewardAdClosed() {
                    super.onRewardAdClosed();
                    showShortToast("广告被关闭");
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onRewardAdFailedToShow(int i) {
                    super.onRewardAdFailedToShow(i);
                    showShortToast("广告展示失败");
                    handler.sendEmptyMessageDelayed(0, 3000);
                }

                @Override
                public void onRewardAdOpened() {
                    super.onRewardAdOpened();
                    showShortToast("广告被打开");
                }

                @Override
                public void onRewarded(Reward reward) {
                    super.onRewarded(reward);
                    showShortToast("广告奖励达成");
                    handler.sendEmptyMessage(0);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
