package com.xxl.hello.main.ui.main.adapter.provider;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainRecyclerItemTestVideoProviderBinding;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;
import com.xxl.hello.main.ui.main.adapter.TestRecycleItemViewModel;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.CircleMediaType;

/**
 * 测试视频数据类型条目
 *
 * @author xxl.
 * @date 2022/10/25.
 */
public class TestVideoProvider extends BaseItemProvider<TestListEntity> {

    //region: 成员变量

    private OnTestItemProviderListener mListener;

    //endregion

    //region: 构造函数

    private TestVideoProvider(@NonNull final OnTestItemProviderListener providerListener) {
        mListener = providerListener;
    }

    public final static TestVideoProvider obtain(@NonNull final OnTestItemProviderListener providerListener) {
        return new TestVideoProvider(providerListener);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public int getItemViewType() {
        return CircleMediaType.VIDEO;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_recycler_item_test_video_provider;
    }

    @Override
    public void convert(@NonNull BaseViewHolder baseViewHolder,
                        TestListEntity testListEntity) {
        MainRecyclerItemTestVideoProviderBinding binding = DataBindingUtil.bind(baseViewHolder.itemView);
        if (mListener != null) {
            binding.setListener(mListener.getMultiRecycleItemListener());
        }
        TestRecycleItemViewModel viewModel = binding.getViewModel();
        if (viewModel == null) {
            viewModel = new TestRecycleItemViewModel();
        }
        viewModel.setItemEntity(testListEntity);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    //endregion


}