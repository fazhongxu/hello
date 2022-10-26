package com.xxl.hello.main.ui.main.adapter.multi.provider;

import com.xxl.core.widget.recyclerview.adapter.BaseRecycleItemListener;
import com.xxl.hello.main.ui.main.adapter.OnTestRecycleItemListener;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public interface OnTestItemProviderListener extends BaseRecycleItemListener {

    /**
     * 获取多条目事件监听
     *
     * @return
     */
    OnTestRecycleItemListener getMultiRecycleItemListener();

    /**
     * 获取搜索的关键词
     *
     * @return
     */
    String getSearchKeywords();
}