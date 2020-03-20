package com.bryan.autolayout.utils;

import android.util.TypedValue;

/**
 * Created by zhy on 16/3/3.
 */
public class DimenUtils {


    private static int getComplexUnit(int data) {
        return TypedValue.COMPLEX_UNIT_MASK & (data >> TypedValue.COMPLEX_UNIT_SHIFT);
    }

    /**
     * 判断传进来的单位是不是px
     */
    public static boolean isPxVal(TypedValue val) {
        if (val != null && val.type == TypedValue.TYPE_DIMENSION &&
                getComplexUnit(val.data) == TypedValue.COMPLEX_UNIT_PX) {
            return true;
        }
        return false;
    }
}
