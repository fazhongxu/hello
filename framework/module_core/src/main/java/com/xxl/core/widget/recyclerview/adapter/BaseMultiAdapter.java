package com.xxl.core.widget.recyclerview.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * 多条目类型
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseMultiAdapter<T, L extends BaseRecycleItemListener> extends BaseProviderMultiAdapter<T>
        implements LoadMoreModule {

    //region: 成员变量

    /**
     * 点击事件
     */
    protected L mListener;

    //endregion

    //region: 构造函数

    public BaseMultiAdapter() {
        registerItemProvider();
    }

    /**
     * 注册条目提供者
     */
    public void registerItemProvider() {

    }

    /**
     * 必须通过此方法，添加 provider
     *
     * @param provider BaseItemProvider
     */
    public void registerItemProvider(BaseItemProvider<T> provider) {
        addItemProvider(provider);
    }

    //endregion

    //region: LoadMoreModule

    @Override
    @NonNull
    public BaseLoadMoreModule addLoadMoreModule(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseLoadMoreModule(baseQuickAdapter);
    }

    //endregion

    //region: 提供方法

    /**
     * 判断数据是否为空
     *
     * @return
     */
    public boolean isDataEmpty() {
        return getData() == null || getData().size() == 0;
    }

    /**
     * 获取条目位置
     *
     * @param targetItemEntity
     * @return
     */
    public int findItemPositon(T targetItemEntity) {
        return getItemPosition(targetItemEntity);
    }

    public void setListener(L listener) {
        mListener = listener;
    }

    //endregion


}