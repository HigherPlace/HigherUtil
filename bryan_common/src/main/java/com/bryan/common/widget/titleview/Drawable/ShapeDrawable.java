package com.bryan.common.widget.titleview.Drawable;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * Created by BinGe on 2017/9/26.
 * 返回按钮
 */

public abstract class ShapeDrawable extends Drawable {

    //画笔基础宽度
    protected int mPaintBaseWidth = 2;

    /**
     * 选中状态
     */
    private boolean mPressed = false;

    /**
     * Drawable宽高
     */
    private float mSize;

    /**
     * 分辨率比例
     */
    private float mScale = 1;


    public ShapeDrawable(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScale = dm.density;
        mSize = mScale * 36;
    }

    public float getDensityScale() {
        return mScale;
    }

    public void setColor(int color) {
        if (getPaint() != null) {
            getPaint().setColor(color);
        }
        invalidateSelf();
    }

    public abstract Paint getPaint();

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        getPaint().setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        getPaint().setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mSize;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mSize;
    }

    /**
     * 这里返回true，按钮在点击的时候才会有状态过来
     *
     * @return
     */
    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    protected boolean onStateChange(int[] state) {
        boolean pressed = false;
        for (int current : state) {
            if (current == android.R.attr.state_pressed) {
                pressed = true;
                break;
            }
        }
        mPressed = pressed;
        invalidateSelf();
        return true;
    }

    public boolean isPressed() {
        return mPressed;
    }

}
