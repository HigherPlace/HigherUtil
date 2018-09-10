package com.app.base.module.LargeImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.app.base.module.base.view.BaseActivity;
import com.app.base.module.base.view.EmptyView;
import com.huichexing.base.R;
import com.huichexing.base.R2;
import com.huichexing.base.module.base.presenter.EmptyPresenter;
import com.huichexing.base.module.base.view.BaseActivity;
import com.huichexing.base.module.base.view.EmptyView;

import java.util.List;

import butterknife.BindView;

/**
 * 查看大图
 * Created by Administrator on 2017/3/22 0022.
 */
public class ImageViewActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    public static final String EXTRA_PATH = "UriList";
    public static final String EXTRA_POSITION = "Position";

    @BindView(R2.id.vp_image_view)
    ViewPager mVp;

    private List<String> uriList;//文件数据
    private int curPosition;
    private FragmentStatePagerAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_image_view;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        uriList = intent.getStringArrayListExtra(ImageViewActivity.EXTRA_PATH);
        curPosition = intent.getIntExtra(ImageViewActivity.EXTRA_POSITION, 0);
        if (uriList == null || uriList.size() == 0) {
            toast("数据错误");
            finish();
            return;
        }

        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                if (uriList == null || uriList.size() == 0) {
                    return 0;
                } else {
                    return uriList.size();
                }
            }

            @Override
            public Fragment getItem(int arg0) {
                return ImageViewFragment.newInstance(uriList.get(arg0));
            }
        };
        mVp.setAdapter(mAdapter);
        mVp.setOffscreenPageLimit(3);
        mVp.setCurrentItem(curPosition);
    }


    @Override
    public EmptyPresenter createPresenter() {
        return new EmptyPresenter(mContext);
    }
}
