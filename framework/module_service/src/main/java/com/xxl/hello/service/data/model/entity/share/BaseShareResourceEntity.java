package com.xxl.hello.service.data.model.entity.share;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xxl.kit.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源分享实体基础类
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public abstract class BaseShareResourceEntity<T extends BaseShareResourceEntity> {

    //region: 成员变量

    /**
     * 多媒体数据
     */
    private List<ShareMediaEntity> mShareMediaEntities;

    /**
     * 描述文本
     */
    private String mDescription;

    /**
     * 链接地址
     */
    private String mLinkUrl;

    //endregion

    //region: 构造函数

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
     * 获取描述文本
     *
     * @return
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * 设置多媒体数据
     *
     * @param mediaEntities
     * @return
     */
    public T setShareMediaEntities(@NonNull final List<ShareMediaEntity> mediaEntities) {
        mShareMediaEntities = mediaEntities;
        return (T) this;
    }

    /**
     * 设置描述文本
     *
     * @param description
     * @return
     */
    public T setDescription(@NonNull final String description) {
        this.mDescription = description;
        return (T) this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}