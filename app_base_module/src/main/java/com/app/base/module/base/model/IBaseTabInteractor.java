package com.app.base.module.base.model;

import java.util.List;

/**
 * Created by bryan on 2018/2/25 0025.
 */

public interface IBaseTabInteractor {

    /**
     * 加载更多
     */
    void loadMore(int page,LoadMoreDataCallBack callBack);

    /**
     * 刷新界面
     */
    void refreshLayout(RefreshDataCallBack callBack);

     interface RefreshDataCallBack<T> {
        void refreshDataSuccess(List<T> list);

        void refreshDataFail(String tip);
    }

     interface LoadMoreDataCallBack<T> {
        void loadMoreSuccess(List<T> list);

        void loadMoreFail(String tip);
    }

}
