package com.xxl.hello.widget.ui.im.template;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.data.model.entity.im.ConversationEntity;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageTemplate;

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
        registerMessageTemplate(VideoMessageTemplateProvider.obtain());
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
     * 获取摘要内容
     *
     * @param conversationEntity
     * @return
     */
    public CharSequence getSummaryContent(@NonNull ConversationEntity conversationEntity) {
        // TODO: 2024/12/20 使用的时候，主要是消息列表，会话信息获取最后一条消息，显示摘要内容
        final MessageEntity lastMessageEntity = conversationEntity.getLastMessageEntity();
        if (lastMessageEntity != null) {
            MessageTemplateProvider provider = getMessageTemplateProvider(lastMessageEntity.getMessageTemplateType());
            if (provider != null) {
                return provider.getSummaryContent(lastMessageEntity);
            }
        }
        return "";
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