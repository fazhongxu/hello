package com.xxl.hello.widget.ui.im.provider;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.ui.im.message.session.base.adapter.ChatSessionRenderAdapter;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;
import com.xxl.kit.TimeUtils;

/**
 * 模板提供基础类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public abstract class BaseMessageProvider<Binding extends ViewDataBinding, T extends MessageEntity> extends BaseItemProvider<T> {

    //region: 成员变量

    /**
     * 时间间隔（分钟）
     */
    private static final long TIME_SPAN = 3 * 60 * 1000L;

    protected ChatSessionRenderAdapter mAdapter;

    protected OnMessageTemplateListener mListener;

    //endregion

    //region: 构造函数

    public BaseMessageProvider(ChatSessionRenderAdapter adapter, OnMessageTemplateListener listener) {
        mAdapter = adapter;
        mListener = listener;
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void convert(@NonNull BaseViewHolder viewHolder, MessageEntity messageEntity) {
        ViewDataBinding itemBinding = DataBindingUtil.bind(viewHolder.itemView);
        convert((Binding) itemBinding, messageEntity);
        itemBinding.executePendingBindings();
    }

    public abstract void convert(@NonNull Binding itemBinding, MessageEntity itemEntity);

    /**
     * 获取前一条消息
     *
     * @param itemEntity
     * @return
     */
    private MessageEntity getPreMessage(@NonNull MessageEntity itemEntity) {
        int position = mAdapter.getItemPosition(itemEntity);
        if (position <= 0) {
            return null;
        }
        return mAdapter.getItem(position - 1);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置消息时间
     *
     * @param tvMessageTime
     * @param itemEntity
     */
    protected void setupMessageTime(@NonNull TextView tvMessageTime,
                                    @NonNull MessageEntity itemEntity) {
        long messageTime = itemEntity.getMessageTime();
        MessageEntity preMessage = getPreMessage(itemEntity);
        boolean isShowTime = false;
        if (preMessage != null) {
            isShowTime = messageTime - preMessage.getMessageTime() > TIME_SPAN;
        }else {
            isShowTime = true;
        }

        if (isShowTime) {
            tvMessageTime.setText(TimeUtils.getChatTimeSpanByNow(messageTime));
            tvMessageTime.setVisibility(View.VISIBLE);
        } else {
            tvMessageTime.setText("");
            tvMessageTime.setVisibility(View.GONE);
        }
    }

    /**
     * 设置头像事件监听
     *
     * @param targetView
     * @param itemEntity
     */
    @SuppressLint("ClickableViewAccessibility")
    protected void setUserAvatarListener(@NonNull View targetView,
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
}