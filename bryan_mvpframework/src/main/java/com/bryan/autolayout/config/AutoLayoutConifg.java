package com.bryan.autolayout.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.bryan.autolayout.utils.L;
import com.bryan.autolayout.utils.UIUtils;


public class AutoLayoutConifg {

    private static AutoLayoutConifg sIntance = new AutoLayoutConifg();


    private static final String KEY_DESIGN_WIDTH = "design_width";
    private static final String KEY_DESIGN_HEIGHT = "design_height";

    private int mScreenWidth;
    private int mScreenHeight;

    private int mDesignWidth;
    private int mDesignHeight;

    /*是否使用设备的物理尺寸，包括状态栏和导航栏*/
    private boolean useDeviceSize;

    private AutoLayoutConifg() {
    }

    public AutoLayoutConifg useDeviceSize() {
        useDeviceSize = true;
        return this;
    }

    public void checkParams() {
        if (mDesignHeight <= 0 || mDesignWidth <= 0) {
            throw new RuntimeException(
                    "you must set " + KEY_DESIGN_WIDTH + " and " + KEY_DESIGN_HEIGHT + "  in your manifest file.");
        }
    }

    public static AutoLayoutConifg getInstance() {
        return sIntance;
    }


    /**
     * 获取屏幕可用宽度
     *
     * @return
     */
    public int getScreenWidth() {
        return mScreenWidth;
    }

    /**
     * 获取屏幕可用高度
     *
     * @return
     */
    public int getScreenHeight() {
        return mScreenHeight;
    }

    /**
     * 获取基准的屏幕宽度
     *
     * @return
     */
    public int getDesignWidth() {
        return mDesignWidth;
    }

    /**
     * 获取基准的屏幕高度
     *
     * @return
     */
    public int getDesignHeight() {
        return mDesignHeight;
    }


    /**
     * 初始化设备信息
     *
     * @param context
     */
    public void init(Context context) {
        getMetaData(context);
        L.e("基准宽度：" + mDesignWidth + "基准高度：" + mDesignHeight);
        int[] screenSize;
        screenSize = UIUtils.getINSTANCE(context, useDeviceSize).getScreenSize();
        mScreenWidth = screenSize[0];
        mScreenHeight = screenSize[1];
        L.e("屏幕可用宽度：" + mScreenWidth + "屏幕可用高度：" + mScreenHeight);
    }

    /**
     * 获取在Manifest中设置的屏幕基准高度
     *
     * @param context
     */
    private void getMetaData(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(context
                    .getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                mDesignWidth = (Integer) applicationInfo.metaData.get(KEY_DESIGN_WIDTH);
                mDesignHeight = (Integer) applicationInfo.metaData.get(KEY_DESIGN_HEIGHT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(
                    "you must set " + KEY_DESIGN_WIDTH + " and " + KEY_DESIGN_HEIGHT + "  in your manifest file.", e);
        }
    }
}
