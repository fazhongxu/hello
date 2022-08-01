package com.xxl.hello.service.data.model.entity.share;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xxl.kit.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片分享数据
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public class ImageShareResourceEntity extends BaseShareResourceEntity<ImageShareResourceEntity> {

    //region: 成员变量

    /**
     * 多媒体数据
     */
    private List<ShareMediaEntity> mShareMediaEntities;

    //endregion

    //region: 构造函数

    private ImageShareResourceEntity() {

    }

    public final static ImageShareResourceEntity obtain() {
        return new ImageShareResourceEntity();
    }

    //endregion

    //region: 提供方法

    /**
     * 获取多媒体地址
     *
     * @return
     */
    public List<String> getShareMediaUrls() {
        final List<String> mediaUrls = new ArrayList<>();
        if (!ListUtils.isEmpty(mShareMediaEntities)) {
            for (ShareMediaEntity shareMediaEntity : mShareMediaEntities) {
                if (TextUtils.isEmpty(shareMediaEntity.getMediaUrl())) {
                    mediaUrls.add(shareMediaEntity.getMediaUrl());
                }
            }
        }
        return mediaUrls;
    }

    /**
     * 获取多媒体数据
     *
     * @return
     */
    public List<ShareMediaEntity> getShareMediaEntities() {
        return mShareMediaEntities;
    }

    /**
     * 设置多媒体数据
     *
     * @param mediaEntities
     * @return
     */
    public ImageShareResourceEntity setShareMediaEntities(@NonNull final List<ShareMediaEntity> mediaEntities) {
        mShareMediaEntities = mediaEntities;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}