package com.app.base.utils.net.callback;


import com.bryan.common.utils.log.LogUtils;

import org.xutils.common.Callback;

/**
 * Created by zwj on 2016/12/9.
 */

public abstract class SimpleCommonCallback implements Callback.CommonCallback<String>{
    private static final String TAG = "SimpleCommonCallback";

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtils.i(TAG, "---> onError");
    }

    @Override
    public void onCancelled(CancelledException cex) {
        LogUtils.i(TAG, "---> onCancelled");
    }

    @Override
    public void onFinished() {
        LogUtils.i(TAG, "---> onFinished");
    }
}
