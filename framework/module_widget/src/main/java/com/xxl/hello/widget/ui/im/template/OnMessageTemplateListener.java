package com.xxl.hello.widget.ui.im.template;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;

/**
 * 模板监听事件
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public interface OnMessageTemplateListener {

    default boolean onMessageItemClick(MessageEntity messageEntity) {
       return false;
    }
}