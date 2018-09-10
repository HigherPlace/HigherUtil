package com.app.base.module.base.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.base.module.base.presenter.EmptyPresenter;
import com.bryan.common.widget.ClearViewPager;
import com.bryan.common.widget.titleview.TitleView;
import com.huichexing.base.R;
import com.huichexing.base.R2;
import com.huichexing.base.module.base.presenter.EmptyPresenter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bryan on 2018/2/13 0013.
 */
public abstract class BaseTabActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R2.id.title)
    TitleView mTitle;
    @BindView(R2.id.tab)
    SlidingTabLayout tab;
    @BindView(R2.id.vp)
    ClearViewPager viewPager;

    @BindView(R2.id.ll_search)
    LinearLayout llSearch;

    @BindView(R2.id.tv_search)
    TextView tvSearch;

    private List<Fragment> mFragments;
    private String[] mTitles;
    private MyPagerAdapter mAdapter;

    @Override
    public EmptyPresenter createPresenter() {
        return new EmptyPresenter(mContext);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_tab;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        if (isHasRightText()) {
            mTitle.setUnit(TitleView.Unit.BACK | TitleView.Unit.TEXT | TitleView.Unit.TEXT_FINISH);
            mTitle.setTitle(getActivityTitle());
            mTitle.setTextFinishBtnText(getRightText());
        } else {
            mTitle.setUnit(TitleView.Unit.BACK | TitleView.Unit.TEXT);
            mTitle.setTitle(getActivityTitle());
        }

        llSearch.setVisibility(View.GONE);

        //初始化
        mFragments = getFragments();
        mTitles = getTitles();

        if (mFragments == null || mTitles == null || mFragments.size() != mTitles.length) {
            throw new IllegalArgumentException("请检查Fragment以及title是否有初始化,或者检查两者数量是否相等");
        }
        if (mTitles.length > 4) {
            tab.setTabSpaceEqual(false);
            tab.setTabWidth(90);
        } else {
            tab.setTabSpaceEqual(true);
        }
        if (mTitles.length == 1) {
            tab.setVisibility(View.GONE);
        }
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(mTitles.length);
        tab.setSnapOnTabClick(true);
        tab.setViewPager(viewPager, mTitles);
    }


    protected void setSearchTip(String tip) {
        llSearch.setVisibility(View.VISIBLE);
        tvSearch.setText(tip);
    }


    /**
     * 设置顶部tab是否均分
     *
     * @param isEqual
     */
    public void setIsTabSpaceEqual(boolean isEqual) {
        if (isEqual) {
            tab.setTabSpaceEqual(true);
        } else {
            tab.setTabSpaceEqual(false);
            tab.setTabWidth(90);
        }
    }

    @OnClick(R2.id.ll_search)
    void clickSearch() {
        onSearchListener();
    }

    /**
     * 搜索点击
     */
    public void onSearchListener() {

    }

    public boolean isHasRightText() {
        return false;
    }

    public String getRightText() {
        return "";
    }

    /**
     * 设置右键点击效果
     *
     * @param listener
     */
    public void setOnRightTvClickListener(View.OnClickListener listener) {
        mTitle.setOnTextFinishClickListener(listener);
    }

    /**
     * 返回Activity的Title
     *
     * @return
     */
    protected abstract String getActivityTitle();


    /**
     * 获取标题
     *
     * @return
     */
    protected abstract String[] getTitles();

    /**
     * 获取具体的Fragment
     *
     * @return
     */
    protected abstract List<Fragment> getFragments();

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
