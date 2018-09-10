package com.app.base.utils.net.callback;


import com.bryan.common.utils.log.LogUtils;

import org.xutils.common.Callback;

import java.io.File;

public class DownloadCallback implements Callback.CommonCallback<File>,
        Callback.ProgressCallback<File> {
    private static final String TAG = "DownloadCallback";

    @Override
    public void onWaiting() {
        LogUtils.i(TAG, " -------->  onWaiting");
    }

    @Override
    public void onStarted() {
        LogUtils.i(TAG, " -------->  onStarted");
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        LogUtils.i(TAG, "-------->  onLoading : total -->" + total
                + ";  current-->" + current + "; isDownloading"
                + isDownloading);
    }

    @Override
    public void onSuccess(File result) {
        LogUtils.i(TAG, " -------->  onSuccess");
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtils.i(TAG, " -------->  onError");
        ex.printStackTrace();
    }

    @Override
    public void onCancelled(CancelledException cex) {
        LogUtils.i(TAG, " -------->  onCancelled");
    }

    @Override
    public void onFinished() {
        LogUtils.i(TAG, " -------->  onFinished");
    }

}
