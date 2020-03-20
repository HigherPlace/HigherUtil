package com.bryan.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bryan.autolayout.widget.AutoCardView;
import com.bryan.autolayout.widget.AutoCoordinatorLayout;
import com.bryan.autolayout.widget.AutoFrameLayout;
import com.bryan.autolayout.widget.AutoLinearLayout;
import com.bryan.autolayout.widget.AutoRelativeLayout;

/**
 * 如果需要自适应布局，需要在子类中重写isNeedAutoLayout方法，并且返回true
 */
public class AutoLayoutActivity extends AppCompatActivity {
    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    private static final String LAYOUT_CARDVIEW = "CardView";
    private static final String LAYOUT_COORDINATORLAYOUT = "CoordinatorLayout";
    private static final String LAYOUT_DRAWERLAYOUT = "DrawerLayout";

    protected boolean isNeedAutoLayout() {
        return false;
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if (!isNeedAutoLayout()) {
            return super.onCreateView(name, context, attrs);
        } else {
            View view = null;
            if (name.equals(LAYOUT_FRAMELAYOUT)) {
                view = new AutoFrameLayout(context, attrs);
            }

            if (name.equals(LAYOUT_LINEARLAYOUT)) {
                view = new AutoLinearLayout(context, attrs);
            }

            if (name.equals(LAYOUT_RELATIVELAYOUT)) {
                view = new AutoRelativeLayout(context, attrs);
            }

            if (name.equals(LAYOUT_COORDINATORLAYOUT)) {
                view = new AutoCoordinatorLayout(context, attrs);
            }

            if (name.equals(LAYOUT_CARDVIEW)) {
                view = new AutoCardView(context, attrs);
            }

            if (name.equals(LAYOUT_DRAWERLAYOUT)) {
                view = new DrawerLayout(context, attrs);
            }
            if (view != null) return view;
            return super.onCreateView(name, context, attrs);
        }

    }


}
