package com.xxl.hello.widget.ui.view.share.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.xxl.core.widget.recyclerview.adapter.BaseBindingAdapter;
import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecyclerItemResourcesShareBinding;

import org.jetbrains.annotations.NotNull;

/**
 * 资源分享适配器
 *
 * @author xxl.
 * @date 2022/10/24.
 */
public class ResourcesShareAdapter extends BaseBindingAdapter<ShareOperateItem,ResourcesShareItemListener, WidgetRecyclerItemResourcesShareBinding> {

    //region: 构造函数

    public ResourcesShareAdapter() {
        super(R.layout.widget_recycler_item_resources_share);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void convert(@NonNull final WidgetRecyclerItemResourcesShareBinding binding,
                        @NonNull final ShareOperateItem item) {

        ResourcesShareItemViewModel viewModel = binding.getViewModel();
        if (viewModel == null) {
            viewModel = new ResourcesShareItemViewModel();
            binding.setViewModel(viewModel);
        }
        viewModel.setItemEntity(item,getItemPosition(item));
        binding.setListener(mListener);
        binding.executePendingBindings();
    }

    //endregion

    //region: 页面视图渲染


    //endregion

}