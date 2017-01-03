package com.adrian.gomoku.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.adrian.gomoku.R;
import com.adrian.gomoku.tools.ParamUtil;

public class ModeActivity extends BaseActivity {

    private RadioGroup mModeRG;
    private Button mConfirmBtn;

    private boolean isSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {
        isSingle = ParamUtil.getInstance().isSinglePlayer();
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_mode);
        initToolbar();
        mModeRG = (RadioGroup) findViewById(R.id.rg_mode);
        mConfirmBtn = (Button) findViewById(R.id.btn_confirm);

        mModeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_single:
                        isSingle = true;
                        break;
                    case R.id.rb_double:
                        isSingle = false;
                        break;
                }
            }
        });
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamUtil.getInstance().setSinglePlayer(isSingle);
                setResult(MainActivity.RES_MODE);
                finish();
            }
        });
        if (isSingle) {
            mModeRG.check(R.id.rb_single);
        } else {
            mModeRG.check(R.id.rb_double);
        }
    }

    @Override
    protected void loadData() {

    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.mipmap.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolBarTextView.setText(R.string.mode);
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_transparent, menu);
        return true;
    }
}
