package com.bryan.mvp.support.lce.impl;

import android.view.View;

import com.bryan.mvp.R;
import com.bryan.mvp.support.lce.MvpLceView;
import com.bryan.mvp.support.lce.impl.animator.DefaultLceAnimator;
import com.bryan.mvp.support.lce.impl.animator.ILceAnimator;

/**
 * Created by bryan on 2018/2/1 0001.
 */

public class MvpLceViewImpl<M> implements MvpLceView<M> {

    private View loadingView;
    private View contentView;
    private View errorView;

    private ILceAnimator lceAnimator;

    /**
     * 初始化Lce View
     *
     * @param v
     */
    public void initLceView(View v) {
        if (loadingView == null) {
            loadingView = v.findViewById(R.id.loadingView);
        }

        if (contentView == null) {
            contentView = v.findViewById(R.id.contentView);
        }
        if (errorView == null) {
            errorView = v.findViewById(R.id.errorView);
        }

        if (loadingView == null) {
            throw new NullPointerException("loadingView不允许为空");
        }
        if (contentView == null) {
            throw new NullPointerException("contentView 不允许为空");
        }
        if (errorView == null) {
            throw new NullPointerException("errorView 不允许为空");
        }
    }

    /**
     * 添加错误点击监听
     *
     * @param listener
     */
    public void setOnErrorViewClickListener(View.OnClickListener listener) {
        if (errorView != null) {
            errorView.setOnClickListener(listener);
        }
    }

    private ILceAnimator getLceAnimator() {
        if (lceAnimator == null) {
            lceAnimator = DefaultLceAnimator.getInstance();
        }
        return lceAnimator;
    }

    /**
     * 绑定动画执行策略
     *
     * @param animator
     */
    public void setLceAnimator(ILceAnimator animator) {
        lceAnimator = animator;
    }


//    @Override
    public void showLoading(boolean pullToRefresh) {
        //注意：记得加判断，因为下拉刷新组件有正在加载头部视图，不需要显示加载过程了
        if (!pullToRefresh) {
            getLceAnimator().showLoading(loadingView, contentView, errorView);
        }
    }

//    @Override
    public void showLoading(String content) {

    }

//    @Override
    public void hideProgress() {

    }

//    @Override
    public void toast(String content) {

    }

    @Override
    public void showContent() {
        getLceAnimator().showContent(loadingView, contentView, errorView);
    }

    @Override
    public void showError() {
        getLceAnimator().showErrorView(loadingView, contentView, errorView);
    }

    @Override
    public void bindData(M data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
