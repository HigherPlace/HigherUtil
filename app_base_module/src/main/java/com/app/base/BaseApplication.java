package com.app.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;
import androidx.multidex.MultiDexApplication;

import com.app.base.utils.user.AccountBean;
import com.app.base.utils.CrashHandler;
import com.app.base.utils.net.bean.RequestBean;
import com.app.base.utils.net.constant.Constant;
import com.app.base.utils.user.UserUtil;
import com.bryan.common.utils.SystemUtil;
import com.bryan.common.utils.ToastMgr;
import com.orhanobut.logger.LogBuilder;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.zxy.tiny.Tiny;

import org.xutils.x;

/**
 * Created by bryan on 2017/11/14 0014.
 */

public class BaseApplication extends MultiDexApplication {

    protected static BaseApplication gApp;

    private static String token;

    public int activityCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            gApp = (BaseApplication) getApplicationContext();
            //init toast
            ToastMgr.init(getApplicationContext());
            //init Tiny
            Tiny.getInstance().init(this);
            //init log
            Logger.initialize(
                    LogBuilder.create()
//                          .logPrintStyle(new XLogStyle())
                            .showMethodLink(false)
                            .showThreadInfo(false)
                            .tagPrefix("bryan")
//                          .globalTag("globalTag")
                            .methodOffset(1)
//                            .logPriority(BuildConfig.IsDebug ? Log.VERBOSE : Log.ASSERT)
                            .logPriority(Log.VERBOSE)
                            .build()

            );

//            if (BuildConfig.IsDebug) {
//                Logger.plant(new CrashlyticsTree());
//            }

            //注册全局异常捕获,异常处理类的初始化要放在Bugly初始化之前
            CrashHandler.getInstance().init(this);
            //初始化bugly
            initBugly();

            //注册XUtils
            x.Ext.init(this);
            x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
            //设置全局的参数
            RequestBean.callbackUnlogin = true;
            setGlobalParamAndHeader();
            //注册Activity的生命周期
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle bundle) {

                }

                @Override
                public void onActivityStarted(Activity activity) {
                    activityCount++;
                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {
                    activityCount--;
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            });
        }
    }

    public static BaseApplication getGlobalContext() {
        return gApp;
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 判断是否已经登录过
     *
     * @return true - 已经登录过； false - 未登录过
     */
    public static boolean isLogin() {
        AccountBean bean = UserUtil.getInstance().getUser();
        return bean != null;
    }

    public static AccountBean getCurUser() {
        return UserUtil.getInstance().getUser();
    }

    /**
     * 设置全局需要传给后台的参数和header
     * 初始化跟登陆成功之后都需要进行此设置
     */
    public static void setGlobalParamAndHeader() {
        RequestBean.clearGlobalMap();
        // 添加token
        if (!TextUtils.isEmpty(getToken())) {
            RequestBean.addGlobalHead(Constant.TOKEN, getToken());
        }
        // 添加设备类型
//        RequestBean.addGlobalHead("deviceTag", Constant.DeviceTag);
        RequestBean.addGlobalHead("versionCode", SystemUtil.getPackageInfo(BaseApplication.getGlobalContext()).versionCode + "");
//        RequestBean.addGlobalHead("appId", com.ctx.base.constant.Constant.AppId);
    }

    public static String getToken() {
        if (token == null) {
            AccountBean user = UserUtil.getInstance().getUser();
            if (user != null) {
                token = user.getToken();
            }
        }
        return token;
    }

    public static void setToken(String pToken) {
        token = pToken;
        setGlobalParamAndHeader();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level > ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            //todo 优化内存，避免被杀掉
        }
    }

    public static void init() {
        token = null;
    }

    private void initBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getGlobalContext());
//        strategy.setAppChannel("myChannel");  //设置渠道
//        strategy.setAppVersion("1.0.1");      //App的版本
//        strategy.setAppPackageName("com.tencent.xx");  //App的包名
        //上报前添加一些参数
//        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
//            /**
//             * Crash处理.
//             *
//             * @param crashType 错误类型：CRASHTYPE_JAVA，CRASHTYPE_NATIVE，CRASHTYPE_U3D ,CRASHTYPE_ANR
//             * @param errorType 错误的类型名
//             * @param errorMessage 错误的消息
//             * @param errorStack 错误的堆栈
//             * @return 返回额外的自定义信息上报
//             */
//            public Map<String, String> onCrashHandleStart(int crashType, String errorType,
//                                                          String errorMessage, String errorStack) {
//                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
//                if (isLogin()) {
//                    map.put("account", UserUtil.getInstance().getUser().getPhone());
//                }
//                return map;
//            }
//
//            /**
//             * Crash处理.
//             *
//             * @param crashType 错误类型：CRASHTYPE_JAVA，CRASHTYPE_NATIVE，CRASHTYPE_U3D ,CRASHTYPE_ANR
//             * @param errorType 错误的类型名
//             * @param errorMessage 错误的消息
//             * @param errorStack 错误的堆栈
//             * @return byte[] 额外的2进制内容进行上报
//             */
//            @Override
//            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType,
//                                                           String errorMessage, String errorStack) {
//                try {
//                    return "Extra data.".getBytes("UTF-8");
//                } catch (Exception e) {
//                    return null;
//                }
//            }
//
//        });

//        CrashReport.putUserData();
        /**
         第三个参数为SDK调试模式开关，调试模式的行为特性如下：
         输出详细的Bugly SDK的Log；
         每一条Crash都会被立即上报；
         自定义日志将会在Logcat中输出。
         建议在测试阶段建议设置成true，发布时设置为false。
         */
        if (BuildConfig.IsDebug) {
            //如果是测试版就设置成测试设备
            CrashReport.setIsDevelopmentDevice(this, BuildConfig.IsDebug);
        }
        CrashReport.initCrashReport(getApplicationContext(), "16359df4b911", BuildConfig.IsDebug);
//        CrashReport.testJavaCrash();
//        CrashReport.testANRCrash();
//        CrashReport.testNativeCrash();
//        CrashReport.postCatchedException(new Throwable("hahah"));
    }

    public static ActivityOptionsCompat getTransactionCompat(View v) {
        return ActivityOptionsCompat.
                makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
    }

}
