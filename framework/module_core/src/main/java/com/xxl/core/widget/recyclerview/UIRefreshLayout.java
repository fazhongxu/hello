package com.xxl.core.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.xxl.core.R;
import com.xxl.kit.ColorUtils;
import com.xxl.kit.ListUtils;

import java.util.List;

/**
 * 刷新视图
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public class UIRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener {

    //region: 成员变量

    /**
     * 默认每页记录条数
     */
    private static final int DEFAULT_PAGE_SIZE = 15;

    /**
     * recyclerView列表
     */
    private RecyclerView mRecyclerView;

    /**
     * 适配器
     */
    private BaseQuickAdapter mAdapter;

    /**
     * 页码
     */
    private int mPage = 1;

    /**
     * 每页记录条数
     */
    private int mPageSize = DEFAULT_PAGE_SIZE;

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

    public UIRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public UIRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupRefreshLayout();
    }

    private void setupRefreshLayout() {
        setColorSchemeColors(ColorUtils.getColor(R.color.core_refresh_color));
        setOnRefreshListener(this);
    }

    //endregion

    //region: 页面生命周期

    //endregion

    //region: OnRefreshDataListener

    @Override
    public void onRefresh() {
        mPage = 1;
        mRequestData = true;
        if (mRefreshDataListener != null) {
            mRefreshDataListener.onRequestData(mPage, mPageSize);
        }
    }

    //endregion

    //region: OnLoadMoreListener

    @Override
    public void onLoadMore() {
        mRequestData = true;
        if (mRefreshDataListener != null) {
            mRefreshDataListener.onRequestData(mPage, mPageSize);
        }
    }

    //endregion

    //region: 提供方法

    public int getPage() {
        return mPage;
    }

    public int getPageSize() {
        return mPageSize;
    }

    /**
     * 重置页码
     */
    public void resetPage() {
        mPage = 1;
    }

    /**
     * 重置每页记录条数
     */
    public void resetPageSize() {
        mPageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 设置每页记录条数
     */
    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    /**
     * 设置刷新监听
     *
     * @param refreshDataListener
     */
    public void setRefreshDataListener(OnRefreshDataListener refreshDataListener) {
        mRefreshDataListener = refreshDataListener;
    }

    /**
     * 设置是否允许刷新
     *
     * @param refreshEnable
     */
    public void setRefreshEnable(boolean refreshEnable) {
        mRefreshEnable = refreshEnable;
        setEnabled(mRefreshEnable);
    }

    /**
     * 设置是否可以加载更多
     *
     * @param loadMoreEnable
     */
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        setLoadMoreEnable(loadMoreEnable, true);
    }

    /**
     * 设置是否可以加载更多
     *
     * @param loadMoreEnable
     */
    public void setLoadMoreEnable(boolean loadMoreEnable,
                                  boolean loadMoreComplete) {
        mLoadMoreEnable = loadMoreEnable;
        if (mAdapter != null) {
            final BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
            loadMoreModule.setEnableLoadMore(loadMoreEnable);
            if (loadMoreComplete) {
                loadMoreModule.loadMoreComplete();
            }
        }
    }

    /**
     * 绑定RecyclerView
     *
     * @param recyclerView
     * @param adapter
     */
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
    public void bindRecyclerView(RecyclerView recyclerView,
                                 BaseQuickAdapter adapter,
                                 RecyclerView.LayoutManager layoutManager) {
        mRecyclerView = recyclerView;
        mAdapter = adapter;
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(this);
        loadMoreModule.setEnableLoadMore(false);
    }

    /**
     * 设置加载的数据
     *
     * @param data
     */
    public <T> void setLoadData(List<T> data) {
        setLoadData(data, ListUtils.getSize(data) >= getPageSize());
    }

    /**
     * 设置加载的数据
     *
     * @param data
     * @param hasNextData 是否有下一页数据
     */
    public <T> void setLoadData(List<T> data,
                                boolean hasNextData) {
        synchronized (this) {
            boolean isRefreshing = isRefreshing();
            boolean isFirstPage = mPage == 1;
            mPage++;
            mRequestData = false;
            setRefreshing(false);
            if (mAdapter != null) {
                if (isRefreshing || isFirstPage) {
                    mAdapter.setList(data);
                } else {
                    mAdapter.addData(data);
                }
                final BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
                mNextLoadEnable = hasNextData;
                if (hasNextData) {
                    loadMoreModule.loadMoreComplete();
                } else {
                    loadMoreModule.loadMoreEnd();
                }
            }
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}