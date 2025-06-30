package com.xxl.hello.widget.ui.im.message.session.base.actions;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.SceneType;

/**
 * 消息长按操作
 *
 * @author xxl.
 * @date 2025/6/30.
 */
public class MessageLongClickAction {

    //region: 成员变量

    /**
     * 标题
     */
    private CharSequence mTitle;

    /**
     * 图标资源
     */
    private int mIconRes;

    /**
     * 过滤器
     */
    private Filter mFilter;

    //endregion

    //region: 构造函数

    private MessageLongClickAction(@NonNull CharSequence title,
                                   @DrawableRes int iconRes) {
        mTitle = title;
        mIconRes = iconRes;
    }

    public final static MessageLongClickAction obtain(@NonNull CharSequence title,
                                                      @DrawableRes int iconRes) {
        return new MessageLongClickAction(title, iconRes);
    }

    //endregion

    //region: 提供方法

    /**
     * 获取标题
     *
     * @return
     */
    public CharSequence getTitle() {
        return mTitle;
    }

    /**
     * 获取图标
     *
     * @return
     */
    public int getIconRes() {
        return mIconRes;
    }

    /**
     * 设置过滤器
     *
     * @param filter
     * @return
     */
    public MessageLongClickAction setFilter(@NonNull Filter filter) {
        mFilter = filter;
        return this;
    }

    /**
     * 过滤
     *
     * @param sceneType
     * @param messageEntity
     * @return
     */
    public boolean filter(@SceneType int sceneType, MessageEntity messageEntity) {
        return mFilter != null && mFilter.filter(sceneType, messageEntity);
    }

    //endregion

    //region: Inner Class Filter

    public interface Filter {
        /**
         * 过滤
         *
         * @param sceneType
         * @param messageEntity
         * @return
         */
        boolean filter(@SceneType int sceneType, MessageEntity messageEntity);
    }

    //endregion

}