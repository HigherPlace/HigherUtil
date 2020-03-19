package com.bryan.common.widget.titleview.Drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.bryan.common.widget.titleview.anim.CosInterpolator;
import com.bryan.common.widget.titleview.anim.ValueAnimation;


/**
 * Created by LooooG on 2017/11/01.
 * SmileLoading效果
 */
public class SmileLoadingDrawable extends ShapeDrawable {

    private RectF rect = new RectF();
    private ValueAnimation animator;
    private Paint mPaint = new Paint();

    private boolean isStart;
    private boolean isShowLeftEye;
    private boolean isShowRightEye;
    private float leftEyeAngle;     // 左眼角度
    private float rightEyeAngle;    // 右眼角度
    private float startAngle;       // 开始角度
    private float sweepAngle;       // 弧度
    private float totalAngle;       // 动画总角度


    public SmileLoadingDrawable(Context context) {
        super(context);
        float width = getDensityScale() * 25;
        rect.set(0, 0, width, width);

        leftEyeAngle = 240;
        rightEyeAngle = 300;
        totalAngle = 719;

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getDensityScale() * 3);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

        animator = new ValueAnimation(1500);
        animator.setInterpolator(new CosInterpolator(0.5f));
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float width = Math.min(Math.min(canvas.getWidth(), canvas.getHeight()) - mPaint.getStrokeWidth(), rect.width());
        rect.set(0, 0, width, width);
        rect.offsetTo(canvas.getWidth() / 2 - rect.width() / 2, canvas.getHeight() / 2 - rect.height() / 2);

        // 左眼240，右眼300, 动画开始位置185
        if (isStart) {
            startAngle = 90 + animator.getValue() * totalAngle;
            if (startAngle > 359) {
                sweepAngle = startAngle - 359;
                if (sweepAngle >= 180) {
                    if (startAngle - 359 >= 270) {
                        // 4阶段
                        sweepAngle = 180 - (startAngle - 359 - 270);
                        startAngle = startAngle - 359 - sweepAngle;
                    } else {
                        // 3阶段
                        sweepAngle = 180;
                        startAngle = startAngle - 359 - sweepAngle;
                    }
                    isShowLeftEye = startAngle + sweepAngle < leftEyeAngle;
                    isShowRightEye = startAngle + sweepAngle < rightEyeAngle;
                } else {
                    // 2阶段
                    startAngle = 359;
                }
            } else {
                // 1阶段
                sweepAngle = 1;
                isShowLeftEye = startAngle > leftEyeAngle;
                isShowRightEye = startAngle > rightEyeAngle;
            }
            canvas.drawArc(rect, startAngle, sweepAngle, false, mPaint);
        } else {
            canvas.drawArc(rect, 355, 190, false, mPaint);
        }

        if (isShowLeftEye || !isStart) {
            canvas.drawArc(rect, leftEyeAngle, 1, false, mPaint);
        }
        if (isShowRightEye || !isStart) {
            canvas.drawArc(rect, rightEyeAngle, 1, false, mPaint);
        }

        invalidateSelf();
    }


    @Override
    public Paint getPaint() {
        return mPaint;
    }

    public void start() {
        setVisible(true, true);
        isStart = true;
        animator.start();
    }

    public void stop() {
        setVisible(true, true);
        isStart = false;
    }

    public void hide() {
        setVisible(false, false);
    }

}
