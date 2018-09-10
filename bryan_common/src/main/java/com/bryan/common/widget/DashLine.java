package com.bryan.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by bryan on 2017/12/25 0025.
 */

public class DashLine extends View {
    private Paint paintLine;
    private int offset = 10;
    private int dashHeight = 10;

    public DashLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintLine = new Paint();
        paintLine.setColor(0XFFFF0000);
        paintLine.setStrokeWidth(4);
    }

    public void setColor(int color) {
        paintLine.setColor(color);
        invalidate();
    }

    private int dp2px(int dp) {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return (int) (dm.density * dp);
    }

    /**
     * 设置虚线高度
     *
     * @param offset 单位（unit） ： dp
     */
    public void setOffSetHeight(int offset) {
        this.offset = dp2px(offset);
        invalidate();
    }

    /**
     * 设置实线高度
     *
     * @param height 单位（unit） ： dp
     */
    public void setHeight(int height) {
        this.dashHeight = dp2px(height);
        invalidate();
    }

    /**
     * 设置画笔粗细
     *
     * @param width 单位（unit） ： dp
     */
    public void setPaintWidth(int width) {

        paintLine.setStrokeWidth(dp2px(width));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int start = 0;
        while (start < height) {
            canvas.drawLine(width / 2, start, width / 2, start + dashHeight, paintLine);
            start += (offset + dashHeight);
        }
    }
}