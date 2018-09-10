package com.app.base.module.base.presenter;

import android.content.Context;

import com.bryan.mvp.base.presenter.MvpBasePresenter;
import com.bryan.mvp.base.view.MvpView;

public abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    public BasePresenter(Context context) {
        super(context);
    }

}
