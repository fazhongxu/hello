package com.xxl.hello.im.ui.template;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.im.data.model.entity.MessageEntity;
import com.xxl.hello.im.data.model.entity.MessageTemplate;

import java.util.LinkedHashMap;

/**
 * 消息模版包装类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class MessageTemplateWrapper {

    private static final LinkedHashMap<String, MessageTemplateProvider> sMessageTemplateProviderMap = new LinkedHashMap<>();

    private static final LinkedHashMap<String, MessageTemplate> sMessageTemplaterMap = new LinkedHashMap<>();

    static {
        registerMessageTemplate(TextMessageTemplateProvider.obtain());
        registerMessageTemplate(ImageMessageTemplateProvider.obtain());
    }

    /**
     * 注册消息模板
     *
     * @param provider
     */
    public static void registerMessageTemplate(MessageTemplateProvider provider) {
        MessageTemplate template = provider.getClass().getAnnotation(MessageTemplate.class);
        if (template == null) {
            throw new RuntimeException("MessageTemplate missing '@MessageTemplate' annotation !");
        }
        sMessageTemplateProviderMap.put(template.templateType(), provider);
        sMessageTemplaterMap.put(template.templateType(), template);
    }

    /**
     * 获取消息模板
     *
     * @param templateType
     * @param
     */
    public static MessageTemplateProvider getMessageTemplateProvider(@NonNull String templateType) {
        return sMessageTemplateProviderMap.get(templateType);
    }

    /**
     * 获取消息模板注解
     *
     * @param templateType
     * @return
     */
    public static MessageTemplate getMessageTemplateAnnotation(@NonNull String templateType) {
        return sMessageTemplaterMap.get(templateType);
    }

    /**
     * 绑定视图
     *
     * @param layout
     * @param messageEntity
     * @param position
     * @param listener
     * @return
     */
    public static View bindView(@NonNull MessageTemplateProviderLayout layout,
                                @NonNull MessageEntity messageEntity,
                                int position,
                                @Nullable OnMessageTemplateListener listener) {
        MessageTemplateProvider provider = getMessageTemplateProvider(messageEntity.getMessageTemplateType());
        if (provider != null) {
            View targetView = layout.inflate(provider);
            if (targetView != null) {
                provider.bindView(targetView, messageEntity, position, listener);
            }
            return targetView;
        }
        return null;
    }

}