package com.bryan.common.utils.log;


import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;


/**
 * log打印辅助类，方便统一管理log，对log的打印进行开关
 */
public class LogUtils {

    public static void v(String msg) {
        Logger.t(getClassName()).v(msg);
    }

    public static void v(String tag, String msg) {
        Logger.t(tag).v(msg);
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    public static void d(String msg) {
        Logger.t(getClassName()).d(msg);
    }

    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void i(String msg) {
        Logger.t(getClassName()).i(msg);
    }

    public static void w(String msg) {
        Logger.t(getClassName()).w(msg);
    }

    public static void w(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        Logger.t(tag).e(throwable, msg);
    }

    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }

    public static void e(String msg, Throwable throwable) {
        Logger.t(getClassName()).e(throwable, msg);
    }

    public static void e(String msg) {
        Logger.t(getClassName()).e(msg);
    }

    public static void sysout(String msg) {
        Logger.t(getClassName()).wtf(msg);
    }

    public static void obj(Object obj) {
        Logger.object(obj);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void catchError(String msg, Throwable throwable) {
        Logger.t("catch-" + getClassName()).e(throwable, msg);
        CrashReport.postCatchedException(throwable);  // bugly会将这个throwable上报
    }

    public static void catchError(String tag, String msg, Throwable throwable) {
        Logger.t("catch-" + tag).e(throwable, msg);
//        FileUtils.writeContent2File(BaseApplication.getGlobalContext(), "error", "cache_error", msg + "-----" + (throwable == null ? "" : throwable.getMessage()), true);
        //捕获到的异常上传到bugly
        CrashReport.postCatchedException(throwable);  // bugly会将这个throwable上报
    }

    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result += thisMethodStack.getClassName() + "."; //  当前的类名（全名）
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

    private static String getClassName() {
        // 这里的数组的index，即2，是根据你工具类的层级取的值，可根据需求改变
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }
}
