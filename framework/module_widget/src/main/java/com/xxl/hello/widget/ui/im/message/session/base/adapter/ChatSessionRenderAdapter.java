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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * 是否处于多选模式
     */
    private boolean mInMultiSelectMode = false;

    /**
     * 选中的消息集合
     */
    private final Set<MessageEntity> mSelectedMessages = new HashSet<>();

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

    //region: 多选模式操作

    /**
     * 是否处于多选模式
     *
     * @return
     */
    public boolean isInMultiSelectMode() {
        return mInMultiSelectMode;
    }

    /**
     * 进入多选模式
     *
     * @param messageEntity 初始选中的消息
     */
    public void enterMultiSelectMode(@NonNull MessageEntity messageEntity) {
        mInMultiSelectMode = true;
        messageEntity.setSelected(true);
        mSelectedMessages.add(messageEntity);
        notifyDataSetChanged();
        notifySelectionChanged();
    }

    /**
     * 退出多选模式
     */
    public void exitMultiSelectMode() {
        mInMultiSelectMode = false;
        for (MessageEntity entity : mSelectedMessages) {
            entity.setSelected(false);
        }
        mSelectedMessages.clear();
        notifyDataSetChanged();
    }

    /**
     * 切换消息选中状态
     *
     * @param messageEntity
     */
    public void toggleSelection(@NonNull MessageEntity messageEntity) {
        if (messageEntity.isSelected()) {
            messageEntity.setSelected(false);
            mSelectedMessages.remove(messageEntity);
        } else {
            messageEntity.setSelected(true);
            mSelectedMessages.add(messageEntity);
        }
        notifyDataChanged(getItemPosition(messageEntity));
        notifySelectionChanged();
    }

    /**
     * 全选
     */
    public void selectAll() {
        List<MessageEntity> data = getData();
        if (data == null) {
            return;
        }
        for (MessageEntity entity : data) {
            if (!entity.isSelected()) {
                entity.setSelected(true);
                mSelectedMessages.add(entity);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 获取选中消息集合（按照列表展示顺序）
     *
     * @return 按列表顺序排列的选中消息列表
     */
    public List<MessageEntity> getSelectedMessages() {
        List<MessageEntity> allMessages = getData();
        if (ListUtils.isEmpty(allMessages)) {
            return allMessages;
        }
        List<MessageEntity> selectedMessages = new ArrayList<>();
        for (MessageEntity message : allMessages) {
            if (mSelectedMessages.contains(message)) {
                selectedMessages.add(message);
            }
        }
        return selectedMessages;
    }

    /**
     * 获取选中消息数量
     *
     * @return
     */
    public int getSelectedCount() {
        return mSelectedMessages.size();
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
        if (mInMultiSelectMode) {
            toggleSelection(messageEntity);
            return true;
        }
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
        if (mInMultiSelectMode) {
            return true;
        }
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

    //region: 内部辅助方法

    private void notifySelectionChanged() {
        if (mListener != null) {
            mListener.onSelectionChanged(getSelectedCount());
        }
    }

    //endregion

}
