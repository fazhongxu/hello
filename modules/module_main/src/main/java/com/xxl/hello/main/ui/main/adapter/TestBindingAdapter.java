package com.xxl.hello.main.ui.main.adapter;

import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.xxl.core.listener.OnTextChangeListener;
import com.xxl.core.widget.recyclerview.adapter.BaseItemDraggableBindingAdapter;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainRecyclerItemTestBindingBinding;
import com.xxl.kit.VibrateUtils;
import com.xxl.kit.ViewUtils;

import java.util.LinkedHashMap;

import javax.inject.Inject;

/**
 * @author xxl.
 * @date 2022/10/10.
 */
public class TestBindingAdapter extends BaseItemDraggableBindingAdapter<TestListEntity, TestBindingRecycleItemListener, MainRecyclerItemTestBindingBinding>
        implements OnItemDragListener {

    private LinkedHashMap<String, TestListEntity> mSelectedMap = new LinkedHashMap<>();

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

    private void setupEditText(MainRecyclerItemTestBindingBinding binding,
                               TestListEntity item) {
        // 实际上是EditText 这里假设为Text
        if (binding.tvContent.getTag() instanceof TextWatcher) {
            binding.tvContent.removeTextChangedListener((TextWatcher) binding.tvContent.getTag());
        }
        binding.tvContent.setText(TextUtils.isEmpty(item.getContent()) ? "" : item.getContent());
        TextWatcher textWatcher = new OnTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setContent(s != null ? s.toString() : "");
            }
        };
        binding.tvContent.addTextChangedListener(textWatcher);
        binding.tvContent.setTag(textWatcher);
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

    /**
     * 选择/取消选择条目（多选）
     *
     * @param entity
     */
    public void toggleSelection(TestListEntity entity) {
        if (isSelected(entity)) {
            mSelectedMap.remove(entity.getId());
        } else {
            mSelectedMap.put(entity.getId(), entity);
        }
        int position = getItemPosition(entity);
        if (position >= 0) {
            notifyDataChanged(position);
        }
    }

    private TestListEntity mSelectedItem;

    /**
     * 选择条目（单选）
     *
     * @param entity
     */
    public void selectItem(TestListEntity entity) {
        TestListEntity preSelectedItem = mSelectedItem;
        mSelectedItem = entity;
        if (preSelectedItem != null) {
            notifyDataChanged(preSelectedItem);
        }
        notifyDataChanged(mSelectedItem);
    }

    /**
     * 是否选中
     *
     * @param entity
     * @return
     */
    public boolean isSelected(TestListEntity entity) {
        return mSelectedMap.get(entity.getId()) != null || mSelectedItem == entity;
    }
}