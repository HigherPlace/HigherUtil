package com.bryan.mvp.base.presenter;

import android.content.Context;

import com.bryan.mvp.base.view.MvpView;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 抽象类 统一管理View层绑定和解除绑定
 * Created by bryan on 2018/2/1 0001.
 */
public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<Context> weakContext;
    private WeakReference<V> weakView;
    private V proxyView;


    public MvpBasePresenter(Context context) {
        this.weakContext = new WeakReference<>(context);
    }

    public Context getContext() {
        return weakContext.get();
    }

    public V getView() {
        return proxyView;
    }

    /**
     * 用于检查View是否为空对象
     *
     * @return
     */
    public boolean isAttachView() {
        return this.weakView != null && this.weakView.get() != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void attachView(V view) {
        this.weakView = new WeakReference<V>(view);
        MvpViewInvocationHandler invocationHandler = new MvpViewInvocationHandler(this.weakView.get());
        proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                invocationHandler);
    }

    @Override
    public void detachView() {
        if (this.weakView != null) {
            this.weakView.clear();
            this.weakView = null;
        }
    }

    @Override
    public void close() {

    }

    /**
     * 动态代理，处理View可能已经被回收的情况
     */
    private class MvpViewInvocationHandler implements InvocationHandler {

        private MvpView mvpView;

        public MvpViewInvocationHandler(MvpView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object arg0, Method arg1, Object[] arg2)
                throws Throwable {
            if (isAttachView()) {
                return arg1.invoke(mvpView, arg2);
            }
            return null;
        }
    }
}
