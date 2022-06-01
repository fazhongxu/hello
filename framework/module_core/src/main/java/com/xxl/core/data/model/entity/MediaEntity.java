package com.xxl.core.data.model.entity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 多媒体信息
 *
 * @author xxl.
 * @date 2022/5/6.
 */
@Keep
@Accessors(prefix = "m")
@Getter
public class MediaEntity {

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

    private MediaEntity() {
        mMediaId = UUID.randomUUID().toString();
    }

    public final static MediaEntity obtain() {
        return new MediaEntity();
    }

    //endregion

    //region: 提供方法

//    public long getWidth() {
//        return mWidth;
//    }
//
//    public long getHeight() {
//        return mHeight;
//    }
//
//    public long getDuration() {
//        return mDuration;
//    }

    /**
     * 设置媒体ID
     *
     * @param mediaId
     * @return
     */
    public MediaEntity setMediaId(@NonNull final String mediaId) {
        this.mMediaId = mediaId;
        return this;
    }

    /**
     * 设置媒体路径
     *
     * @param mediaUrl
     * @return
     */
    public MediaEntity setMediaUrl(@NonNull final String mediaUrl) {
        this.mMediaUrl = mediaUrl;
        return this;
    }

    /**
     * 设置是否是视频
     *
     * @param isVideo
     * @return
     */
    public MediaEntity setVideo(boolean isVideo) {
        this.mVideo = isVideo;
        return this;
    }

    /**
     * 设置时常
     *
     * @param duration
     * @return
     */
    public MediaEntity setDuration(final long duration) {
        this.mDuration = duration;
        return this;
    }

    /**
     * 设置宽
     *
     * @param width
     * @return
     */
    public MediaEntity setWidth(final long width) {
        this.mWidth = width;
        return this;
    }

    /**
     * 设置高
     *
     * @param height
     * @return
     */
    public MediaEntity setHeight(final long height) {
        this.mHeight = height;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}