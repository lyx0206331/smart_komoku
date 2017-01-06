package com.adrian.gomoku.tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.adrian.gomoku.application.MyApplication;

/**
 * Created by ranqing on 2017/1/7.
 */

public class CommUtil {

    public static Display getScreenWH() {
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display;
    }

    public static DisplayMetrics getScreenInfo() {
        Resources resources = MyApplication.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm;
    }
}
