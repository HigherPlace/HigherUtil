package com.app.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.base.R;
import com.app.base.entity.ImageEntity;
import com.bryan.common.utils.image.ImageBuilder;

import java.util.List;

/**
 * Created by bryan on 2018/3/2 0002.
 */

public class ImageViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ImageEntity> entities;

    public ImageViewPagerAdapter(Context context, List<ImageEntity> entities) {
        this.context = context;
        this.entities = entities;
    }

    @Override
    public int getCount() {
        return entities == null ? 0 : entities.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //设置每一页显示的内容
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        //ImageView设置图片
        new ImageBuilder(context, imageView, entities.get(position).getUrl(), ImageBuilder.LoadMode.URL)
                .setDefaultImageId(R.drawable.ic_default_user)
                .build();
        container.addView(imageView); // 添加到ViewPager容器
        return imageView;// 返回填充的View对象
    }

    //销毁条目对象
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
