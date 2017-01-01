package com.adrian.gomoku.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.adrian.gomoku.R;
import com.adrian.gomoku.tools.ParamUtil;
import com.adrian.gomoku.views.GomokuView;

import de.cketti.library.changelog.ChangeLog;


public class MainFragment extends Fragment implements GomokuView.IGameOverListener, View.OnClickListener {

    private LinearLayout mParentLL;
    private GomokuView mGomokuView;
    private AlertDialog mAlertDialog;
    private Button mRevokeBtn;
    private Button mRestartBtn;

    public static final String BOARD_COLOR = "boardColor";
    public static final String BG_RES_ID = "bgResId";
    public static final String SINGLE_PLAYER = "singlePlayer";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mParentLL = (LinearLayout) rootView.findViewById(R.id.fragment_main);
        mGomokuView = (GomokuView) rootView.findViewById(R.id.gomoku_view);
        mRevokeBtn = (Button) rootView.findViewById(R.id.btn_revoke);
        mRestartBtn = (Button) rootView.findViewById(R.id.btn_restart);
        mGomokuView.setListener(this);
        mRevokeBtn.setOnClickListener(this);
        mRestartBtn.setOnClickListener(this);

        ChangeLog cl = new ChangeLog(getContext());
        if (cl.isFirstRun()) {
            cl.getLogDialog().show();
        }

//        mGomokuView.setAiOpened(true);
//        if (mGomokuView.isAiOpened()) {
//            mRevokeBtn.setVisibility(View.GONE);
//        } else {
//            mRevokeBtn.setVisibility(View.VISIBLE);
//        }
        setSinglePlayer(ParamUtil.getInstance().isSinglePlayer());

        setBackgroundResId(ParamUtil.getInstance().getBgResId());
        setBoardColor(ParamUtil.getInstance().getBoardColor());
        return rootView;
    }

    public void setSinglePlayer(boolean isSingle) {
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

    public void setBackgroundResId(int resId) {
        mParentLL.setBackgroundResource(resId);
    }

    public int getBackgroundResId() {
        return ParamUtil.getInstance().getBgResId();
    }

    public void setBackgroundColor(int color) {
        mParentLL.setBackgroundColor(color);
    }

    public void setBoardColor(int color) {
        mGomokuView.setBoardColor(color);
    }

    public int getBoardColor() {
        return ParamUtil.getInstance().getBoardColor();
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
}
