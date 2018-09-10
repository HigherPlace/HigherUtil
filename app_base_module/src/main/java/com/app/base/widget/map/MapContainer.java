package com.app.base.widget.map;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by bryan on 2018/3/21 0021.
 */

public class MapContainer extends RelativeLayout {
    private NestedScrollView scrollView;
    private SmartRefreshLayout otherViewGroup;

    public MapContainer(Context context) {
        super(context);
    }

    public MapContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollView(NestedScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public void setOtherViewGroup(SmartRefreshLayout viewGroup) {
        otherViewGroup = viewGroup;
    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (scrollView != null) {
                scrollView.requestDisallowInterceptTouchEvent(false);
            }
            if (otherViewGroup != null) {
                otherViewGroup.requestDisallowInterceptTouchEvent(false);
            }
        } else {
            if (scrollView != null) {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
            if (otherViewGroup != null) {
                otherViewGroup.requestDisallowInterceptTouchEvent(true);
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
