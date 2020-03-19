package com.bryan.common.widget.titleview.Drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;


/**
 * Created by BinGe on 2017/9/26.
 * 菜单按钮
 */

public class ListDrawable extends ShapeDrawable {

    private Paint mPaint = new Paint();
    private float mRadius;

    public ListDrawable(Context context) {
        super(context);
        mRadius = getDensityScale() * 10;
        mPaint.setAntiAlias(true);
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
        float r = getDensityScale() * mPaintBaseWidth;
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        float padding = mRadius * 0.6f;
        float startX = x - mRadius / 2 - r * 2;

        canvas.drawLine(x - mRadius / 2, y - padding, x + mRadius, y - padding, mPaint);
        canvas.drawCircle(startX, y - padding, r, mPaint);
        canvas.drawLine(x - mRadius / 2, y, x + mRadius, y, mPaint);
        canvas.drawCircle(startX, y, r, mPaint);
        canvas.drawLine(x - mRadius / 2, y + padding, x + mRadius, y + padding, mPaint);
        canvas.drawCircle(startX, y + padding, r, mPaint);

    }

    @Override
    public Paint getPaint() {
        return mPaint;
    }
}
