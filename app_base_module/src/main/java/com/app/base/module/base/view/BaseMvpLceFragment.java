package com.app.base.module.base.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bryan.common.utils.ToastMgr;
import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.support.lce.MvpLceView;
import com.bryan.mvp.support.lce.impl.MvpLceFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpLceFragment<M, V extends MvpLceView<M>, P extends MvpPresenter<V>> extends MvpLceFragment<M, V, P> {

    public String TAG = getClass().getSimpleName();
    protected Activity mActivity;
    private Unbinder unbinder;

    //我们自己的Fragment需要缓存视图
    private View viewContent;//缓存视图
    private boolean isInit;
    private boolean isPullToRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        receiveArguments(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (viewContent == null) {
            viewContent = inflater.inflate(getContentView(), container, false);
            unbinder = ButterKnife.bind(this, viewContent);
        }

        //判断Fragment对应的Activity是否存在这个视图
        ViewGroup parent = (ViewGroup) viewContent.getParent();
        if (parent != null) {
            //如果存在,那么我就干掉,重写添加,这样的方式我们就可以缓存视图
            parent.removeView(viewContent);
        }
        return viewContent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit) {
            this.isInit = true;
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().close();
    }

    public abstract int getContentView();

    public void initData() {

    }

    /**
     * 利用setArguments传递参数在这里进行获取
     *
     * @param savedInstanceState
     */
    protected void receiveArguments(Bundle savedInstanceState) {
    }

    public boolean isPullToRefresh() {
        return isPullToRefresh;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        super.loadData(pullToRefresh);
        this.isPullToRefresh = pullToRefresh;
    }

    public abstract void initContentView(View contentView);

    public abstract void initNavigation(View contentView);


    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    public View getRootView() {
        return viewContent;
    }

    /**
     * 显示loading页面
     *
     * @param pullToRefresh true:代表你用的是下拉刷新组件
     */
    @Override
    public void showLoading(boolean pullToRefresh) {
        // TODO: 2018/2/6 0006
    }

    public void toast(String content) {
        ToastMgr.show(content);
    }
}
