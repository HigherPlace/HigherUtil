/**
 *
 */
package com.bryan.common.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 只展示最后一个Toast
 */
public class ToastMgr {

    private static Toast mToast;

    private ToastMgr() {
    }

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    public static void init(Context _context) {
        View v = Toast.makeText(_context, "", Toast.LENGTH_SHORT).getView();
        init(_context, v);
    }

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    public static void init(Context _context, View view) {
        mToast = new Toast(_context);
        mToast.setView(view);
    }

    /**
     * 设置Toast背景
     */
    public static void setBackgroundView(View view) {
        checkInit();
        mToast.setView(view);
    }

    public static void show(CharSequence text, int duration) {
        checkInit();
        mToast.setText(text);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void show(int resid, int duration) {
        checkInit();
        mToast.setText(resid);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    private static void checkInit() {
        if (mToast == null) {
            throw new IllegalStateException("ToastMgr is not initialized, please call init once before you call this method");
        }
    }
}