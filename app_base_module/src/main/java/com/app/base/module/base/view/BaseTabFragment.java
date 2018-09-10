package com.app.base.module.base.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.app.base.R;
import com.app.base.R2;
import com.app.base.module.base.model.BaseTabViewState;
import com.app.base.module.base.model.IBaseTabInteractor;
import com.app.base.module.base.presenter.BaseTabPresenter;
import com.bryan.autolayout.utils.AutoUtils;
import com.bryan.common.utils.recyclerview.RecycleViewDivider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * 可配合BaseTabActivity一起使用，或者单独使用
 * Created by bryan on 2018/2/25 0025.
 */
public abstract class BaseTabFragment<T> extends BaseMvpFragment<BaseTabView, BaseTabPresenter<BaseTabView>> implements BaseTabView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R2.id.rv)
    public RecyclerView mRecyclerView;
    @BindView(R2.id.refreshLayout)
    public SmartRefreshLayout refreshLayout;

    protected BaseQuickAdapter<T, BaseViewHolder> adapter;

    private QMUIEmptyView mEmptyView;
    private String mEmptyTip = "当前没有数据";

    //懒加载
    public boolean isInited;    // 是否为第一次创建fragment界面,true已经初始化过界面，false还未初始化过界面
    protected boolean isLoad = false;
    protected boolean isFirst = true;//判断是否初次加载数据，防止来回多次加载

    @Override
    protected void initData() {
        //initRecyclerViewz
        adapter = generatorAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //设置订单之间的间距
        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, AutoUtils.getPercentWidthSizeBigger(3), getResources().getColor(R.color.bg_divider)));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        mEmptyView = (QMUIEmptyView) mActivity.getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mRecyclerView.getParent(), false);
        AutoUtils.auto(mEmptyView);
        if (isNeedLoadMore()) {
            //设置加载更多监听
            adapter.setOnLoadMoreListener(this, mRecyclerView);
            //设置第一次进来不加载,需要先绑定setOnLoadMoreListener(this, mRecyclerView);
            adapter.disableLoadMoreIfNotFullPage();
        }
        adapter.setEmptyView(mEmptyView);

        //initRefreshLayout
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getPresenter().refreshData();
                refresh();
            }
        });
    }

    /*重新刷新界面*/
    protected void refresh() {
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_base_tab;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInited = true;
        fetchData();
    }

    /**
     * 是否需要加载更多
     *
     * @return
     */
    protected boolean isNeedLoadMore() {
        return true;
    }

    /*标识是否需要懒加载，默认不需要，如果需要的话重写此方法，返回true*/
    protected boolean isNeedLazyLoad() {
        return false;
    }

    /**
     * fragment 从不可见到可见时会调用此方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isNeedLazyLoad()) {
            return;
        }
        if (getUserVisibleHint()) {
            isLoad = true;
            fetchData();
        } else {
            isLoad = false;
        }
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     * 3-已经加载过一次的不再进行预加载
     */
    public void fetchData() {
        if (isNeedLazyLoad()) {
            if (!isInited || !isLoad || !isFirst) {
                return;
            }
            if (getUserVisibleHint()) {
                //初始化数据
                showLoading("加载中...");
                getPresenter().refreshData();
                refresh();
                isLoad = true;
                isFirst = false;
            } else {
//            if (isLoad) {
//                stopLoad();
//            }
            }
        } else {
            //初始化数据
            showLoading("加载中...");
            getPresenter().refreshData();
            refresh();
        }

    }


    @Override
    public BaseTabPresenter createPresenter() {
        return new BaseTabPresenter<T>(mContext, generatorInteractor());
    }

    protected abstract IBaseTabInteractor generatorInteractor();

    protected abstract BaseQuickAdapter<T, BaseViewHolder> generatorAdapter();

    @Override
    public void render(BaseTabViewState viewState) {
        dismissLoading();
        if (viewState instanceof BaseTabViewState.RefreshSuccess) {
            refreshLayout.finishRefresh();
            adapter.setNewData(((BaseTabViewState.RefreshSuccess) viewState).getResult());
            adapter.setEnableLoadMore(true);
            if (((BaseTabViewState.RefreshSuccess) viewState).getResult().size() < BaseTabPresenter.mPagerNum) {
                //加载完全部数据
                adapter.loadMoreEnd(isNeedEndTip());
                if (adapter.getData().size() == 0) {
                    mEmptyView.show(mEmptyTip, null);
                }
            } else {
                adapter.loadMoreComplete();
            }
        } else if (viewState instanceof BaseTabViewState.refreshFail) {
            refreshLayout.finishRefresh();
            toast(((BaseTabViewState.refreshFail) viewState).getTip());
            if (adapter.getData().size() == 0) {
                adapter.setNewData(null);
                mEmptyView.show(false, "加载失败", ((BaseTabViewState.refreshFail) viewState).getTip(), "点击重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshLayout.autoRefresh();
                    }
                });
            }
        } else if (viewState instanceof BaseTabViewState.LoadMoreSuccess) {
            adapter.addData(((BaseTabViewState.LoadMoreSuccess) viewState).getResult());
            if (((BaseTabViewState.LoadMoreSuccess) viewState).getResult().size() < BaseTabPresenter.mPagerNum) {
                //加载完全部数据
                adapter.loadMoreEnd(isNeedEndTip());
            } else {
                adapter.loadMoreComplete();
            }
        } else if (viewState instanceof BaseTabViewState.LoadMoreFail) {
            toast(((BaseTabViewState.LoadMoreFail) viewState).getTip());
            adapter.loadMoreFail();
        }
    }

    protected boolean isNeedEndTip() {
        return true;
    }

    protected void setEmptyTip(String tip) {
        mEmptyTip = tip;
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        getPresenter().loadMore();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInited = false;
        isLoad = false;

    }
}
