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

public class AddDrawable extends ShapeDrawable {

    private Paint mPaint = new Paint();
    private float mRadius;

    public AddDrawable(Context context) {
        super(context);
        mRadius = getDensityScale() * 8;

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getDensityScale() * mPaintBaseWidth);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        canvas.drawColor(Color.RED);
        if (isPressed()) {
            mPaint.setAlpha(100);
        } else {
            mPaint.setAlpha(255);
        }
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        canvas.drawLine(x, y - mRadius, x, y + mRadius, mPaint);
        canvas.drawLine(x + mRadius, y, x - mRadius, y, mPaint);
    }

    @Override
    public Paint getPaint() {
        return mPaint;
    }
}
