package com.xxl.hello.widget.ui.im.message.session.base.adapter;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseBindingAdapter;
import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageTemplate;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.SceneType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetRecycleItemChatSessionBinding;
import com.xxl.hello.widget.ui.im.message.session.base.actions.MessageLongClickAction;
import com.xxl.hello.widget.ui.im.message.session.base.actions.MessageLongClickActionManager;
import com.xxl.hello.widget.ui.im.message.session.base.menu.MessageLongClickMenu;
import com.xxl.hello.widget.ui.im.template.MessageTemplateWrapper;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;
import com.xxl.kit.ListUtils;
import com.xxl.kit.TimeUtils;
import com.xxl.kit.ToastUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import java.util.List;

/**
 * 会话列表适配器
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class ChatSessionAdapter extends BaseBindingAdapter<MessageEntity, ChatSessionRecycleItemListener, WidgetRecycleItemChatSessionBinding>
        implements OnMessageTemplateListener {

    //region: 成员变量

    /**
     * 时间间隔
     */
    private static final long TIME_SPAN = 3 * 60 * 1000L;

    //endregion

    //region: 构造函数

    @Inject
    public ChatSessionAdapter() {
        super(R.layout.widget_recycle_item_chat_session);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void convert(@NonNull WidgetRecycleItemChatSessionBinding itemBinding,
                        @NonNull MessageEntity itemEntity) {
        ChatSessionRecycleItemViewModel viewModel = itemBinding.getViewModel();
        if (viewModel == null) {
            viewModel = new ChatSessionRecycleItemViewModel(itemEntity);
            itemBinding.setViewModel(viewModel);
        }
        itemBinding.setListener(mListener);
        setMessageLayout(itemBinding, itemEntity);
        itemBinding.executePendingBindings();
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置消息视图
     *
     * @param itemBinding
     * @param itemEntity
     */
    private void setMessageLayout(@NonNull WidgetRecycleItemChatSessionBinding itemBinding,
                                  @NonNull MessageEntity itemEntity) {
        View view = MessageTemplateWrapper.bindView(itemBinding.flMessageProviderLayout, itemEntity, getItemPosition(itemEntity), this);

        long messageTime = itemEntity.getMessageTime();
        MessageEntity preMessage = getPreMessage(itemEntity);
        boolean isShowTime = false;
        if (preMessage != null) {
            isShowTime = messageTime - preMessage.getMessageTime() > TIME_SPAN;
        }else {
            isShowTime = true;
        }

        if (isShowTime) {
            itemBinding.tvMessageTime.setText(TimeUtils.getChatTimeSpanByNow(messageTime));
            itemBinding.tvMessageTime.setVisibility(View.VISIBLE);
        } else {
            itemBinding.tvMessageTime.setText("");
            itemBinding.tvMessageTime.setVisibility(View.GONE);
        }
        setMessageGravity(itemBinding, itemEntity);
        setUserLayout(itemBinding, itemEntity);
    }

    /**
     * 获取前一条消息
     *
     * @param itemEntity
     * @return
     */
    private MessageEntity getPreMessage(@NonNull MessageEntity itemEntity) {
        int position = getItemPosition(itemEntity);
        if (position <= 0) {
            return null;
        }
        return getItem(position - 1);
    }

    /**
     * 设置消息对齐方式
     *
     * @param itemBinding
     * @param itemEntity
     */
    private void setMessageGravity(@NonNull WidgetRecycleItemChatSessionBinding itemBinding,
                                   @NonNull MessageEntity itemEntity) {
        MessageTemplate messageTemplate = MessageTemplateWrapper.getMessageTemplateAnnotation(itemEntity.getMessageTemplateType());
        if (messageTemplate.isCenterHorizontal()) {
            itemBinding.flMessageProviderLayout.setChildGravityCenter();
        } else {
            if (itemEntity.getMessageDirection() == MessageDirection.LEFT) {
                itemBinding.flMessageProviderLayout.setChildGravityLeft();
            } else {
                itemBinding.flMessageProviderLayout.setChildGravityRight();
            }
        }
    }

    /**
     * 设置用户信息视图
     *
     * @param itemBinding
     * @param itemEntity
     */
    private void setUserLayout(@NonNull WidgetRecycleItemChatSessionBinding itemBinding,
                               @NonNull MessageEntity itemEntity) {
        if (itemEntity.getMessageDirection() == MessageDirection.LEFT) {
            itemBinding.ivLeftAvatar.setVisibility(View.VISIBLE);
            itemBinding.ivRightAvatar.setVisibility(View.INVISIBLE);
            itemBinding.tvLeftNickname.setVisibility(View.VISIBLE);
            itemBinding.tvRightNickname.setVisibility(View.INVISIBLE);
        } else {
            itemBinding.ivLeftAvatar.setVisibility(View.INVISIBLE);
            itemBinding.ivRightAvatar.setVisibility(View.VISIBLE);
            itemBinding.tvLeftNickname.setVisibility(View.INVISIBLE);
            itemBinding.tvRightNickname.setVisibility(View.VISIBLE);
        }
        setUserAvatarListener(itemBinding.ivLeftAvatar, itemEntity);
        setUserAvatarListener(itemBinding.ivRightAvatar, itemEntity);
    }

    /**
     * 设置头像事件监听
     *
     * @param targetView
     * @param itemEntity
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setUserAvatarListener(@NonNull View targetView,
                                       @NonNull MessageEntity itemEntity) {
        GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mListener != null) {
                    mListener.onAvatarClick(targetView, itemEntity.getSenderId(), itemEntity.getSenderNickname());
                }
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mListener != null) {
                    mListener.onAvatarDoubleClick(targetView, itemEntity.getSenderId(), itemEntity.getSenderNickname());
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (mListener != null) {
                    mListener.onAvatarLongClick(targetView, itemEntity.getSenderId(), itemEntity.getSenderNickname());
                }
            }
        });
        targetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    //endregion

    //region: OnMessageTemplateListener

    @Override
    public boolean onMessageItemClick(MessageEntity messageEntity) {
        if (messageEntity.getMessageType() == 2) {
            WidgetRouterApi.MediaPreview.newBuilder()
                    .setMediaPreviewItem(messageEntity.getMediaPath())
                    .navigation();
            return true;
        }
        if (messageEntity.getMessageType() == 1) {
            ToastUtils.success(messageEntity.getMessageText()).show();
            return true;
        }
        return false;
    }

    private MessageLongClickMenu mMessageLongClickMenu;

    @Override
    public boolean onMessageItemLongClick(View targetView,
                                          MessageEntity messageEntity) {
        List<MessageLongClickAction> actions = MessageLongClickActionManager.getInstance().getActions(SceneType.CHAT, messageEntity);
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