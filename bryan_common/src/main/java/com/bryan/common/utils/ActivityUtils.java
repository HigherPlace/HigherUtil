package com.bryan.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bryan.common.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by BinGe on 2017/9/18.
 * 针对activity的一样公共工具方法
 */

public class ActivityUtils {

    /**
     * 设置通知栏字体颜色
     *
     * @param a
     * @param isFontColorDark 为false时为白色，true为深色
     */
    public static boolean setDarkStatusIcon(Activity a, boolean isFontColorDark) {
        if (setStatusBarLightModeWithAndroidM(a, isFontColorDark)) {
            return true;
        } else if (setStatusBarLightModeWithFlyme(a, isFontColorDark)) {
            return true;
        } else return setStatusBarLightModeWithMIUI(a, isFontColorDark);
    }

    /**
     * android6.0手机设置通知栏字体颜色
     *
     * @param activity
     * @param isFontColorDark
     * @return
     */
    private static boolean setStatusBarLightModeWithAndroidM(Activity activity, boolean isFontColorDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (isFontColorDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
                return true;
            }
        }
        return false;
    }

    /**
     * 小米手机设置通知栏字体颜色
     *
     * @param activity
     * @param isFontColorDark
     * @return
     */
    private static boolean setStatusBarLightModeWithMIUI(Activity activity, boolean isFontColorDark) {
        Window window = activity.getWindow();
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (isFontColorDark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 魅族手机设置通知栏字体颜色
     *
     * @param activity
     * @param isFontColorDark
     * @return
     */
    private static boolean setStatusBarLightModeWithFlyme(Activity activity, boolean isFontColorDark) {
        Window window = activity.getWindow();
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (isFontColorDark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 设置window的通知栏颜色，可以是activity也可以是dialog
     *
     * @param window
     * @param color
     */
    public static void setWindowStatusBarColor(Window window, int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开一个activity，左右动画
     */
    public static void pushActivity(Context context, Class<?> activityClass) {
        pushActivity(context, new Intent(context, activityClass));
    }

    public static void pushActivityForResult(Context context, Class<?> activityClass, int requestCode) {
        startActivityForResult(context, new Intent(context, activityClass),
                R.anim.activity_push_open_in, R.anim.activity_push_open_out, requestCode);
    }

    public static void pushActivity(Context context, Intent intent) {
        startActivity(context, intent, R.anim.activity_push_open_in, R.anim.activity_push_open_out);
    }

    public static void pullActivity(Context context) {
        closeActivity(context, R.anim.activity_push_close_in, R.anim.activity_push_close_out);
    }

    /**
     * 打开一个activity，从下到上动画
     */
    public static void presentationActivity(Context context, Class<?> activityClass) {
        presentationActivity(context, new Intent(context, activityClass));
    }

    public static void presentationActivity(Context context, Intent intent) {
        startActivity(context, intent, R.anim.activity_presentation_open_in, R.anim.activity_presentation_open_out);
    }

    public static void dismissActivity(Context context) {
        closeActivity(context, R.anim.activity_presentation_close_in, R.anim.activity_presentation_close_out);
    }

    private static void startActivity(Context context, Intent intent, int enter, int close) {
        context.startActivity(intent);
        if (context instanceof Activity) {
            Activity a = (Activity) context;
            a.overridePendingTransition(enter, close);
        }
    }

    private static void startActivityForResult(Context context, Intent intent, int enter, int close, int reqCode) {
        if (context instanceof Activity) {
            Activity a = (Activity) context;
            a.startActivityForResult(intent, reqCode);
            a.overridePendingTransition(enter, close);
        }
    }

    private static void closeActivity(Context context, int enter, int close) {
        if (context instanceof Activity) {
            Activity a = (Activity) context;
            a.finish();
            a.overridePendingTransition(enter, close);
        }
    }
}

