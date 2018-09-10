package com.bryan.mvp.support.delegate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;
import com.bryan.mvp.support.delegate.MvpDelegateCallbackProxy;

/**
 * Created by bryan on 2018/2/1 0001.
 */

public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements FragmentMvpDelegate<V, P> {

    private MvpDelegateCallbackProxy<V, P> proxy;

    private FragmentMvpDelegateCallback<V, P> delegateCallback;

    public FragmentMvpDelegateImpl(FragmentMvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("delegateCallback 不允许为空");
        }
        this.delegateCallback = delegateCallback;
    }

    private MvpDelegateCallbackProxy<V, P> getDelegateProxy() {
        if (proxy == null) {
            proxy = new MvpDelegateCallbackProxy<>(delegateCallback);
        }
        return proxy;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDelegateProxy().createPresenter();
        getDelegateProxy().attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {
        getDelegateProxy().detachView();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onDetach() {

    }
}
