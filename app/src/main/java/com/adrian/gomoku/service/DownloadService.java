package com.adrian.gomoku.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import com.adrian.gomoku.tools.CommUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadService extends Service {

    private String savePath;
    private String appName = "gomoku.apk";

    private String url;

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
        if (savePath != null) {
            return;
        }
        if (CommUtil.isHasSdcard()) {
            savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gomoku/download/";
        } else {
            savePath = getApplicationContext().getFilesDir().getAbsolutePath() + "/gomoku/download/";
        }
    }

    class DownloadThread extends Thread {
        @Override
        public void run() {
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {
                HttpResponse httpResponse = client.execute(httpPost);
                byte[] result = EntityUtils.toByteArray(httpResponse
                        .getEntity());//使用http协议下载图片
                boolean flag = write2sdcard(savePath, result);//当成功写入内存卡后，将标志设为true
                if (flag) {

//                    handler.sendEmptyMessage(1);//通过handler发送消息
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private boolean write2sdcard(String path, byte[] data) {
        FileOutputStream fis;
        boolean flag = false;
        try {
            fis = new FileOutputStream(path + "/gomoku.apk");
            fis.write(data);
            flag = true;
            fis.flush();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
