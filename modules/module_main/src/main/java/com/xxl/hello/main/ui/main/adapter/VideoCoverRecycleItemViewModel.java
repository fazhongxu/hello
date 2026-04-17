package com.xxl.hello.main.ui.main.adapter;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

/**
 * 视频封面条目ViewModel
 *
 * @author xxl.
 * @date 2026/3/24.
 */
public class VideoCoverRecycleItemViewModel {

    private VideoCoverEntity mTargetItemEntity;

    private ObservableField<String> mObservableTitle = new ObservableField<>();
    private ObservableField<String> mObservableSubtitle = new ObservableField<>();
    private ObservableBoolean mObservableSelected = new ObservableBoolean();

    public void setItemEntity(VideoCoverEntity itemEntity) {
        mTargetItemEntity = itemEntity;
        mObservableTitle.set(itemEntity.getTitle());
        mObservableSubtitle.set(itemEntity.getSubtitle());
        mObservableSelected.set(itemEntity.isSelected());
    }

    public VideoCoverEntity getTargetItemEntity() {
        return mTargetItemEntity;
    }

    public ObservableField<String> getObservableTitle() {
        return mObservableTitle;
    }

    public ObservableField<String> getObservableSubtitle() {
        return mObservableSubtitle;
    }

    public ObservableBoolean getObservableSelected() {
        return mObservableSelected;
    }
}
