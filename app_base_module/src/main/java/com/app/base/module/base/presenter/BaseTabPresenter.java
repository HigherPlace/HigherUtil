package com.app.base.module.base.presenter;

import android.content.Context;

import com.app.base.module.base.model.BaseTabViewState;
import com.app.base.module.base.model.IBaseTabInteractor;
import com.app.base.module.base.view.BaseTabView;

import java.util.List;

/**
 * Created by bryan on 2018/2/25 0025.
 */

public class BaseTabPresenter<T> extends BasePresenter<BaseTabView> {

    private IBaseTabInteractor interactor;

    /**
     * 当前页数
     *
     * @return
     */
    private int mCurPage = 1;

    public static final int mPagerNum = 10;

    public BaseTabPresenter(Context context, IBaseTabInteractor interactor) {
        super(context);
        this.interactor = interactor;
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        mCurPage = 1;
        interactor.refreshLayout(new IBaseTabInteractor.RefreshDataCallBack<T>() {
            @Override
            public void refreshDataSuccess(List<T> list) {
                mCurPage++;
                getView().render(new BaseTabViewState.RefreshSuccess<>(list));
            }

            @Override
            public void refreshDataFail(String tip) {
                getView().render(new BaseTabViewState.refreshFail(tip));
            }
        });
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        interactor.loadMore(mCurPage, new IBaseTabInteractor.LoadMoreDataCallBack<T>() {
            @Override
            public void loadMoreSuccess(List<T> list) {
                mCurPage++;
                getView().render(new BaseTabViewState.LoadMoreSuccess<>(list));
            }

            @Override
            public void loadMoreFail(String tip) {
                getView().render(new BaseTabViewState.LoadMoreFail(tip));
            }
        });
    }
}
