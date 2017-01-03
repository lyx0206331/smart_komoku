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

    public static final String BOARD_COLOR = "boardColor";
    public static final String BG_RES_ID = "bgResId";
    public static final String SINGLE_PLAYER = "singlePlayer";
    public static final String OPENED_PIECE = "openedPieceSound";
    public static final String OPENED_BG_MUSIC = "openedBgMusic";

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

    /**
     * 设置背景资源ID
     *
     * @param resId
     */
    public void setBgResId(int resId) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(BG_RES_ID, resId);
        editor.commit();
    }

    /**
     * 获取背景资源ID
     * @return
     */
    public int getBgResId() {
        SharedPreferences pref = getPref();
        return pref.getInt(BG_RES_ID, R.drawable.bg_4);
    }

    /**
     * 设置棋盘颜色
     * @param color
     */
    public void setBoardColor(int color) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(BOARD_COLOR, color);
        editor.commit();
    }

    /**
     * 获取棋盘颜色
     * @return
     */
    public int getBoardColor() {
        SharedPreferences pref = getPref();
        return pref.getInt(BOARD_COLOR, 0x88000000);
    }

    /**
     * 设置是否单人模式
     * @param isSingle
     */
    public void setSinglePlayer(boolean isSingle) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(SINGLE_PLAYER, isSingle);
        editor.commit();
    }

    /**
     * 获取是否单人模式
     * @return
     */
    public boolean isSinglePlayer() {
        SharedPreferences pref = getPref();
        return pref.getBoolean(SINGLE_PLAYER, true);
    }

    /**
     * 设置是否打开落子声音
     *
     * @param has
     */
    public void setOpenedPieceSound(boolean has) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(OPENED_PIECE, has);
        editor.commit();
    }

    /**
     * 判断是否已打开落子声音
     *
     * @return
     */
    public boolean openedPieceSound() {
        SharedPreferences pref = getPref();
        return pref.getBoolean(OPENED_PIECE, true);
    }

    /**
     * 设置是否打开背景音乐
     *
     * @param opened
     */
    public void setOpenBgMusic(boolean opened) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(OPENED_BG_MUSIC, opened);
        editor.commit();
    }

    /**
     * 判断是否已打开背景音乐
     *
     * @return
     */
    public boolean openedBgMusic() {
        SharedPreferences pref = getPref();
        return pref.getBoolean(OPENED_BG_MUSIC, true);
    }
}
