package com.app.base.module.base.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.base.BaseApplication;
import com.app.base.constant.Constant;
import com.bryan.common.utils.BroadcastManager;
import com.bryan.common.utils.ToastMgr;
import com.huichexing.base.BaseApplication;
import com.huichexing.base.constant.Constant;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.xutils.common.Callback;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment的基类，不适用MVP
 * Created by bryan on 2018/2/6 0006.
 */
public abstract class BaseFragment extends Fragment {

    public String TAG = getClass().getSimpleName();
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder unbinder;

    //我们自己的Fragment需要缓存视图
    private View viewContent;//缓存视图
    private boolean isInit;

    protected Callback.Cancelable cancelable;
    protected Callback.Cancelable cancelable2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        receiveArguments(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewContent == null) {
            viewContent = inflater.inflate(getContentView(), container, false);
            unbinder = ButterKnife.bind(this, viewContent);
        }

        //判断Fragment对应的Activity是否存在这个试图
        ViewGroup parent = (ViewGroup) viewContent.getParent();
        if (parent != null) {
            //如果试图存在，就干掉重新添加，这样就可以缓存试图
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
    }

    /**
     * 利用setArguments传递参数在这里进行获取
     *
     * @param savedInstanceState
     */
    protected void receiveArguments(Bundle savedInstanceState) {
    }


    /**
     * 返回试图资源id
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 加载数据
     */
    protected abstract void initData();

    protected void toast(String content) {
        ToastMgr.show(content);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        if (cancelable != null) {
            cancelable.cancel();
        }
        if (cancelable2 != null) {
            cancelable2.cancel();
        }
        super.onDestroyView();
    }

    public View getRootView() {
        return viewContent;
    }

    protected QMUITipDialog loadingDialog;

    protected void showLoading() {
        showLoading("请稍后...");
    }

    protected void showLoading(String tip) {
        if (loadingDialog == null) {
            loadingDialog = new QMUITipDialog.Builder(mContext)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(tip)
                    .create();
        }
        loadingDialog.show();
    }

    protected void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


    protected void reLogin() {
        toast("登陆已失效，请重新登陆");
        BroadcastManager.getInstance(BaseApplication.getGlobalContext()).sendBroadcast(Constant.Action.Quit);
    }


}
