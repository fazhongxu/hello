package com.xxl.hello.widget.ui.im.template;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;

/**
 * 消息模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public abstract class MessageTemplateProvider {

    //region: 页面生命周期

    /**
     * 填充视图
     *
     * @param context
     * @param viewGroup
     * @return
     */
    public View inflate(@NonNull Context context,
                        @NonNull ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(getLayoutRes(), viewGroup, false);
    }

    /**
     * 获取摘要内容
     *
     * @param messageEntity
     * @return
     */
    public abstract CharSequence getSummaryContent(@NonNull MessageEntity messageEntity);

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