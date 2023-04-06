package com.xxl.hello.service.data.model.entity.media;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

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

    //endregion

    //region: 构造函数

    private MediaPreviewItemEntity() {

    }

    public final static MediaPreviewItemEntity obtain() {
        return new MediaPreviewItemEntity();
    }

    //endregion

    //region: 提供方法

    public long getWidth() {
        return mWidth;
    }

    public long getHeight() {
        return mHeight;
    }

    public long getDuration() {
        return mDuration;
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

    //endregion

    //region: 内部辅助方法

    //endregion

}