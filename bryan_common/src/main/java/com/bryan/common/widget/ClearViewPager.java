package com.bryan.common.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 没有动画效果，不能滑动的ViewPager
 * Created by bryan on 2017/11/17 0017.
 */
public class ClearViewPager extends ViewPager {

    private boolean noScroll = false;//默认false，可滚动

    public ClearViewPager(Context context) {
        super(context);
    }

    public ClearViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public boolean isNoScroll() {
        return noScroll;
    }

    //设置不可滚动，setNoScroll(true)
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !noScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !noScroll && super.onInterceptTouchEvent(event);
    }

    //去除页面切换时的滑动翻页效果
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
