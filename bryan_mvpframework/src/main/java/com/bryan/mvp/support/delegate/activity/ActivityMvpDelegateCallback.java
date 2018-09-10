package com.bryan.mvp.support.delegate.activity;


import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;
import com.bryan.mvp.support.delegate.MvpDelegateCallBack;

/**
 * 扩展目标接口 针对不同的模块，目标接口有差异
 */
public interface ActivityMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpDelegateCallBack<V, P> {


}
