package com.xxl.hello.main.ui.main.adapter;

import com.xxl.core.widget.recyclerview.adapter.BaseBindingAdapter;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainRecyclerItemTestBindingBinding;

import javax.inject.Inject;

/**
 * @author xxl.
 * @date 2022/10/10.
 */
public class TestBindingAdapter extends BaseBindingAdapter<String, TestBindingRecycleItemListener, MainRecyclerItemTestBindingBinding> {

    @Inject
    public TestBindingAdapter() {
        super(R.layout.main_recycler_item_test_binding);
    }

    @Override
    public void convert(MainRecyclerItemTestBindingBinding binding,
                        String item) {
        TestBindingRecycleItemViewModel viewModel = binding.getViewModel();
        if (viewModel == null) {
            viewModel = new TestBindingRecycleItemViewModel();
            binding.setViewModel(viewModel);
        }
        viewModel.setItemEntity(item);
        binding.setListener(mListener);
        binding.executePendingBindings();
    }

}