package com.bryan.mvp.support.delegate.activity;

import android.os.Bundle;

import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;
import com.bryan.mvp.support.delegate.MvpDelegateCallbackProxy;

/**
 * Created by bryan on 2018/2/1 0001.
 */

public class ActivityMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements ActivityMvpDelegate<V, P> {

    private MvpDelegateCallbackProxy<V, P> proxy;
    // 具体的目标接口实现类，需要持有创建Mvp实例
    private ActivityMvpDelegateCallback<V, P> delegateCallback;

    public ActivityMvpDelegateImpl(ActivityMvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("delegateCallback不允许为空");
        }
        this.delegateCallback = delegateCallback;
    }

    private MvpDelegateCallbackProxy<V, P> getDelegateProxy() {
        if (this.proxy == null) {
            this.proxy = new MvpDelegateCallbackProxy<>(this.delegateCallback);
        }
        return this.proxy;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getDelegateProxy().createPresenter();
        getDelegateProxy().attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        getDelegateProxy().detachView();
    }

    @Override
    public void onContentChanged() {

    }
}
