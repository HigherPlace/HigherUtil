package com.sunfusheng.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.sunfusheng.DownBmpResult;

import java.io.File;

/**
 * 图片夹在辅助工具类
 * Created by bryan on 2018/10/8.
 */
public class ImageLoadUtil {

    /**
     * 下载图片
     */
    public static void downBmp(String url, final Context context, final DownBmpResult listener) {
        //submit只会下载图片，而不会对图片进行加载。当图片下载完成之后，我们可以得到图片的存储路径，以便后续进行操作。
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //需要使用ApplicationContext，防止主线程销毁的情况
                    Context _context = context.getApplicationContext();
                    FutureTarget<File> target = Glide.with(_context)
                            .asFile()
                            .load(url)
                            .submit();
                    final File imageFile = target.get();
                    if (listener != null) {
                        listener.onResult(true, imageFile.getAbsolutePath());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onResult(false, null);
                    }
                }
            }
        }).start();

    }
}
