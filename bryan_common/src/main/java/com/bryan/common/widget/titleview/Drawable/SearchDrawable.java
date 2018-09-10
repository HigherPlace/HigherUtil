package com.bryan.common.widget.titleview.Drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;


/**
 * Created by BinGe on 2017/9/26.
 * 菜单按钮
 */

public class SearchDrawable extends ShapeDrawable {

    private Paint mPaint = new Paint();
    private float mRadius;

    public SearchDrawable(Context context) {
        super(context);
        mRadius = getDensityScale() * 8;

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getDensityScale() * mPaintBaseWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        canvas.drawColor(Color.RED);
        if (isPressed()) {
            mPaint.setAlpha(100);
        } else {
            mPaint.setAlpha(255);
        }
        float r = getDensityScale() * 8;
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        canvas.drawCircle(x, y, r, mPaint);
        canvas.drawLine(x + r * 0.64f, y + r * 0.64f, x + r * 1.2f, y + r * 1.2f, mPaint);
    }

    @Override
    public Paint getPaint() {
        return mPaint;
    }
}
