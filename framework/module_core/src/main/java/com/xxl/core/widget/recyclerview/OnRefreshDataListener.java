package com.xxl.core.widget.recyclerview;

/**
 * 数据刷新监听
 *
 * @author xxl.
 * @date 2022/10/10.
 */
public interface OnRefreshDataListener {

    /**
     * 请求数据
     *
     * @param page     页码
     * @param pageSize 每页记录条数
     */
    void onRequestData(int page,
                       int pageSize);
}