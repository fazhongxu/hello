package com.xxl.hello.widget.ui.im.render;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;

/**
 * @author xxl.
 * @date 2025/12/10.
 */
public abstract class BaseMessageRender<Binding extends ViewDataBinding> implements MessageRender {

    //region: 生命周期方法

    /**
     * 渲染
     *
     * @param context
     * @param container
     * @param messageEntity
     * @param listener
     * @return
     */
    @Override
    public View render(Context context, FrameLayout container, MessageEntity messageEntity, OnMessageTemplateListener listener) {
        Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context), getResLayout(), container, false);
        container.addView(binding.getRoot());
        render(binding, messageEntity, listener);
        return binding.getRoot();
    }

    /**
     * 获取资源视图
     *
     * @return
     */
    public abstract int getResLayout();

    /**
     * 渲染
     *
     * @param messageBinding
     * @param messageEntity
     * @param listener
     */
    public abstract void render(Binding messageBinding, MessageEntity messageEntity, OnMessageTemplateListener listener);

    //endregion
}