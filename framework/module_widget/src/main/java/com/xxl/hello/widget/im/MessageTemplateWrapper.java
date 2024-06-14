package com.xxl.hello.widget.im;

import androidx.annotation.NonNull;

import com.xxl.hello.service.im.MessageTemplate;

import java.util.LinkedHashMap;

/**
 * 消息模版包装类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class MessageTemplateWrapper {

    private static final LinkedHashMap<String, MessageTemplateProvider> sMessageTemplateProviderMap = new LinkedHashMap<>();

    static {
        registerMessageTemplate(new TextMessageTemplateProvider());
        registerMessageTemplate(new ImageMessageTemplateProvider());
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
    }

    /**
     * 获取消息模板
     *
     * @param templateType
     * @param
     */
    public static MessageTemplateProvider getMessageTemplate(@NonNull String templateType) {
        return sMessageTemplateProviderMap.get(templateType);
    }

}