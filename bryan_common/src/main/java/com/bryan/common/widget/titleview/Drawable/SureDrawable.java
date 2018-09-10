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

public class SureDrawable extends ShapeDrawable {

    private Paint mPaint = new Paint();
    private float mRadius;

    public SureDrawable(Context context) {
        super(context);
        mRadius = getDensityScale() * 8;

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getDensityScale() * mPaintBaseWidth);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//                canvas.drawColor(Color.RED);
        if (isPressed()) {
            mPaint.setAlpha(100);
        } else {
            mPaint.setAlpha(255);
        }
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        int paddingX = 0;

        float[] p1 = new float[]{x - (2 * mRadius) / 3, y + mRadius * 0.1f};
        float[] p2 = new float[]{x, y + mRadius};
        float[] p3 = new float[]{x + mRadius, y - mRadius};

        canvas.drawLine(p1[0] + paddingX, p1[1], p2[0] + paddingX, p2[1], mPaint);
        canvas.drawLine(p2[0] + paddingX, p2[1], p3[0] + paddingX, p3[1], mPaint);
    }

    @Override
    public Paint getPaint() {
        return mPaint;
    }
}
