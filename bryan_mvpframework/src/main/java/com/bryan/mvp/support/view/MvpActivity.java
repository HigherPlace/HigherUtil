package com.bryan.mvp.support.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bryan.autolayout.AutoLayoutActivity;
import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;
import com.bryan.mvp.support.delegate.activity.ActivityMvpDelegate;
import com.bryan.mvp.support.delegate.activity.ActivityMvpDelegateCallback;
import com.bryan.mvp.support.delegate.activity.ActivityMvpDelegateImpl;

/**
 * Created by bryan on 2018/2/1 0001.
 */
@SuppressLint("NewApi")
public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends AutoLayoutActivity
        implements ActivityMvpDelegateCallback<V, P>, MvpView {

    private P presenter;

    private ActivityMvpDelegate<V, P> activityMvpDelegate;

    @SuppressWarnings("unchecked")
    protected ActivityMvpDelegate<V, P> getActivityMvpDelegate() {
        if (this.activityMvpDelegate == null) {
            this.activityMvpDelegate = new ActivityMvpDelegateImpl(this);
        }
        return this.activityMvpDelegate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getActivityMvpDelegate().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivityMvpDelegate().onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getActivityMvpDelegate().onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getActivityMvpDelegate().onStop();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getActivityMvpDelegate().onContentChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getActivityMvpDelegate().onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getActivityMvpDelegate().onDestroy();
    }
}
