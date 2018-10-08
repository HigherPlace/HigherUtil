package com.bryan.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;

import java.io.File;

/**
 * Created by bryan on 2018/3/22 0022.
 */

public class AppCommonUtils {

    /**
     * 将View保存成一张图片，保存到SDCard
     *
     * @param context
     * @param view
     */
    public static void viewSaveToImage(Context context, View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);
        ACache cache = ACache.get(new File(getSaveBmpPath(context)));
        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);
        cache.put("qrcode.png", cachebmp);
        view.destroyDrawingCache();
    }

    /**
     * 根据View生成图片
     *
     * @param v
     * @return
     */
    public static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    /**
     * 获取保存图片的目录，用于给用户查看
     */
    public static String getSaveBmpPath(Context context) {
        String cachePath = null;
        boolean isSuccess = false;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "二维码" + File.separator + "图片");
            if (!file.exists()) {
                isSuccess = file.mkdirs();
            } else {
                isSuccess = true;
            }
            if (isSuccess) {
                return cachePath = file.getAbsolutePath();
            } else {
                return cachePath = context.getApplicationContext().getCacheDir().getPath();
            }
        } else {
            return cachePath = context.getApplicationContext().getCacheDir().getPath();
        }
    }

}
