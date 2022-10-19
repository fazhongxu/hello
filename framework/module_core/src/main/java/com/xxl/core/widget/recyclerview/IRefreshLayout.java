package com.xxl.core.widget.recyclerview;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipictures.statemanager.StateLayout;
import com.alipictures.statemanager.manager.StateEventListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxl.core.exception.ResponseException;
import com.xxl.kit.ListUtils;

import java.util.List;

/**
 * 刷新视图接口
 *
 * @author xxl.
 * @date 2022/10/11.
 */
public interface IRefreshLayout {

    /**
     * 获取页码
     *
     * @return
     */
    int getPage();

    /**
     * 获取每页记录条数
     *
     * @return
     */
    int getPageSize();

    /**
     * 获取列表适配器
     *
     * @return
     */
    BaseQuickAdapter getAdapter();

    /**
     * 获取recyclerView 列表
     *
     * @return
     */
    RecyclerView getRecyclerView();

    /**
     * 设置每页记录条数
     *
     * @param pageSize
     * @return
     */
    void setPageSize(final int pageSize);

    /**
     * 加载结束后是否隐藏加载更多布局的最小数量
     *
     * @return
     */
    int getMinLoadMoreEndGoneCount();

    /**
     * 重置页码
     */
    void resetPage();

    /**
     * 重置每页记录条数
     */
    void resetPageSize();

    /**
     * 重置加载结束后是否隐藏加载更多布局的最小数量
     */
    void resetMinLoadMoreEndGoneCount();

    /**
     * 设置加载结束后是否隐藏加载更多布局的最小数量
     */
    void setMinLoadMoreEndGoneCount(int minLoadMoreEndGoneCount);

    /**
     * 是否加载中
     *
     * @return
     */
    boolean isLoading();

    /**
     * 设置是否允许刷新
     *
     * @param refreshEnable
     */
    void setRefreshEnable(boolean refreshEnable);

    /**
     * 设置是否可以加载更多
     *
     * @param loadMoreEnable
     */
    void setLoadMoreEnable(boolean loadMoreEnable);

    /**
     * 设置是否开启自动加载
     * 当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多
     *
     * @param enable 是否需要显示底部加载试图
     */
    void setEnableLoadMoreIfNotFullPage(final boolean enable);

    /**
     * 设置刷新监听
     *
     * @param refreshDataListener
     */
    void setRefreshDataListener(OnRefreshDataListener refreshDataListener);

    /**
     * 绑定RecyclerView
     *
     * @param recyclerView
     * @param adapter
     */
    default void bindRecyclerView(RecyclerView recyclerView,
                                  BaseQuickAdapter adapter) {
        bindRecyclerView(recyclerView, adapter, new LinearLayoutManager(recyclerView.getContext()));
    }

    /**
     * 绑定RecyclerView
     *
     * @param recyclerView
     * @param adapter
     * @param layoutManager
     */
    void bindRecyclerView(RecyclerView recyclerView,
                          BaseQuickAdapter adapter,
                          RecyclerView.LayoutManager layoutManager);

    /**
     * 展示加载中状态视图
     */
    void showLoadingState();

    /**
     * 隐藏状态视图
     */
    void dismissState();

    /**
     * 获取状态视图
     *
     * @return
     */
    StateLayout getStateLayout();

    /**
     * 设置状态视图
     *
     * @param state
     * @param stateEventListener
     */
    void setStateLayout(String state,
                        StateEventListener stateEventListener);

    /**
     * 请求数据
     */
    default void requestData() {
        requestData(false);
    }

    /**
     * 请求数据
     *
     * @param isForce 是否强制执行
     */
    void requestData(final boolean isForce);

    /**
     * 设置加载的数据
     *
     * @param data
     */
    default <T> void setLoadData(List<T> data) {
        setLoadData(data, ListUtils.getSize(data) >= getPageSize());
    }


    /**
     * 设置加载的数据
     *
     * @param data
     * @param hasNextData 是否有下一页数据
     */
    default <T> void setLoadData(List<T> data,
                                 boolean hasNextData) {
        setLoadData(data, hasNextData, getPageSize());
    }

    /**
     * 设置加载的数据
     *
     * @param data
     * @param hasNextData             是否有下一页数据
     * @param minLoadEndMoreGoneCount 加载结束后是否隐藏加载更多布局的最小数量
     */
    <T> void setLoadData(List<T> data,
                         boolean hasNextData,
                         int minLoadEndMoreGoneCount);

    /**
     * 未知异常处理
     *
     * @param exception
     * @return
     */
    boolean onUnKowException(ResponseException exception);

}