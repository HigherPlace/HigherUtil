package com.bryan.common.widget.titleview.Drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.bryan.common.widget.titleview.anim.ValueAnimation;


/**
 * Created by BinGe on 2017/9/26.
 * loading的背景效果
 */

public class LoadingDrawable extends ShapeDrawable {

    private RectF rect = new RectF();
    private ValueAnimation animator;
    private Paint mPaint = new Paint();

    public LoadingDrawable(Context context) {
        super(context);
        float width = getDensityScale() * 36;
        rect.set(0, 0, width, width);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getDensityScale() * 1);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        animator = new ValueAnimation(1000);
        animator.setRepeatCount(-1);
        animator.start();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float r1 = Math.min(canvas.getHeight(), canvas.getHeight()) - mPaint.getStrokeWidth();
        float r2 = Math.min(r1, rect.width());
        rect.set(0, 0, r2, r2);
        rect.offsetTo(canvas.getWidth() / 2 - rect.width() / 2, canvas.getHeight() / 2 - rect.height() / 2);
        canvas.drawArc(rect, animator.getValue() * 359, 320, false, mPaint);
        invalidateSelf();
    }


    @Override
    public Paint getPaint() {
        return mPaint;
    }

}
