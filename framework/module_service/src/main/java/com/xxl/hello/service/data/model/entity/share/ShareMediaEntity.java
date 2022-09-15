package com.xxl.hello.service.data.model.entity.share;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.util.UUID;

/**
 * 分享多媒体信息
 *
 * @author xxl.
 * @date 2022/5/6.
 */
@Keep
public class ShareMediaEntity {

    //region: 成员变量

    /**
     * 多媒体ID
     */
    private String mMediaId;

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

    private ShareMediaEntity() {
        mMediaId = UUID.randomUUID().toString();
    }

    public final static ShareMediaEntity obtain() {
        return new ShareMediaEntity();
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

    /**
     * 设置媒体ID
     *
     * @param mediaId
     * @return
     */
    public ShareMediaEntity setMediaId(@NonNull final String mediaId) {
        this.mMediaId = mediaId;
        return this;
    }

    /**
     * 设置媒体路径
     *
     * @param mediaUrl
     * @return
     */
    public ShareMediaEntity setMediaUrl(@NonNull final String mediaUrl) {
        this.mMediaUrl = mediaUrl;
        return this;
    }

    /**
     * 设置是否是视频
     *
     * @param isVideo
     * @return
     */
    public ShareMediaEntity setVideo(boolean isVideo) {
        this.mVideo = isVideo;
        return this;
    }

    /**
     * 设置时常
     *
     * @param duration
     * @return
     */
    public ShareMediaEntity setDuration(final long duration) {
        this.mDuration = duration;
        return this;
    }

    /**
     * 设置宽
     *
     * @param width
     * @return
     */
    public ShareMediaEntity setWidth(final long width) {
        this.mWidth = width;
        return this;
    }

    /**
     * 设置高
     *
     * @param height
     * @return
     */
    public ShareMediaEntity setHeight(final long height) {
        this.mHeight = height;
        return this;
    }

    //endregion

}