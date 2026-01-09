package com.xxl.hello.widget.ui.im.message.session.base;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.entity.LocalMedia;
import com.xxl.core.image.selector.MediaSelector;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.core.widget.recyclerview.OnRefreshDataListener;
import com.xxl.core.widget.recyclerview.UISmartRefreshLayout;
import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.SDKMessage;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MenuOperateType;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.NotificationMessageType;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.SessionType;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetFragmentChatSessionBinding;
import com.xxl.hello.widget.ui.im.message.session.base.adapter.ChatSessionRecycleItemListener;
import com.xxl.hello.widget.ui.im.message.session.base.adapter.ChatSessionRenderAdapter;
import com.xxl.hello.widget.ui.im.message.session.base.menu.OnCopyOperate;
import com.xxl.hello.widget.ui.im.message.session.base.menu.OnDeleteOperate;
import com.xxl.hello.widget.ui.im.message.session.base.menu.OnMenuItemOperate;
import com.xxl.hello.widget.ui.im.message.session.base.menu.OnRecallOperate;
import com.xxl.hello.widget.ui.im.message.session.base.menu.OnShareOperate;
import com.xxl.hello.widget.ui.view.keyboard.CommonKeyboardLayout;
import com.xxl.hello.widget.ui.view.keyboard.OnCommonKeyboardListener;
import com.xxl.hello.widget.ui.view.plugin.impl.AlbumPlugin.AlbumPluginObservable;
import com.xxl.kit.LogUtils;
import com.xxl.kit.MimeType;
import com.xxl.kit.VibrateUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * 会话基础类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public abstract class BaseChatSessionFragment<V extends BaseChatSessionViewModel<N>, N extends BaseChatSessionNavigator> extends BaseViewModelFragment<V, WidgetFragmentChatSessionBinding>
        implements OnRefreshDataListener, OnCommonKeyboardListener, ChatSessionRecycleItemListener, AlbumPluginObservable {

    //region: 成员变量

    /**
     * 会话视图
     */
    private WidgetFragmentChatSessionBinding mChatSessionBinding;

    /**
     * 会话列表适配器
     */
//    @Inject
//    ChatSessionAdapter mChatSessionAdapter;

    @Inject
    ChatSessionRenderAdapter mChatSessionAdapter;

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.widget_fragment_chat_session;
    }

    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    @Override
    public int getViewNavigatorVariable() {
        return BR.navigator;
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CommonKeyboardLayout commonKeyboard = mChatSessionBinding.commonKeyboard;
        commonKeyboard.handleOnActivityResult(getActivity(), requestCode, resultCode, data);
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected void setupLayout(@NonNull View rootView) {
        mChatSessionBinding = getViewDataBinding();
        setupChatRecyclerView();
        setupMenu();
    }

    /**
     * 设置会话列表视图
     */
    protected void setupChatRecyclerView() {
        UISmartRefreshLayout refreshLayout = mChatSessionBinding.refreshLayout;
        RecyclerView recyclerView = mChatSessionBinding.rvList;
        mChatSessionAdapter.setListener(this);
        refreshLayout.setRefreshDataListener(this);
        refreshLayout.bindRecyclerView(recyclerView, mChatSessionAdapter);
        CommonKeyboardLayout commonKeyboard = mChatSessionBinding.commonKeyboard;
        commonKeyboard.setOnCommentKeyboardListener(this);
        commonKeyboard.init(getActivity(), refreshLayout);
        commonKeyboard.show(null);
    }

    private LinkedHashMap<String, OnMenuItemOperate> mMenuOperates = new LinkedHashMap<>();

    /**
     * 设置菜单
     */
    protected void setupMenu() {
        mMenuOperates.put(MenuOperateType.COPY, new OnCopyOperate());
        mMenuOperates.put(MenuOperateType.SHARE, new OnShareOperate());
        mMenuOperates.put(MenuOperateType.RECALL, new OnRecallOperate());
        mMenuOperates.put(MenuOperateType.DELETE, new OnDeleteOperate());
    }

    /**
     * 获取会话类型
     *
     * @return
     */
    @SessionType
    protected abstract int getSessionType();

    //endregion

    //region: OnRefreshDataListener

    /**
     * 请求数据
     *
     * @param page     页码
     * @param pageSize 每页记录条数
     */
    @Override
    public void onRequestData(int page,
                              int pageSize) {
        mChatSessionBinding.refreshLayout.finishRefresh();
    }

    //endregion

    //region: OnCommonKeyboardListener

    /**
     * 评论键盘视图展开
     */
    @Override
    public void onCommentLayoutExpand() {
        scrollToLastPosition();
    }

    /**
     * 发送点击
     *
     * @param content
     */
    @Override
    public void onSendClick(@Nullable String content) {
        SDKMessage sdkMessage = SDKMessage.obtain()
                .setSessionType(getSessionType())
                .setTextContent(content);
        MessageEntity messageEntity = MessageEntity.obtain(sdkMessage);
        messageEntity.setMessageType(MessageType.TEXT);
        messageEntity.setMessageDirection(new Random().nextInt(10) % 3 == 0 ? MessageDirection.LEFT : MessageDirection.RIGHT);
        mChatSessionAdapter.addData(messageEntity);

        scrollToLastPosition();
    }

    //endregion

    //region: ChatSessionRecycleItemListener

    /**
     * 头像点击
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    @Override
    public void onAvatarClick(@NonNull View targetView,
                              @NonNull String targetUserId,
                              @NonNull String targetNickname) {

    }

    /**
     * 头像双击
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    @Override
    public void onAvatarDoubleClick(@NonNull View targetView,
                                    @NonNull String targetUserId,
                                    @NonNull String targetNickname) {
        LogUtils.d("双击头像 " + targetNickname);
        VibrateUtils.vibrate();
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "rotation", 0, 6, -6, 4, -4, 2, -2, 0);
        targetView.setPivotX(targetView.getWidth() / 2F);
        targetView.setPivotY(targetView.getHeight() / 2F + 40);
        animator.setDuration(800);
        animator.start();
    }

    /**
     * 头像长按
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    @Override
    public boolean onAvatarLongClick(@NonNull View targetView,
                                     @NonNull String targetUserId,
                                     @NonNull String targetNickname) {
        return false;
    }

    /**
     * 消息菜单条目
     *
     * @param operateType
     * @param messageEntity
     */
    @Override
    public void onMessageMenuItemClick(@MenuOperateType String operateType,
                                       @NonNull MessageEntity messageEntity) {

        OnMenuItemOperate onMenuItemOperate = mMenuOperates.get(operateType);
        if (onMenuItemOperate != null) {
            onMenuItemOperate.handle(this, messageEntity);
        }
    }

    //endregion

    //region: AlbumPluginObservable

    private int mPreDirection = MessageDirection.LEFT;

    @Override
    public void handleAlbumPluginResult(final List<LocalMedia> targetMedias) {
        List<MessageEntity> messageEntities = new ArrayList<>();
        int direction = mPreDirection = mPreDirection == MessageDirection.LEFT ? MessageDirection.RIGHT : MessageDirection.LEFT;
        for (LocalMedia targetMedia : targetMedias) {
            SDKMessage sdkMessage = SDKMessage.obtain()
                    .setSessionType(getSessionType())
                    .setMediaPath(MediaSelector.getMediaPath(targetMedia));
            MessageEntity messageEntity = MessageEntity.obtain(sdkMessage);
            messageEntity.setMessageType(MimeType.isVideo(targetMedia.getMimeType()) ? MessageType.VIDEO : MessageType.IMAGE);
            messageEntity.setMessageDirection(direction);
            messageEntities.add(messageEntity);
        }
        mChatSessionAdapter.addData(messageEntities);
        mChatSessionBinding.commonKeyboard.hideExtendLayout();
        scrollToLastPosition();
    }

    //endregion

    //region: Fragment 方法

    /**
     * 滚动到最后一个位置
     */
    protected void scrollToLastPosition() {
        if (mChatSessionAdapter.getItemCount() - 1 >= 0) {
            mChatSessionBinding.rvList.getLayoutManager().scrollToPosition(mChatSessionAdapter.getItemCount() - 1);
        }
    }

    /**
     * 消息撤回
     *
     * @param messageEntity
     */
    public void onMessageRecallClick(MessageEntity messageEntity) {
        SDKMessage sdkMessage = SDKMessage.obtain()
                .setNotificationContent(NotificationMessageType.RECALL);

        MessageEntity targetMessageEntity = MessageEntity.obtain(sdkMessage);
        targetMessageEntity.setMessageType(MessageType.NOTIFICATION);
        targetMessageEntity.setMessageDirection(MessageDirection.CENTER);
        int position = mChatSessionAdapter.getItemPosition(messageEntity);
        mChatSessionAdapter.remove(messageEntity);
        mChatSessionAdapter.addData(position, targetMessageEntity);
    }

    /**
     * 消息删除
     *
     * @param messageEntity
     */
    public void onMessageDeleteClick(MessageEntity messageEntity) {
        mChatSessionAdapter.remove(messageEntity);
    }

    //endregion

}