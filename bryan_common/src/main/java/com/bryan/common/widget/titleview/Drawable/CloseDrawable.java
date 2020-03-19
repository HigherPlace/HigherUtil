package com.bryan.common.widget.titleview.Drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

/**
 * Created by BinGe on 2017/9/26.
 * 返回按钮
 */

public class CloseDrawable extends ShapeDrawable {

    private Paint mPaint = new Paint();
    private float mRadius;

    public CloseDrawable(Context context) {
        super(context);
        mRadius = getDensityScale() * 8;

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getDensityScale() * mPaintBaseWidth);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (isPressed()) {
            mPaint.setAlpha(100);
        } else {
            mPaint.setAlpha(255);
        }

        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        canvas.drawLine(x - mRadius, y - mRadius, x + mRadius, y + mRadius, mPaint);
        canvas.drawLine(x + mRadius, y - mRadius, x - mRadius, y + mRadius, mPaint);
    }

    @Override
    public Paint getPaint() {
        return mPaint;
    }

}
