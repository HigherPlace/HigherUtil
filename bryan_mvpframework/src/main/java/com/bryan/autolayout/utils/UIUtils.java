package com.bryan.autolayout.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 使用基准值生成设备上需要的宽高值
 * Created by bryan on 2017/11/28 0028.
 */
public class UIUtils {

    private static UIUtils INSTANCE;

    public static UIUtils getINSTANCE(Context context, boolean useDeviceSize) {
        if (INSTANCE == null) {
            INSTANCE = new UIUtils(context, useDeviceSize);
        }
        return INSTANCE;
    }

    //基准宽高
    public static final int STANDARD_WIDTH = 1080;//基准宽度
    public static final int STANDARD_HEIGHT = 1872;//基准高度，1920-状态栏高度（默认48px）
    //dimen文件路径
    private static final String DIMEN_CLASS = "com.android.internal.R$dimen";
    //状态栏name
    private static final String DIMEN_FIELD = "system_bar_height";

    //实际设备的分辨率
    public float displayMetricsWidth;
    public float displayMetricsHeight;


    //初始化
    private UIUtils(Context context, boolean useDeviceSize) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //存放真实宽高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (displayMetricsWidth == 0.0f || displayMetricsHeight == 0.0f) {
            //保存屏幕真实高度
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            //获取到状态栏的高度
            int systemBarHeight = getSystemBarHeight(context);
            /*默认获取到的高度不算状态栏，也不算导航栏的高度*/
            int navigationBarHeight = getNavigationBarHeight(context);
            Log.i("TAG", "systemBarHeight :" + systemBarHeight);
            //处理真实的宽高问题，要适配所有的机器需要拿到真实的宽高
            //有横屏和竖屏区分
            if (!useDeviceSize) {
                if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                    //横屏
                    this.displayMetricsWidth = (float) displayMetrics.heightPixels;
                    this.displayMetricsHeight = (float) displayMetrics.widthPixels - systemBarHeight;
                } else {
                    //竖屏
                    this.displayMetricsWidth = (float) displayMetrics.widthPixels;
                    this.displayMetricsHeight = (float) displayMetrics.heightPixels - systemBarHeight;
                }
            } else {
                if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                    //横屏
                    this.displayMetricsWidth = (float) displayMetrics.heightPixels + navigationBarHeight;
                    ;
                    this.displayMetricsHeight = (float) displayMetrics.widthPixels;
                } else {
                    //竖屏
                    this.displayMetricsWidth = (float) displayMetrics.widthPixels;
                    this.displayMetricsHeight = (float) displayMetrics.heightPixels + navigationBarHeight;
                }
            }

        }
    }

    public int[] getScreenSize() {
        int[] size = new int[2];
        size[0] = (int) displayMetricsWidth;
        size[1] = (int) displayMetricsHeight;
        return size;
    }

    /**
     * 获取系统状态栏高度
     *
     * @param context
     * @return
     */
    private int getSystemBarHeight(Context context) {
        return getValue(context, DIMEN_CLASS, DIMEN_FIELD, 48);
    }

    /**
     * 获取导航栏高度
     *
     * @param context context
     * @return 导航栏高度
     */
    public int getNavigationBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * @param context
     * @param dimenClass 安卓源码存放dimen的类
     * @param dimenField 状态栏高度的信息
     * @param defValue   默认值
     * @return
     */
    private int getValue(Context context, String dimenClass, String dimenField, int defValue) {
        try {
            Class cls = Class.forName(dimenClass);
            Object obj = cls.newInstance();
            Field field = cls.getField(dimenField);
            //获取到资源id
            int resId = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelOffset(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    /*******************************暂时用不上***************************************/

    //开始获取缩放以后的结果（以1920*1080的基准进行缩放）
    public float getWidth(float width) {
        return width * displayMetricsWidth / STANDARD_WIDTH;
    }

    public float getHeight(float height) {
        return height * displayMetricsHeight / STANDARD_HEIGHT;
    }

    //开始获取缩放以后的结果（以1920*1080的基准进行缩放）
    public int getWidth(int width) {
        return (int) (width * displayMetricsWidth / STANDARD_WIDTH);
    }

    public int getHeight(int height) {
        return (int) (height * displayMetricsHeight / STANDARD_HEIGHT);
    }


}
