package com.adrian.gomoku.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mParentLL = (LinearLayout) rootView.findViewById(R.id.fragment_main);
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

        setSinglePlayer();
        setPieceSound();
        setTheme();
        return rootView;
    }

    public void setTheme() {
        mParentLL.setBackgroundResource(ParamUtil.getInstance().getBgResId());
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
}
