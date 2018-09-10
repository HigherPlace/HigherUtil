package com.bryan.mvp.support.lce;

import com.bryan.mvp.base.view.MvpView;

/**
 * Created by bryan on 2018/2/1 0001.
 */
public interface MvpLceView<M> extends MvpView {

    /**
     * 显示contentView
     */
    void showContent();


    /**
     * 显示异常页面
     */
    void showError();

    /**
     * 绑定数据
     *
     * @param data
     */
    void bindData(M data);

    /**
     * 加载数据
     *
     * @param pullToRefresh
     */
    void loadData(boolean pullToRefresh);


}
