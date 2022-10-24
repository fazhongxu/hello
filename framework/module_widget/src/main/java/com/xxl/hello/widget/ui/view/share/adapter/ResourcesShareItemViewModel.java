package com.xxl.hello.widget.ui.view.share.adapter;

import androidx.databinding.ObservableField;

import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;

/**
 * 资源分享
 *
 * @author xxl.
 * @date 2022/10/24.
 */
public class ResourcesShareItemViewModel {

    //region: 成员变量

    /**
     * 资源条目
     */
    private ShareOperateItem mTargetItemEntity;

    /**
     * 位置索引
     */
    private int mItemPosition;

    /**
     * 图标
     */
    private ObservableField<Integer> mObservableIcon = new ObservableField<>();

    /**
     * 标题
     */
    private ObservableField<String> mObservableTitle = new ObservableField<>();

    //endregion

    //region: 构造函数

    public ResourcesShareItemViewModel() {

    }

    //endregion

    //region: 提供方法

    public void setItemEntity(ShareOperateItem item,
                              int itemPosition) {
        mTargetItemEntity = item;
        mItemPosition = itemPosition;
        mObservableIcon.set(item.getIcon());
        mObservableTitle.set(item.getTitle());
    }

    public ShareOperateItem getTargetItemEntity() {
        return mTargetItemEntity;
    }

    public int getItemPosition() {
        return mItemPosition;
    }

    public ObservableField<Integer> getObservableIcon() {
        return mObservableIcon;
    }

    public ObservableField<String> getObservableTitle() {
        return mObservableTitle;
    }

    //endregion
}