package com.app.base.module.LargeImageView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.app.base.R;
import com.app.base.R2;
import com.app.base.module.base.view.BaseFragment;
import com.bryan.common.utils.image.ImageBuilder;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageViewFragment extends BaseFragment {

    public static final String EXTRA_PATH = "path";
    public static final String EXTRA_DATA_TYPE = "dataType";

    @BindView(R2.id.iv_picture)
    TouchImageView touchIv;
    private String mPath;//图片地址

    public static Fragment newInstance(String path) {
        Bundle bundle = new Bundle();
        bundle.putString(ImageViewFragment.EXTRA_PATH, path);
        ImageViewFragment fragment = new ImageViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_image_view;
    }

    @Override
    protected void initData() {
        mPath = getArguments().getString(ImageViewFragment.EXTRA_PATH);
    }


    @Override
    public void onResume() {
        super.onResume();
        // 加载图片
        if (!TextUtils.isEmpty(mPath)) {
            loadUrlBmp();
        } else {
            touchIv.setImageResource(R.drawable.ic_rectangle_picture_default);
        }
    }

    @OnClick(R2.id.ll_content)
    void clickWindow() {
        getActivity().finish();
    }

    @OnClick(R2.id.iv_picture)
    void clickImageView() {
        getActivity().finish();
    }

    /**
     * 加载Url图片
     */
    private void loadUrlBmp() {
        new ImageBuilder(mContext, touchIv, mPath, ImageBuilder.LoadMode.URL)
                .setDefaultImageId(R.drawable.ic_rectangle_picture_default)
                .setScaleType(ImageView.ScaleType.FIT_CENTER)
                .build();
    }


}
