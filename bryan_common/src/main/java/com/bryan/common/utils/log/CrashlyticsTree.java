package com.bryan.common.utils.log;

import android.util.Log;

import androidx.annotation.Nullable;

import timber.log.Timber;

public class CrashlyticsTree extends Timber.Tree {

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return;
        }

//        if (t == null && message != null) {
////            CrashReport.
////            Crashlytics.logException(new Exception(message));
//        } else if (t != null  && tag.contains("catch")) {
//            CrashReport.postCatchedException();
////            Crashlytics.logException(new Exception(message, t));
//        } else if (t != null) {
////            Crashlytics.logException(t);
//        }
        if (tag != null && tag.contains("catch") && t != null) {
            //把捕获到的异常上传到bugly
//            CrashReport.postCatchedException(t);
        }
        //可以将日志按照不同级别存储在文件/数据库等地方

    }
}
