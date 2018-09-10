package com.bryan.common.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.bryan.common.widget.titleview.Drawable.AddDrawable;


/**
 * Created by BinGe on 2017/10/16.
 */

public class AddView extends AppCompatTextView {

    public AddView(Context context) {
        this(context, null);
    }

    public AddView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) background;
            AddDrawable newBackground = new AddDrawable(context);
            newBackground.setColor(cd.getColor());
            setBackground(newBackground);
        } else {
            setBackground(new AddDrawable(context));
        }
    }
}
