package com.xxl.hello.widget.ui.listener;

import com.xxl.hello.service.data.repository.DataRepositoryKit;

/**
 * VIP拦截监听
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public interface OnVipInterceptListener {

    /**
     * 获取数据服务接口集合
     * @return
     */
    DataRepositoryKit getDataRepositoryKit();
}