package com.xxl.hello.main.ui.main.adapter.multi;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.xxl.core.widget.recyclerview.adapter.BaseMultiDraggableAdapter;
import com.xxl.hello.main.ui.main.adapter.OnTestRecycleItemListener;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;
import com.xxl.hello.main.ui.main.adapter.multi.provider.OnTestItemProviderListener;
import com.xxl.hello.main.ui.main.adapter.multi.provider.TestImageProvider;
import com.xxl.hello.main.ui.main.adapter.multi.provider.TestTextProvider;
import com.xxl.hello.main.ui.main.adapter.multi.provider.TestVideoProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

/**
 * 测试多布局
 *
 * @author xxl.
 * @date 2022/10/10.
 */
public class TestMultiAdapter extends BaseMultiDraggableAdapter<TestListEntity, OnTestRecycleItemListener>
        implements OnTestItemProviderListener {

    //region: 成员变量

    /**
     * 搜索关键词
     */
    private String mSearchKeywords;

    //endregion

    //region: 构造函数

    @Inject
    public TestMultiAdapter() {
        super();
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void registerItemProvider() {
        registerItemProvider(TestTextProvider.obtain(this));
        registerItemProvider(TestImageProvider.obtain(this));
        registerItemProvider(TestVideoProvider.obtain(this));
    }

    @Override
    protected int getItemType(@NonNull List<? extends TestListEntity> list,
                              int position) {
        TestListEntity testListEntity = list.get(position);
        return testListEntity.getMediaType();
    }

    //endregion

    //region: OnTestItemProviderListener

    /**
     * 获取多条目事件监听
     *
     * @return
     */
    @Override
    public OnTestRecycleItemListener getMultiRecycleItemListener() {
        return mListener;
    }

    /**
     * 获取搜索的关键词
     *
     * @return
     */
    @Override
    public String getSearchKeywords() {
        return mSearchKeywords;
    }

    //endregion

}