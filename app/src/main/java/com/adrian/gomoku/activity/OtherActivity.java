package com.adrian.gomoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.adrian.gomoku.receiver.MyReceiver;
import com.adrian.gomoku.R;
import com.adrian.gomoku.service.BgMusicService;
import com.adrian.gomoku.tools.CommUtil;
import com.adrian.gomoku.tools.DownloadTask;
import com.adrian.gomoku.tools.ParamUtil;
import com.adrian.gomoku.tools.UpdateHelper;
import com.adrian.gomoku.views.RippleView;
import com.adrian.gomoku.views.SwitchButton;

import java.io.File;

public class OtherActivity extends BaseActivity {

    private SwitchButton mBgMusicSB;
    private SwitchButton mPieceSoundSB;
    private RippleView mRuleRV;
    private RippleView mUpdateRV;
    private TextView mVersionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_other);
        mBgMusicSB = (SwitchButton) findViewById(R.id.sb_bg_music);
        mBgMusicSB.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ParamUtil.getInstance().setOpenBgMusic(isChecked);
                if (isChecked) {
                    Intent intent = new Intent(OtherActivity.this, BgMusicService.class);
                    intent.setAction(BgMusicService.ACTION_START_PLAY);
                    startService(intent);
                } else {
                    Intent intent = new Intent(OtherActivity.this, BgMusicService.class);
                    stopService(intent);
                }
            }
        });
        mPieceSoundSB = (SwitchButton) findViewById(R.id.sb_piece_sound);
        mPieceSoundSB.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ParamUtil.getInstance().setOpenedPieceSound(isChecked);
            }
        });
        mRuleRV = (RippleView) findViewById(R.id.tv_rule);
        mRuleRV.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(RuleActivity.class);
            }
        });
        mUpdateRV = (RippleView) findViewById(R.id.tv_update);
        mUpdateRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new UpdateHelper(OtherActivity.this, false)).execute();

//                Intent intent = new Intent(MyReceiver.ACTION_UPDATE);
//                Bundle bundle = new Bundle();
//                bundle.putString("url", "http://www.ximalaya.com/down?tag=web&client=android");
//                intent.putExtras(bundle);
//                mContext.sendBroadcast(intent);

//                String savePath = null;
//                    if (CommUtil.isHasSdcard()) {
//                        savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gomoku/download/";
//                    } else {
//                        savePath = getApplicationContext().getFilesDir().getAbsolutePath() + "/gomoku/download/";
//                    }
//                    File desDir = new File(savePath);
//                    if (!desDir.exists()) {
//                        desDir.mkdirs();
//                    }
//                DownloadTask task = new DownloadTask(OtherActivity.this, savePath + "gomoku.apk");
//                task.execute("http://www.ximalaya.com/down?tag=web&client=android");
            }
        });
        mVersionTV = (TextView) findViewById(R.id.tv_version);
        mVersionTV.setText(CommUtil.getVersionName(this));
        initToolbar();
        mBgMusicSB.setChecked(ParamUtil.getInstance().openedBgMusic());
        mPieceSoundSB.setChecked(ParamUtil.getInstance().openedPieceSound());
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
//                setResult(MainActivity.RES_OTHER);
//                finish();
                onBackPressed();
            }
        });
        mToolBarTextView.setText(R.string.other);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        setResult(MainActivity.RES_OTHER);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_transparent, menu);
        return true;
    }
}
