package com.adrian.gomoku.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adrian.gomoku.R;
import com.adrian.gomoku.activity.BaseActivity;
import com.adrian.gomoku.ai.GomokuAI;
import com.adrian.gomoku.views.GomokuView;

import de.cketti.library.changelog.ChangeLog;

import static java.security.AccessController.getContext;

public class MainActivity extends BaseActivity implements GomokuView.IGameOverListener, View.OnClickListener {

    private GomokuView mGomokuView;
    private AlertDialog mAlertDialog;
    private Button mRevokeBtn;
    private Button mRestartBtn;

    private long mLastBackPress;
    private static final long mBackPressThreshold = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

        Log.e("SDK_VERSION", "系统版本：" + Build.VERSION.SDK_INT);

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        mGomokuView = (GomokuView) findViewById(R.id.gomoku_view);
        mRevokeBtn = (Button) findViewById(R.id.btn_revoke);
        mRestartBtn = (Button) findViewById(R.id.btn_restart);
        mGomokuView.setListener(this);
        mRevokeBtn.setOnClickListener(this);
        mRestartBtn.setOnClickListener(this);

        ChangeLog cl = new ChangeLog(this);
        if (cl.isFirstRun()) {
            cl.getLogDialog().show();
        }

        mGomokuView.setAiOpened(true);
        if (mGomokuView.isAiOpened()) {
            mRevokeBtn.setVisibility(View.GONE);
        } else {
            mRevokeBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (Math.abs(currentTime - mLastBackPress) > mBackPressThreshold) {
            Toast.makeText(this, R.string.back_again_quit, Toast.LENGTH_SHORT).show();
            mLastBackPress = currentTime;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void gameOver(boolean isWhiteWin) {
        int msgId = isWhiteWin ? R.string.white_win : R.string.black_win;
        if (mAlertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            mAlertDialog = builder.setPositiveButton(R.string.quit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton(R.string.start_again, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mGomokuView.start();
                    dialog.dismiss();
                }
            }).setCancelable(false).create();
        }
        mAlertDialog.setMessage(getString(msgId));
        mAlertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_revoke:
                mGomokuView.revoke();
                break;
            case R.id.btn_restart:
                mGomokuView.start();
                break;
        }
    }
}
