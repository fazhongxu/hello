package com.xxl.hello.main.ui.main.adapter;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseMultiAdapter;
import com.xxl.hello.main.ui.main.adapter.provider.OnTestItemProviderListener;
import com.xxl.hello.main.ui.main.adapter.provider.TestImageProvider;
import com.xxl.hello.main.ui.main.adapter.provider.TestTextProvider;
import com.xxl.hello.main.ui.main.adapter.provider.TestVideoProvider;

import java.util.List;

import javax.inject.Inject;

/**
 * 测试多布局
 *
 * @author xxl.
 * @date 2022/10/10.
 */
public class TestMultiAdapter extends BaseMultiAdapter<TestProviderMultiEntity, OnTestRecycleItemListener>
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
    protected int getItemType(@NonNull List<? extends TestProviderMultiEntity> list,
                              int position) {
        TestProviderMultiEntity providerMultiEntity = list.get(position);
        return providerMultiEntity.getMediaType();
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