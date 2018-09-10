package com.bryan.mvp.support.delegate.activity;

import android.os.Bundle;

import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;

/**
 * Created by bryan on 2018/2/1 0001.
 */
public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onRestart();

    void onStop();

    void onDestroy();

    void onContentChanged();
}
