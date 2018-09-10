package com.bryan.common.widget.titleview.anim;

import android.view.animation.Interpolator;

/**
 * Created by LooooG on 2017/11/1.
 *
 */
public class CosInterpolator implements Interpolator {

    private float offset = 0;

    public CosInterpolator() {
    }

    public CosInterpolator(float offset) {
        this.offset = offset;
    }

    public float getInterpolation(float input) {
        return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + offset;
    }
}
