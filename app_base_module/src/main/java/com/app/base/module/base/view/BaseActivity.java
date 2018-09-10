package com.app.base.module.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.app.base.BaseApplication;
import com.app.base.constant.Constant;
import com.bryan.common.utils.AppKeyBoardMgr;
import com.bryan.common.utils.AppManager;
import com.bryan.common.utils.BroadcastManager;
import com.bryan.common.utils.KeyBoardUtil;
import com.bryan.common.utils.ToastMgr;
import com.bryan.common.utils.WeakHandler;
import com.bryan.common.utils.log.LogUtils;
import com.bryan.mvp.base.presenter.MvpPresenter;
import com.bryan.mvp.base.view.MvpView;
import com.bryan.mvp.support.view.MvpActivity;
import com.huichexing.base.BaseApplication;
import com.huichexing.base.R;
import com.huichexing.base.constant.Constant;
import com.huichexing.base.entity.AccountBean;
import com.huichexing.base.utils.user.UserUtil;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.xutils.common.Callback;

import butterknife.ButterKnife;

/**
 * Created by bryan on 2018/2/6 0006.
 */

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> {
    protected String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected Activity mActivity;

    protected Callback.Cancelable cancelable;
    protected Callback.Cancelable cancelable2;

    protected WeakHandler weakHandler = new WeakHandler();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        mActivity = this;
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        LogUtils.i(TAG, "onCreate");
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);

        init(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().close();
        }
        if (cancelable != null) {
            cancelable.cancel();
        }
        if (cancelable2 != null) {
            cancelable2.cancel();
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        //修复软键盘可能存在的内存泄漏
        KeyBoardUtil.fixInputMethodManagerLeak(mContext);
        //提示系统回收
        System.gc();
    }

    protected void beforeSetContentView() {
    }

    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);

    protected QMUITipDialog loadingDialog;

    protected void showLoading() {
        showLoading("请稍后...");
    }


    protected void showLoading(String tip) {
        if (mActivity.isFinishing()) {
            //如果ActivityFinish了就不管
        } else if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        } else if (loadingDialog == null) {
            loadingDialog = new QMUITipDialog.Builder(mContext)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(tip)
                    .create();
            loadingDialog.show();
        }


    }

    protected void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 点击空白地方隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    public void toast(String content) {
        ToastMgr.show(content);
    }


    protected Intent getIntent(Class<?> cls) {
        return new Intent(mContext, cls);
    }

    protected Intent getIntent(Class<?> cls, int flags) {
        Intent intent = getIntent(cls);
        intent.setFlags(flags);
        return intent;
    }

    public void startActivity(Class<?> cls) {
        startActivity(getIntent(cls));
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(getIntent(cls), requestCode);
        overridePendingTransition(R.anim.activity_push_open_in, R.anim.activity_push_open_out);
    }

    protected void startActivityFinish(Class<?> cls) {
        startActivity(cls);
        finish();
    }

    /**
     * 隐藏软键盘
     */
    public void hideInputMethod() {
        AppKeyBoardMgr.hideInputMethod(mActivity);
    }


    protected void reLogin() {
        toast("登陆已失效，请重新登陆");
        BroadcastManager.getInstance(BaseApplication.getGlobalContext()).sendBroadcast(Constant.Action.Quit);
    }

    public AccountBean getAccountBean() {
        AccountBean result = UserUtil.getInstance().getUser();
        if (result == null) {
            reLogin();
        }
        return result;
    }

}
