package com.xxl.hello.main.ui.main.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.xxl.core.widget.recyclerview.adapter.BaseItemDraggableBindingAdapter;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainRecyclerItemTestBindingBinding;
import com.xxl.kit.VibrateUtils;
import com.xxl.kit.ViewUtils;

import javax.inject.Inject;

/**
 * @author xxl.
 * @date 2022/10/10.
 */
public class TestBindingAdapter extends BaseItemDraggableBindingAdapter<TestListEntity, TestBindingRecycleItemListener, MainRecyclerItemTestBindingBinding>
        implements OnItemDragListener {

    @Inject
    public TestBindingAdapter() {
        super(R.layout.main_recycler_item_test_binding);
    }

    @Override
    public void convert(MainRecyclerItemTestBindingBinding binding,
                        TestListEntity item) {
        TestBindingRecycleItemViewModel viewModel = binding.getViewModel();
        if (viewModel == null) {
            viewModel = new TestBindingRecycleItemViewModel();
            binding.setViewModel(viewModel);
        }

        viewModel.setItemEntity(item);
        binding.setListener(mListener);
        ViewUtils.setOnClickListener(binding.tvSetTop, v -> {
            binding.swipeMenuLayout.resetStatus();
            if (mListener != null) {
                mListener.onTopItemClick(item);
            }
        });
        ViewUtils.setOnClickListener(binding.tvRefreshTop, v -> {
            binding.swipeMenuLayout.resetStatus();
            if (mListener != null) {
                mListener.onRefreshTopItemClick(item);
            }
        });
        binding.executePendingBindings();
        setOnItemDragListener(this);
    }

    //region: OnItemDragListener

    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        VibrateUtils.vibrate();
    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    //endregion
}