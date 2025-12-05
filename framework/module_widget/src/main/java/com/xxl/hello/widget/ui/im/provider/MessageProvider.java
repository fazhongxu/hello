package com.xxl.hello.widget.ui.im.provider;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;

/**
 * @author xxl.
 * @date 2025/12/3.
 */
public interface MessageProvider {

    /**
     * 获取背景资源
     */
    @DrawableRes
    int getBackgroundDrawableRes(MessageEntity message);

    /**
     * 绑定视图
     */
    void bindView(Context context, FrameLayout container, MessageEntity message);
}