package com.bryan.mvp.support.lce.impl;

import android.view.View;

import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.support.lce.MvpLceView;
import com.bryan.mvp.support.lce.impl.animator.ILceAnimator;
import com.bryan.mvp.support.view.MvpActivity;

/**
 * Created by bryan on 2018/2/1 0001.
 */

public abstract class MvpLceActivity<M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends MvpActivity<V, P> implements MvpLceView<M> {

    private MvpLceViewImpl<M> lceViewImpl;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        lceViewImpl = new MvpLceViewImpl<>();
        initLceView(getWindow().getDecorView());
    }

    private void initLceView(View v) {
        lceViewImpl.initLceView(v);
        lceViewImpl.setOnErrorViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onErrorClick();
            }
        });
    }

    private void onErrorClick() {
        loadData(false);
    }

    /**
     * 提供给子类配置想要的动画策略
     *
     * @param lceAnimator
     */
    public void setLceAnimator(ILceAnimator lceAnimator) {
        this.lceViewImpl.setLceAnimator(lceAnimator);

    }

//    @Override
    public void showLoading(boolean pullToRefresh) {
        lceViewImpl.showLoading(pullToRefresh);
    }

    @Override
    public void showContent() {
        lceViewImpl.showContent();
    }

    @Override
    public void showError() {
        lceViewImpl.showError();
    }

}
