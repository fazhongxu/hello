package com.xxl.hello.widget.ui.im.message.session.base.actions;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageType;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MenuOperateType;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.SceneType;
import com.xxl.hello.widget.R;
import com.xxl.kit.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息长按操作管理
 *
 * @author xxl.
 * @date 2025/6/30.
 */
public class MessageLongClickActionManager {

    //region: 成员变量

    private static MessageLongClickActionManager sInstance;

    private List<MessageLongClickAction> mActions;

    //endregion

    //region: 构造函数

    private MessageLongClickActionManager() {
        initActions();
    }

    public static MessageLongClickActionManager getInstance() {
        if (sInstance == null) {
            synchronized (MessageLongClickActionManager.class) {
                if (sInstance == null) {
                    sInstance = new MessageLongClickActionManager();
                }
            }
        }
        return sInstance;
    }

    //endregion

    //region: 初始化

    /**
     * 初始化
     */
    private void initActions() {
        mActions = new ArrayList<>();
        mActions.add(buildCopyAction());
        mActions.add(buildEmojAction());
        mActions.add(buildShareAction());
        mActions.add(buildDeleteAction());
        mActions.add(buildFavoriteAction());
    }

    /**
     * 复制
     *
     * @return
     */
    private MessageLongClickAction buildCopyAction() {
        return MessageLongClickAction.obtain(StringUtils.getString(R.string.resources_menu_copy), R.drawable.resources_ic_menu_copy, MenuOperateType.COPY)
                .setFilter(new MessageLongClickAction.Filter() {
                    @Override
                    public boolean filter(@SceneType int sceneType, MessageEntity messageEntity) {
                        return messageEntity.getMessageType() == MessageType.TEXT;
                    }
                });
    }

    /**
     * 添加表情
     *
     * @return
     */
    private MessageLongClickAction buildEmojAction() {
        return MessageLongClickAction.obtain(StringUtils.getString(R.string.resources_menu_emoj), R.drawable.resources_ic_menu_emoji,MenuOperateType.ADD_EMOTION)
                .setFilter(new MessageLongClickAction.Filter() {
                    @Override
                    public boolean filter(@SceneType int sceneType, MessageEntity messageEntity) {
                        return messageEntity.getMessageType() == MessageType.IMAGE;
                    }
                });
    }

    /**
     * 分享
     *
     * @return
     */
    private MessageLongClickAction buildShareAction() {
        return MessageLongClickAction.obtain(StringUtils.getString(R.string.resources_menu_share), R.drawable.resources_ic_menu_share,MenuOperateType.SHARE)
                .setFilter(new MessageLongClickAction.Filter() {
                    @Override
                    public boolean filter(@SceneType int sceneType, MessageEntity messageEntity) {
                        return true;
                    }
                });
    }

    /**
     * 删除
     *
     * @return
     */
    private MessageLongClickAction buildDeleteAction() {
        return MessageLongClickAction.obtain(StringUtils.getString(R.string.resources_menu_delete), R.drawable.resources_ic_menu_delete,MenuOperateType.DELETE)
                .setFilter(new MessageLongClickAction.Filter() {
                    @Override
                    public boolean filter(@SceneType int sceneType, MessageEntity messageEntity) {
                        return true;
                    }
                });
    }

    /**
     * 收藏
     *
     * @return
     */
    private MessageLongClickAction buildFavoriteAction() {
        return MessageLongClickAction.obtain(StringUtils.getString(R.string.resources_menu_favorite), R.drawable.resources_ic_menu_favorite,MenuOperateType.FAVORITE)
                .setFilter(new MessageLongClickAction.Filter() {
                    @Override
                    public boolean filter(@SceneType int sceneType, MessageEntity messageEntity) {
                        return true;
                    }
                });
    }

    //endregion

    //region: 提供方法

    /**
     * 获取操作集合
     *
     * @param sceneType
     * @param messageEntity
     * @return
     */
    public List<MessageLongClickAction> getActions(@SceneType int sceneType,
                                                   @NonNull MessageEntity messageEntity) {
        List<MessageLongClickAction> actions = new ArrayList<>();
        for (MessageLongClickAction action : mActions) {
            if (action.filter(sceneType,messageEntity)) {
                actions.add(action);
            }
        }
        return actions;
    }

    //endregion
}