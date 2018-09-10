package com.bryan.mvp.support.delegate;

import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;

/**
 * Created by bryan on 2018/2/1 0001.
 */

public class MvpDelegateCallbackProxy<V extends MvpView, P extends MvpPresenter<V>>
        implements MvpDelegateCallBack<V, P> {

    private MvpDelegateCallBack<V, P> mvpDelegateCallBack;

    public MvpDelegateCallbackProxy(
            MvpDelegateCallBack<V, P> mvpDelegateCallBack) {
        this.mvpDelegateCallBack = mvpDelegateCallBack;
    }

    @Override
    public P createPresenter() {
        P presenter = mvpDelegateCallBack.getPresenter();
        if (presenter == null) {
            presenter = mvpDelegateCallBack.createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("Presenter不允许为空");
        }
        mvpDelegateCallBack.setPresenter(presenter);
        return getPresenter();
    }

    @Override
    public P getPresenter() {
        P presenter = mvpDelegateCallBack.getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter不允许为空");
        }
        return presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        mvpDelegateCallBack.setPresenter(presenter);
    }

    @Override
    public V getMvpView() {
        return mvpDelegateCallBack.getMvpView();
    }

    public void attachView(){
        getPresenter().attachView(getMvpView());
    }

    public void detachView(){
        getPresenter().detachView();
    }
}
