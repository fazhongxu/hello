package com.xxl.hello.im.ui.template;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.im.data.model.entity.MessageEntity;

/**
 * 消息模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public abstract class MessageTemplateProvider {

    //region: 页面生命周期

    /**
     * 获取视图
     *
     * @return
     */
    public abstract int getLayoutRes();

    /**
     * 绑定视图
     *
     * @param rootView
     * @param messageEntity
     * @param position
     * @param listener
     */
    public abstract void bindView(@NonNull View rootView,
                                  @NonNull MessageEntity messageEntity,
                                  int position,
                                  @Nullable OnMessageTemplateListener listener);
    //endregion

    //region: 提供方法

    //endregion
}