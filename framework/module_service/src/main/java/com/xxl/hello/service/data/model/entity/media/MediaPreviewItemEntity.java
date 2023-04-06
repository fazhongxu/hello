package com.xxl.hello.service.data.model.entity.media;

import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 多媒体预览条目信息
 *
 * @author xxl.
 * @date 2023/04/06.
 */
@Keep
public class MediaPreviewItemEntity {

    //region: 成员变量

    /**
     * 多媒体地址
     */
    private String mMediaUrl;

    /**
     * 是否是视频
     */
    private boolean mVideo;

    /**
     * 多媒体时常
     */
    private long mDuration;

    /**
     * 多媒体高度
     */
    private long mWidth;

    /**
     * 多媒体高度
     */
    private long mHeight;

    /**
     * 开始横坐标
     */
    private int mStartX;

    /**
     * 开始纵坐标
     */
    private int mStartY;

    /**
     * 预览的宽度
     */
    private int mPreviewWidth;

    /**
     * 预览的高度
     */
    private int mPreviewHeight;

    /**
     * 位置
     */
    private int mPosition;

    //endregion

    //region: 构造函数

    private MediaPreviewItemEntity() {

    }

    public final static MediaPreviewItemEntity obtain() {
        return new MediaPreviewItemEntity();
    }

    //endregion

    //region: 提供方法

    public String getMediaUrl() {
        return mMediaUrl;
    }

    public long getWidth() {
        return mWidth;
    }

    public long getHeight() {
        return mHeight;
    }

    public long getDuration() {
        return mDuration;
    }

    public int getStartX() {
        return mStartX;
    }

    public int getStartY() {
        return mStartY;
    }

    public int getPreviewWidth() {
        return mPreviewWidth;
    }

    public int getPreviewHeight() {
        return mPreviewHeight;
    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * 设置媒体路径
     *
     * @param mediaUrl
     * @return
     */
    public MediaPreviewItemEntity setMediaUrl(@NonNull final String mediaUrl) {
        this.mMediaUrl = mediaUrl;
        return this;
    }

    /**
     * 设置是否是视频
     *
     * @param isVideo
     * @return
     */
    public MediaPreviewItemEntity setVideo(boolean isVideo) {
        this.mVideo = isVideo;
        return this;
    }

    /**
     * 设置时常
     *
     * @param duration
     * @return
     */
    public MediaPreviewItemEntity setDuration(final long duration) {
        this.mDuration = duration;
        return this;
    }

    /**
     * 设置宽
     *
     * @param width
     * @return
     */
    public MediaPreviewItemEntity setWidth(final long width) {
        this.mWidth = width;
        return this;
    }

    /**
     * 设置高
     *
     * @param height
     * @return
     */
    public MediaPreviewItemEntity setHeight(final long height) {
        this.mHeight = height;
        return this;
    }

    /**
     * 设置开始横坐标
     *
     * @param startX
     * @return
     */
    public MediaPreviewItemEntity setStartX(final int startX) {
        this.mStartX = startX;
        return this;
    }

    /**
     * 设置开始纵坐标
     *
     * @param startY
     * @return
     */
    public MediaPreviewItemEntity setStartY(final int startY) {
        this.mStartY = startY;
        return this;
    }

    /**
     * 设置位置索引
     *
     * @param position
     * @return
     */
    public MediaPreviewItemEntity setPosition(final int position) {
        this.mPosition = position;
        return this;
    }

    /**
     * 设置view的属性
     *
     * @param targetView
     * @return
     */
    public MediaPreviewItemEntity setTargetViewAttributes(@Nullable final View targetView) {
        if (targetView != null) {
            int[] location = new int[2];
            targetView.getLocationOnScreen(location);
            mStartX = location[0];
            mStartY = location[1];

            mPreviewWidth = targetView.getMeasuredWidth();
            mPreviewHeight = targetView.getMeasuredHeight();
        }
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}