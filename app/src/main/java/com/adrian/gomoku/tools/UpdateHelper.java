package com.adrian.gomoku.tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.adrian.gomoku.receiver.MyReceiver;
import com.adrian.gomoku.R;

import net.youmi.android.AdManager;
import net.youmi.android.update.AppUpdateInfo;

/**
 * Created by adrian on 17-1-10.
 */

public class UpdateHelper extends AsyncTask<Void, Void, AppUpdateInfo> {
    private Context mContext;
    private boolean isAuto;

    public UpdateHelper(Context context, boolean autoCheck) {
        mContext = context;
        isAuto = autoCheck;
    }

    @Override
    protected AppUpdateInfo doInBackground(Void... params) {
        try {
            // 在 doInBackground 中调用 AdManager 的 checkAppUpdate 即可从有米服务器获得应用更新信息。
            return AdManager.getInstance(mContext).syncCheckAppUpdate();
            // 此方法务必在非 UI 线程调用，否则有可能不成功。
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(final AppUpdateInfo result) {
        super.onPostExecute(result);
        try {
            if (result == null || result.getUrl() == null) {
                Log.i("CHECK_UPDATE", "has no new version!");
                if (!isAuto) {
                    // 如果 AppUpdateInfo 为 null 或它的 url 属性为 null，则可以判断为没有新版本。
                    Toast.makeText(mContext, R.string.has_no_update, Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // 这里简单示例使用一个对话框来显示更新信息
            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.has_new_version)
                    .setMessage(result.getUpdateTips()) // 这里是版本更新信息
                    .setNegativeButton(R.string.update_now,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ximalaya.com/down?tag=web&client=android"));
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mContext.startActivity(intent);
                                    // ps：这里示例点击“马上升级”按钮之后简单地调用系统浏览器进行新版本的下载，
                                    // 但强烈建议开发者实现自己的下载管理流程，这样可以获得更好的用户体验。
                                    Intent intent = new Intent(MyReceiver.ACTION_UPDATE);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url", result.getUrl());
                                    intent.putExtras(bundle);
                                    mContext.sendBroadcast(intent);
                                }
                            })
                    .setPositiveButton(R.string.update_next_time,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
