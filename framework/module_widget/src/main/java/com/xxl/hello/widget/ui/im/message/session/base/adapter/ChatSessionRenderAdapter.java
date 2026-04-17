package com.xxl.hello.widget.ui.im.message.session.base.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseMultiAdapter;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.ui.im.message.session.base.actions.MessageLongClickAction;
import com.xxl.hello.widget.ui.im.message.session.base.actions.MessageLongClickActionManager;
import com.xxl.hello.widget.ui.im.message.session.base.menu.MessageLongClickMenu;
import com.xxl.hello.widget.ui.im.provider.CenterMessageProvider;
import com.xxl.hello.widget.ui.im.provider.LeftMessageProvider;
import com.xxl.hello.widget.ui.im.provider.RightMessageProvider;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;
import com.xxl.kit.ListUtils;
import com.xxl.kit.ToastUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 会话列表适配器
 * 多条目（区分左右消息）+消息渲染（不同消息类型）
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class ChatSessionRenderAdapter extends BaseMultiAdapter<MessageEntity, ChatSessionRecycleItemListener>
        implements OnMessageTemplateListener {

    //region: 成员变量

    //endregion

    //region: 构造函数

    @Inject
    public ChatSessionRenderAdapter() {
        super();
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void registerItemProvider() {
        registerItemProvider(new LeftMessageProvider(this));
        registerItemProvider(new RightMessageProvider(this));
        registerItemProvider(new CenterMessageProvider(this));
    }

    @Override
    protected int getItemType(@NonNull List<? extends MessageEntity> list, int position) {
        return list.get(position).getMessageDirection();
    }

    //endregion

    //region: OnMessageTemplateListener


    @Override
    public void onAvatarClick(@NonNull View targetView,
                              @NonNull String targetUserId,
                              @NonNull String targetNickname) {
        if (mListener != null) {
            mListener.onAvatarClick(targetView, targetUserId, targetNickname);
        }
    }

    @Override
    public boolean onAvatarLongClick(@NonNull View targetView,
                                     @NonNull String targetUserId,
                                     @NonNull String targetNickname) {
        if (mListener != null) {
            return mListener.onAvatarLongClick(targetView, targetUserId, targetNickname);
        }
        return false;
    }

    @Override
    public void onAvatarDoubleClick(@NonNull View targetView,
                                    @NonNull String targetUserId,
                                    @NonNull String targetNickname) {
        if (mListener != null) {
            mListener.onAvatarDoubleClick(targetView, targetUserId, targetNickname);
        }
    }

    @Override
    public boolean onMessageItemClick(MessageEntity messageEntity) {
        if (messageEntity.getMessageType() == MessageType.IMAGE) {
            WidgetRouterApi.MediaPreview.newBuilder()
                    .setMediaPreviewItem(messageEntity.getMediaPath())
                    .navigation();
            return true;
        }
        if (messageEntity.getMessageType() == MessageType.TEXT) {
            ToastUtils.success(messageEntity.getMessageText()).show();
            return true;
        }
        return false;
    }

    private MessageLongClickMenu mMessageLongClickMenu;

    @Override
    public boolean onMessageItemLongClick(View targetView,
                                          MessageEntity messageEntity) {
        List<MessageLongClickAction> actions = MessageLongClickActionManager.getInstance().getActions(ChatEnumsApi.SceneType.CHAT, messageEntity);
        if (!ListUtils.isEmpty(actions)) {
            mMessageLongClickMenu = MessageLongClickMenu.from(targetView)
                    .setItems(actions)
                    .setOnMenuItemClickListener(action -> {
                        if (mListener != null) {
                            mListener.onMessageMenuItemClick(action.getTag(), messageEntity);
                        }
                    });
            mMessageLongClickMenu.show();
            return true;
        }
        return false;
    }

    //endregion


}