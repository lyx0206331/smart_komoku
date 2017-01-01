package com.adrian.gomoku.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.health.ServiceHealthStats;

import com.adrian.gomoku.R;
import com.adrian.gomoku.application.MyApplication;
import com.adrian.gomoku.fragment.MainFragment;

/**
 * Created by ranqing on 2017/1/2.
 */

public class ParamUtil {

    private static ParamUtil instance;

    public static ParamUtil getInstance() {
        if (instance == null) {
            instance = new ParamUtil();
        }
        return instance;
    }

    private SharedPreferences getPref() {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences("param", Context.MODE_PRIVATE);
        return pref;
    }

    public void setBgResId(int resId) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(MainFragment.BG_RES_ID, resId);
        editor.commit();
    }

    public int getBgResId() {
        SharedPreferences pref = getPref();
        return pref.getInt(MainFragment.BG_RES_ID, R.drawable.bg_4);
    }

    public void setBoardColor(int color) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(MainFragment.BOARD_COLOR, color);
        editor.commit();
    }

    public int getBoardColor() {
        SharedPreferences pref = getPref();
        return pref.getInt(MainFragment.BOARD_COLOR, 0x88000000);
    }

    public void setSinglePlayer(boolean isSingle) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(MainFragment.SINGLE_PLAYER, isSingle);
        editor.commit();
    }

    public boolean isSinglePlayer() {
        SharedPreferences pref = getPref();
        return pref.getBoolean(MainFragment.SINGLE_PLAYER, true);
    }
}
