package com.bryan.mvp.support.delegate;

import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;

/**
 * 代理模式－静态代理：目标接口
 * Created by bryan on 2018/2/1 0001.
 */
public interface MvpDelegateCallBack<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * 创建Presenter方法
     *
     * @return
     */
    P createPresenter();

    /**
     * 得到Presenter的实例
     *
     * @return
     */
    P getPresenter();

    /**
     * 设置一个新的Presenter
     *
     * @param presenter
     */
    void setPresenter(P presenter);

    /**
     * 获得具体的MvpView的实例对象
     * @return
     */
    V getMvpView();


}
