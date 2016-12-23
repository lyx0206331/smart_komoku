package com.adrian.gomoku.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.adrian.gomoku.R;
import com.adrian.gomoku.activity.BaseActivity;
import com.adrian.gomoku.views.GomokuView;

import de.cketti.library.changelog.ChangeLog;

import static java.security.AccessController.getContext;

public class MainActivity extends BaseActivity implements GomokuView.IGameOverListener {

    private GomokuView mGomokuView;

    private long mLastBackPress;
    private static final long mBackPressThreshold = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        mGomokuView = (GomokuView) findViewById(R.id.gomoku_view);
        mGomokuView.setListener(this);

        ChangeLog cl = new ChangeLog(this);
        if (cl.isFirstRun()) {
            cl.getLogDialog().show();
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (Math.abs(currentTime - mLastBackPress) > mBackPressThreshold) {
            Toast.makeText(this, "再按一次返回退出", Toast.LENGTH_SHORT).show();
            mLastBackPress = currentTime;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void gameOver(boolean isWhiteWin) {
        String text = isWhiteWin ? "白棋胜利" : "黑棋胜利";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text).setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("再来一局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGomokuView.start();
                dialog.cancel();
            }
        }).create().show();
    }
}
