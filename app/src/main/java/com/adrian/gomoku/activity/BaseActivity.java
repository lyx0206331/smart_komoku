package com.adrian.gomoku.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mPd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_GAME);
        initVariables();
        initViews();
        loadData();
    }

    protected void startActivityForResult(Class<? extends Activity> dstAct, int reqCode) {
        Intent intent = new Intent(getApplicationContext(), dstAct);
        startActivityForResult(intent, reqCode);
    }

    protected void startActivityForResult(Class<? extends Activity> dstAct, int reqCode, @NonNull Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), dstAct);
        intent.putExtras(bundle);
        startActivityForResult(intent, reqCode);
    }

    protected void startActivity(Class<? extends Activity> dstAct) {
        Intent intent = new Intent(getApplicationContext(), dstAct);
        startActivity(intent);
    }

    protected void startActivity(Class<? extends Activity> dstAct, @NonNull Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), dstAct);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void showProgress(boolean show) {
        if (mPd == null) {
            mPd = new ProgressDialog(this);
            mPd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mPd.setMessage("Loading...");
            mPd.setIndeterminate(false);
            mPd.setCancelable(false);
        }
        if (show && !mPd.isShowing()) {
            mPd.show();
        } else if (!show && mPd.isShowing()) {
            mPd.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 初始化变量
     */
    protected abstract void initVariables();

    /**
     * 初始化UI
     */
    protected abstract void initViews();

    /**
     * 数据加载
     */
    protected abstract void loadData();
}
