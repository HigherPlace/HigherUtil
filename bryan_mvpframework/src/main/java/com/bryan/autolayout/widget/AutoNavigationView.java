package com.bryan.autolayout.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bryan.autolayout.utils.AutoLayoutHelper;
import com.google.android.material.navigation.NavigationView;


/**
 * Created by zwj on 2017/3/29.
 */

public class AutoNavigationView extends NavigationView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoNavigationView(Context context) {
        super(context);
    }

    public AutoNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public AutoFrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoFrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
