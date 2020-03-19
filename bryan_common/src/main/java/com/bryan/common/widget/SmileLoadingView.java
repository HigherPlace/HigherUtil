package com.bryan.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.bryan.common.widget.titleview.Drawable.SmileLoadingDrawable;


/**
 * Created by LooooG on 2017/11/01.
 * SmileLoadView
 */
public class SmileLoadingView extends View {

    private SmileLoadingDrawable mSmileLoadingDrawable;

    public SmileLoadingView(Context context) {
        this(context, null);
    }

    public SmileLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mSmileLoadingDrawable = new SmileLoadingDrawable(getContext());
        Drawable background = getBackground();
        if (background != null && background instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) background;
            int color = cd.getColor();
            mSmileLoadingDrawable.setColor(color);
        }
        setBackground(mSmileLoadingDrawable);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        mSmileLoadingDrawable.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void start() {
        mSmileLoadingDrawable.start();
    }

    public void stop() {
        mSmileLoadingDrawable.stop();
    }

    public void hide() {
        mSmileLoadingDrawable.hide();
    }
}
