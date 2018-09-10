package com.bryan.common.widget.titleview.anim;

import android.animation.ValueAnimator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by BinGe on 2017/10/27.
 */

public class ValueAnimation {

    ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

    public ValueAnimation(long duration) {
        animator.setDuration(duration);
        setInterpolator(new LinearInterpolator());
    }

    public void setRepeatCount(int count) {
        animator.setRepeatCount(count);
    }

    public void start() {
        animator.start();
    }

    public void setInterpolator(Interpolator value) {
        animator.setInterpolator(value);
    }

    public void addUpdateListener(ValueAnimator.AnimatorListener listener) {
        animator.addListener(listener);
    }

    public float getValue() {
        return (float) animator.getAnimatedValue();
    }

}
