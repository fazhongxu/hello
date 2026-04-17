package com.xxl.hello.main.ui.main.adapter;

import com.xxl.core.widget.recyclerview.adapter.BaseBindingAdapter;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainRecyclerItemVideoCoverBinding;

import javax.inject.Inject;

/**
 * 视频封面适配器
 *
 * @author xxl.
 * @date 2026/3/24.
 */
public class VideoCoverAdapter extends BaseBindingAdapter<VideoCoverEntity, VideoCoverRecycleItemListener, MainRecyclerItemVideoCoverBinding> {

    private VideoCoverEntity mSelectedItem;

    @Inject
    public VideoCoverAdapter() {
        super(R.layout.main_recycler_item_video_cover);
    }

    @Override
    public void convert(MainRecyclerItemVideoCoverBinding binding, VideoCoverEntity item) {
        VideoCoverRecycleItemViewModel viewModel = binding.getViewModel();
        if (viewModel == null) {
            viewModel = new VideoCoverRecycleItemViewModel();
            binding.setViewModel(viewModel);
        }

        binding.ivCover.setImageResource(item.getCoverResId());
        viewModel.setItemEntity(item);
        binding.setListener(mListener);
        binding.executePendingBindings();
    }

    /**
     * 选择条目（单选）
     *
     * @param entity
     */
    public void selectItem(VideoCoverEntity entity) {
        VideoCoverEntity preSelectedItem = mSelectedItem;
        if (preSelectedItem != null) {
            preSelectedItem.setSelected(false);
            notifyDataChanged(preSelectedItem);
        }
        mSelectedItem = entity;
        mSelectedItem.setSelected(true);
        notifyDataChanged(mSelectedItem);
    }

    /**
     * 是否选中
     *
     * @param entity
     * @return
     */
    public boolean isSelected(VideoCoverEntity entity) {
        return mSelectedItem == entity;
    }
}
