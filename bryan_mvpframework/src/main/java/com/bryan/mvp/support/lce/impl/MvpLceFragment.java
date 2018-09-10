package com.bryan.mvp.support.lce.impl;

import android.os.Bundle;
import android.view.View;

import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.support.lce.MvpLceView;
import com.bryan.mvp.support.lce.impl.animator.ILceAnimator;
import com.bryan.mvp.support.view.MvpFragment;

/**
 * Created by bryan on 2018/2/1 0001.
 */

public abstract class MvpLceFragment<M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends MvpFragment<V, P> implements MvpLceView<M> {

    private MvpLceViewImpl<M> lceViewImpl;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (lceViewImpl == null) {
            lceViewImpl = new MvpLceViewImpl<M>();
        }
        initLceView(view);
    }

    private void initLceView(View v) {
        lceViewImpl.initLceView(v);
        lceViewImpl.setOnErrorViewClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onErrorClick();
            }
        });
    }

    private void onErrorClick() {
        loadData(false);
    }

    public void setLceAnimator(ILceAnimator lceAnimator) {
        lceViewImpl.setLceAnimator(lceAnimator);
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

    @Override
    public void bindData(M data) {
        lceViewImpl.bindData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        lceViewImpl.loadData(pullToRefresh);
    }
}
