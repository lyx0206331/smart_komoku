package com.adrian.gomoku.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.adrian.gomoku.R;
import com.adrian.gomoku.tools.CommUtil;
import com.adrian.gomoku.tools.ImageUtil;

public class WelcomeActivity extends BaseActivity {

    private ImageView mBgIV;

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

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_welcome);
        DisplayMetrics dm = CommUtil.getScreenInfo();
        mBgIV = (ImageView) findViewById(R.id.iv_bg);
        mBgIV.setImageBitmap(ImageUtil.getImageFromResource(R.drawable.nav_0, dm.widthPixels, dm.heightPixels));
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void loadData() {
    }
}
