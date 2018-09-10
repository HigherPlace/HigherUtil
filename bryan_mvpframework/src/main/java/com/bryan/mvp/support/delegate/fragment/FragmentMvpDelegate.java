package com.bryan.mvp.support.delegate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;


public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {
    void onCreate(Bundle savedInstanceState);

    void onActivityCreated(Bundle savedInstanceState);

    void onViewCreated(View view, Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onResume();

    void onStop();

    void onDestroyView();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onAttach(Context context);

    void onDetach();
}
