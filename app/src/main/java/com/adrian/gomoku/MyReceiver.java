package com.adrian.gomoku;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adrian.gomoku.service.DownloadService;

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION_UPDATE = "com.adrian.start_update";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("RECEIVER", action);
        if (action.equals(ACTION_UPDATE)) {
            Intent i = new Intent(context, DownloadService.class);
            i.setAction(DownloadService.ACTION_START_DOWNLOAD);
            i.putExtra("apk_url", intent.getStringExtra("url"));
            context.startService(i);
        }
    }
}
