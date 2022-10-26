package com.xxl.hello.main.ui.main.adapter.section;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseRecycleItemListener;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public interface TestSectionRecycleItemListener extends BaseRecycleItemListener {

    /**
     * 条目点击
     *
     * @param targetEntity
     */
    void onItemClick(@NonNull final TestListEntity targetEntity);
}