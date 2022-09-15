package com.xxl.hello.service.data.model.entity.share;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;

/**
 * 资源分享操作条目
 *
 * @author xxl.
 * @date 2022/7/19.
 */
public class ShareOperateItem<T> {

    //region: 成员变量

    /**
     * 操作类型
     */
    private int mOperateType;

    /**
     * 标题
     */
    private String mTitle;

    /**
     * icon
     */
    @DrawableRes
    private int mIcon;

    /**
     * 点击操作
     */
    private OnItemHandle<T> mOnItemHandle;

    //endregion

    //region: 构造函数

    private ShareOperateItem() {

    }

    public final static ShareOperateItem obtain() {
        return new ShareOperateItem();
    }

    //endregion

    //region: 提供方法

    /**
     * 获取操作类型
     *
     * @return
     */
    @ShareOperateType
    public int getOperateType() {
        return mOperateType;
    }

    /**
     * 获取图标资源
     *
     * @return
     */
    public int getIcon() {
        return mIcon;
    }

    /**
     * 获取标题
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * 获取点击事件
     *
     * @return
     */
    public OnItemHandle<T> getOnItemHandle() {
        return mOnItemHandle;
    }

    public void onClick(T targetEntity) {
        if (mOnItemHandle != null) {
            mOnItemHandle.onClick(targetEntity);
        }
    }

    /**
     * 设置操作类型
     *
     * @param operateType
     * @return
     */
    public ShareOperateItem setOperateType(@ShareOperateType int operateType) {
        this.mOperateType = operateType;
        return this;
    }

    /**
     * 设置标题
     *
     * @param mTitle
     * @return
     */
    public ShareOperateItem setTitle(@Nullable String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    /**
     * 设置图标
     *
     * @param icon
     * @return
     */
    public ShareOperateItem setIcon(@DrawableRes int icon) {
        this.mIcon = icon;
        return this;
    }

    /**
     * 设置操作事件
     *
     * @param onItemHandle
     * @return
     */
    public ShareOperateItem setOnItemHandle(@NonNull final OnItemHandle<T> onItemHandle) {
        this.mOnItemHandle = onItemHandle;
        return this;
    }

    //endregion

    //region: Inner Class Action

    public interface OnItemHandle<T> {

        /**
         * 点击
         *
         * @param targetItem
         */
        void onClick(T targetItem);
    }

    //endregion

}