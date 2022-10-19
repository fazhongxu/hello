package com.xxl.core.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xxl.core.widget.recyclerview.adapter.SimpleLoadMoreView;
import com.xxl.kit.ListUtils;

import java.util.List;

/**
 * 刷新视图
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public class UISmartRefreshLayout extends SmartRefreshLayout implements IRefreshLayout, OnRefreshListener, OnLoadMoreListener, com.chad.library.adapter.base.listener.OnLoadMoreListener {

    //region: 成员变量

    /**
     * 默认每页记录条数
     */
    private static final int DEFAULT_PAGE_SIZE = 15;

    /**
     * 默认最小加载结束后是否显示"没有更多数据"标识的数量
     */
    private static final int DEFAULT_MIN_LOAD_MORE_END_GONE_COUNT = 15;

    /**
     * recyclerView列表
     */
    private RecyclerView mRecyclerView;

    /**
     * 适配器
     */
    private BaseQuickAdapter mAdapter;


    // TODO: 2022/10/19 加StateLayout状态视图 
    /**
     * 页码
     */
    private int mPage = 1;

    /**
     * 每页记录条数
     */
    private int mPageSize = DEFAULT_PAGE_SIZE;

    /**
     * 加载结束后是否隐藏加载更多布局的最小数量
     */
    private int mMinLoadMoreEndGoneCount = DEFAULT_MIN_LOAD_MORE_END_GONE_COUNT;

    /**
     * 数据请求监听
     */
    private OnRefreshDataListener mRefreshDataListener;

    /**
     * 是否正在请求数据
     */
    private boolean mRequestData;

    /**
     * 是否启用刷新
     */
    private boolean mRefreshEnable = true;

    /**
     * 是否启用加载更多
     */
    private boolean mLoadMoreEnable = true;

    /**
     * 是否还可以加载下一页
     */
    private boolean mNextLoadEnable = true;

    //endregion

    //region: 构造函数

    public UISmartRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public UISmartRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setRefreshLayout();
    }

    //endregion

    //region: 页面视图渲染

    private void setRefreshLayout() {
        setOnRefreshListener(this);
        setRefreshHeader(new MaterialHeader(getContext()));
    }

    //endregion

    //region: IRefreshLayout

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public int getPageSize() {
        return mPageSize;
    }

    /**
     * 获取列表适配器
     *
     * @return
     */
    @Override
    public BaseQuickAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 获取recyclerView 列表
     *
     * @return
     */
    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 设置每页记录条数
     */
    @Override
    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }


    /**
     * 加载结束后是否隐藏加载更多布局的最小数量
     *
     * @return
     */
    @Override
    public int getMinLoadMoreEndGoneCount() {
        return mMinLoadMoreEndGoneCount;
    }

    /**
     * 重置页码
     */
    @Override
    public void resetPage() {
        mPage = 1;
    }

    /**
     * 重置每页记录条数
     */
    @Override
    public void resetPageSize() {
        mPageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 重置加载结束后是否隐藏加载更多布局的最小数量
     */
    @Override
    public void resetMinLoadMoreEndGoneCount() {
        mMinLoadMoreEndGoneCount = DEFAULT_MIN_LOAD_MORE_END_GONE_COUNT;
    }

    /**
     * 设置加载结束后是否隐藏加载更多布局的最小数量
     */
    @Override
    public void setMinLoadMoreEndGoneCount(int minLoadMoreEndGoneCount) {
        mMinLoadMoreEndGoneCount = minLoadMoreEndGoneCount;
    }

    /**
     * 是否加载中
     *
     * @return
     */
    @Override
    public boolean isLoading() {
        if (mAdapter == null) {
            return false;
        }
        return mAdapter.getLoadMoreModule().isLoading();
    }

    /**
     * 设置是否允许刷新
     *
     * @param refreshEnable
     */
    @Override
    public void setRefreshEnable(boolean refreshEnable) {
        mRefreshEnable = refreshEnable;
        setEnabled(mRefreshEnable);
    }

    /**
     * 设置是否可以加载更多
     *
     * @param loadMoreEnable
     */
    @Override
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        mLoadMoreEnable = loadMoreEnable;
        if (mAdapter != null) {
            final BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
            loadMoreModule.setEnableLoadMore(loadMoreEnable);
        }
    }

    /**
     * 设置是否开启自动加载
     * 当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多
     *
     * @param enable 是否需要显示底部加载试图
     */
    @Override
    public void setEnableLoadMoreIfNotFullPage(final boolean enable) {
        if (mAdapter != null) {
            BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
            loadMoreModule.setEnableLoadMoreIfNotFullPage(enable);
        }
    }

    /**
     * 设置刷新监听
     *
     * @param refreshDataListener
     */
    @Override
    public void setRefreshDataListener(OnRefreshDataListener refreshDataListener) {
        mRefreshDataListener = refreshDataListener;
    }

    /**
     * 绑定RecyclerView
     *
     * @param recyclerView
     * @param adapter
     */
    @Override
    public void bindRecyclerView(RecyclerView recyclerView,
                                 BaseQuickAdapter adapter) {
        bindRecyclerView(recyclerView, adapter, new LinearLayoutManager(getContext()));
    }

    /**
     * 绑定RecyclerView
     *
     * @param recyclerView
     * @param adapter
     * @param layoutManager
     */
    @Override
    public void bindRecyclerView(RecyclerView recyclerView,
                                 BaseQuickAdapter adapter,
                                 RecyclerView.LayoutManager layoutManager) {
        mRecyclerView = recyclerView;
        mAdapter = adapter;
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setLoadMoreView(new SimpleLoadMoreView());
        loadMoreModule.setOnLoadMoreListener(this);
        loadMoreModule.setEnableLoadMore(false);
    }

    /**
     * 请求数据
     */
    @Override
    public void requestData() {
        requestData(false);
    }

    /**
     * 请求数据
     *
     * @param isForce 是否强制执行
     */
    @Override
    public void requestData(final boolean isForce) {
        final boolean isNotForce = !isForce && (mRefreshDataListener == null || mRequestData || isLoading());
        if (isNotForce) {
            return;
        }
        resetPage();
        setLoadMoreEnable(false);
        mRequestData = true;
        mRefreshDataListener.onRequestData(getPage(), getPageSize());
    }

    /**
     * 设置加载的数据
     *
     * @param data
     */
    @Override
    public <T> void setLoadData(List<T> data) {
        setLoadData(data, ListUtils.getSize(data) >= getPageSize());
    }

    /**
     * 设置加载的数据
     *
     * @param data
     * @param hasNextData 是否有下一页数据
     */
    @Override
    public <T> void setLoadData(List<T> data,
                                boolean hasNextData) {
        setLoadData(data, hasNextData, getMinLoadMoreEndGoneCount());
    }

    /**
     * 设置加载的数据
     *
     * @param data
     * @param hasNextData             是否有下一页数据
     * @param minLoadEndMoreGoneCount 加载结束后是否隐藏加载更多布局的最小数量
     */
    @Override
    public <T> void setLoadData(List<T> data,
                                boolean hasNextData,
                                int minLoadEndMoreGoneCount) {
        synchronized (this) {
            boolean isRefreshing = isRefreshing();
            boolean isFirstPage = mPage == 1;
            mPage++;
            mRequestData = false;
            finishRefresh(false);
            int size = ListUtils.getSize(data);
            if (mAdapter != null) {
                if (isRefreshing || isFirstPage) {
                    mAdapter.setNewInstance(data);
                } else {
                    if (size > 0) {
                        mAdapter.addData(data);
                    }
                }
                final BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
                mNextLoadEnable = hasNextData;
                if (hasNextData) {
                    loadMoreModule.loadMoreComplete();
                } else {
                    loadMoreModule.loadMoreEnd(isFirstPage && size < minLoadEndMoreGoneCount);
                }
            }
        }
    }

    //endregion

    //region: OnRefreshListener

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mRequestData = true;
        resetPage();
        if (mRefreshDataListener != null) {
            mRefreshDataListener.onRequestData(mPage, mPageSize);
        }
    }

    //endregion

    //region: OnLoadMoreListener

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mRequestData = true;
        if (mRefreshDataListener != null) {
            mRefreshDataListener.onRequestData(mPage, mPageSize);
        }
    }

    //endregion

    //region: OnLoadMoreListener

    @Override
    public void onLoadMore() {
        if (mRefreshListener == null || isRefreshing()) {
            return;
        }
        mRequestData = true;
        mRefreshDataListener.onRequestData(mPage, mPageSize);
    }

    //endregion


}