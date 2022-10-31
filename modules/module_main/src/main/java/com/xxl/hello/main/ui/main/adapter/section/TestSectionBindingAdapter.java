package com.xxl.hello.main.ui.main.adapter.section;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseSectionBindingAdapter;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainRecyclerItemTestSectionBinding;
import com.xxl.hello.main.databinding.MainRecyclerItemTestSectionHeaderBinding;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;

import javax.inject.Inject;

/**
 * @author xxl.
 * @date 2022/10/10.
 */
public class TestSectionBindingAdapter extends BaseSectionBindingAdapter<TestListEntity, TestSectionRecycleItemListener, MainRecyclerItemTestSectionHeaderBinding, MainRecyclerItemTestSectionBinding> {

    @Inject
    public TestSectionBindingAdapter() {
        super(R.layout.main_recycler_item_test_section_header, R.layout.main_recycler_item_test_section);
    }

    /**
     * convertHeader
     *
     * @param headerBinding
     * @param itemEntity
     */
    @Override
    public void convertHeader(@NonNull MainRecyclerItemTestSectionHeaderBinding headerBinding,
                              @NonNull TestListEntity itemEntity) {
        headerBinding.tvTitle.setText("header" + getItemPosition(itemEntity));
        headerBinding.executePendingBindings();
    }

    /**
     * convert
     *
     * @param itemBinding
     * @param itemEntity
     */
    @Override
    public void convert(@NonNull MainRecyclerItemTestSectionBinding itemBinding,
                        @NonNull TestListEntity itemEntity) {
        itemBinding.tvContent.setText(itemEntity.getContent());
        itemBinding.setListener(mListener);
        itemBinding.executePendingBindings();
    }

}