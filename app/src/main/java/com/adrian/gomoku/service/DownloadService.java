package com.adrian.gomoku.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.adrian.gomoku.tools.CommUtil;
import com.adrian.gomoku.tools.DownloadTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadService extends Service {

    public static final String ACTION_START_DOWNLOAD = "com.adrian.start_download";
    public static final String ACTION_STOP_DOWNLOAD = "com.adrian.stop_download";

    private String savePath;
    private String appName = "gomoku.apk";

    private DownloadTask downloadTask;

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        if (CommUtil.isHasSdcard()) {
            savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gomoku/download/";
        } else {
            savePath = getApplicationContext().getFilesDir().getAbsolutePath() + "/gomoku/download/";
        }
        File desDir = new File(savePath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        String url = intent.getStringExtra("apk_url");
        Log.e("DOWNLOAD", "action : " + action + " -- url : " + url);
        if (action.equals(ACTION_START_DOWNLOAD)) {
            downloadTask = new DownloadTask(this, savePath + appName);
            downloadTask.execute(url);
        } else if (action.equals(ACTION_STOP_DOWNLOAD)) {
            if (downloadTask != null) {
                downloadTask.cancel(true);
                downloadTask = null;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
