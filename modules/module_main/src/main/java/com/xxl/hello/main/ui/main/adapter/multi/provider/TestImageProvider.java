package com.xxl.hello.main.ui.main.adapter.multi.provider;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainRecyclerItemTestImageProviderBinding;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;
import com.xxl.hello.main.ui.main.adapter.multi.TestRecycleItemViewModel;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.CircleMediaType;

/**
 * 测试图片数据类型条目
 *
 * @author xxl.
 * @date 2022/10/25.
 */
public class TestImageProvider extends BaseItemProvider<TestListEntity> {

    //region: 成员变量

    private OnTestItemProviderListener mListener;

    private boolean mIsExpand;

    //endregion

    //region: 构造函数

    private TestImageProvider(@NonNull final OnTestItemProviderListener providerListener,boolean isExpand) {
        mListener = providerListener;
        mIsExpand = isExpand;
    }

    public final static TestImageProvider obtain(@NonNull final OnTestItemProviderListener providerListener,boolean isExpand) {
        return new TestImageProvider(providerListener,isExpand);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public int getItemViewType() {
        return CircleMediaType.IMAGE;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_recycler_item_test_image_provider;
    }

    @Override
    public void convert(@NonNull BaseViewHolder baseViewHolder,
                        TestListEntity testListEntity) {
        MainRecyclerItemTestImageProviderBinding binding = DataBindingUtil.bind(baseViewHolder.itemView);
        if (mListener != null) {
            binding.setListener(mListener.getMultiRecycleItemListener());
        }

        if (testListEntity.mPosition > 8 && !mIsExpand){
            binding.llItemContainer.setVisibility(View.GONE);
        }else {
            binding.llItemContainer.setVisibility(View.VISIBLE);
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