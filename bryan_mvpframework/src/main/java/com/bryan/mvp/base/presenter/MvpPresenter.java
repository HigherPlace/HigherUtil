package com.bryan.mvp.base.presenter;

import com.bryan.mvp.base.view.MvpView;

/**
 * 抽象P层接口
 * Created by bryan on 2018/2/1 0001.
 */
public interface MvpPresenter<V extends MvpView> {
    /**
     * 绑定视图
     *
     * @param view
     */
    void attachView(V view);

    /**
     * 解绑视图
     */
    void detachView();

    void close();

}
