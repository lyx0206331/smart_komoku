package com.adrian.gomoku.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adrian.gomoku.R;
import com.adrian.gomoku.tools.CommUtil;
import com.adrian.gomoku.tools.ImageUtil;
import com.adrian.gomoku.tools.ParamUtil;
import com.adrian.gomoku.views.GomokuView;

import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

import de.cketti.library.changelog.ChangeLog;


public class MainFragment extends Fragment implements GomokuView.IGameOverListener, View.OnClickListener {

    private ImageView mBgIV;
    private GomokuView mGomokuView;
    private AlertDialog mAlertDialog;
    private Button mRevokeBtn;
    private Button mRestartBtn;

    private Display display;
    private DisplayMetrics dm;
    private int screenW, screenH;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mBgIV = (ImageView) rootView.findViewById(R.id.iv_bg);
        mGomokuView = (GomokuView) rootView.findViewById(R.id.gomoku_view);
        mRevokeBtn = (Button) rootView.findViewById(R.id.btn_revoke);
        mRestartBtn = (Button) rootView.findViewById(R.id.btn_restart);
        mGomokuView.setPieceSoundResId(R.raw.piece);
        mGomokuView.setListener(this);
        mRevokeBtn.setOnClickListener(this);
        mRestartBtn.setOnClickListener(this);

        ChangeLog cl = new ChangeLog(getContext());
        if (cl.isFirstRun()) {
            cl.getLogDialog().show();
        }

        dm = CommUtil.getScreenInfo();
        screenW = dm.widthPixels;
        screenH = dm.heightPixels;

        setSinglePlayer();
        setPieceSound();
        setTheme();
//        setupBannerAd();
        return rootView;
    }

    public void setTheme() {
        mBgIV.setImageBitmap(ImageUtil.getImageFromResource(ParamUtil.getInstance().getBgResId(), screenW, screenH));
        mGomokuView.setBoardColor(ParamUtil.getInstance().getBoardColor());
    }

    public void setPieceSound() {
        mGomokuView.setSoundOpened(ParamUtil.getInstance().openedPieceSound());
    }

    public void setSinglePlayer() {
        boolean isSingle = ParamUtil.getInstance().isSinglePlayer();
        if (isSingle && !mGomokuView.isAiOpened()) {
            mRevokeBtn.setVisibility(View.GONE);
            mGomokuView.setAiOpened(true);
            mGomokuView.start();
        } else if (!isSingle && mGomokuView.isAiOpened()) {
            mRevokeBtn.setVisibility(View.VISIBLE);
            mGomokuView.setAiOpened(false);
            mGomokuView.start();
        }
    }

    @Override
    public void gameOver(boolean isWhiteWin) {
        int msgId = isWhiteWin ? R.string.white_win : R.string.black_win;
        if (mAlertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            mAlertDialog = builder.setPositiveButton(R.string.quit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置广告条广告
     */
    private void setupBannerAd() {
        /**
         * 悬浮布局
         */
        // 实例化LayoutParams
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置广告条的悬浮位置，这里示例为右下角
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        // 获取广告条
        View bannerView = BannerManager.getInstance(getContext())
                .getBannerView(getContext(), new BannerViewListener() {

                    @Override
                    public void onRequestSuccess() {
//                        logInfo("请求广告条成功");
                        Log.i("TAG", "请求广告条成功");

                    }

                    @Override
                    public void onSwitchBanner() {
//                        logDebug("广告条切换");
                        Log.d("TAG", "广告条切换");
                    }

                    @Override
                    public void onRequestFailed() {
//                        logError("请求广告条失败");
                        Log.e("TAG", "请求广告条失败");
                    }
                });
        // 添加广告条到窗口中
        getActivity().addContentView(bannerView, layoutParams);
    }
}
