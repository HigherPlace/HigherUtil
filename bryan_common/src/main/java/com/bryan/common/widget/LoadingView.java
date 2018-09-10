package com.bryan.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bryan.common.widget.titleview.Drawable.LoadingDrawable;


/**
 * Created by BinGe on 2017/10/11.
 */
public class LoadingView extends View {

    private LoadingDrawable mLoadingDrawable;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mLoadingDrawable = new LoadingDrawable(getContext());
        Drawable background = getBackground();
        if (background != null && background instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) background;
            int color = cd.getColor();
            mLoadingDrawable.setColor(color);
        }
        setBackground(mLoadingDrawable);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
