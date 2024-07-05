package com.xxl.hello.im.ui.message.session.base;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.core.widget.recyclerview.UISmartRefreshLayout;
import com.xxl.hello.im.BR;
import com.xxl.hello.im.R;
import com.xxl.hello.im.data.model.entity.MessageDirection;
import com.xxl.hello.im.data.model.entity.MessageEntity;
import com.xxl.hello.im.databinding.ImFragmentChatSessionBinding;
import com.xxl.hello.im.ui.message.session.base.adapter.ChatSessionAdapter;
import com.xxl.hello.widget.ui.view.keyboard.CommentKeyboardLayout;
import com.xxl.hello.widget.ui.view.keyboard.OnCommentKeyboardListener;

import java.util.Random;

import javax.inject.Inject;

/**
 * 会话基础类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public abstract class BaseChatSessionFragment<V extends BaseChatSessionViewModel<N>, N extends BaseChatSessionNavigator> extends BaseViewModelFragment<V, ImFragmentChatSessionBinding> implements OnCommentKeyboardListener {

    //region: 成员变量

    /**
     * 会话视图
     */
    private ImFragmentChatSessionBinding mChatSessionBinding;

    /**
     * 会话列表适配器
     */
    @Inject
    ChatSessionAdapter mChatSessionAdapter;

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.im_fragment_chat_session;
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
    protected void setupData() {

    }

    @Override
    protected void setupLayout(@NonNull View rootView) {
        mChatSessionBinding = getViewDataBinding();
        setupChatRecyclerView();
    }

    /**
     * 设置会话列表视图
     */
    protected void setupChatRecyclerView() {
        UISmartRefreshLayout refreshLayout = mChatSessionBinding.refreshLayout;
        RecyclerView recyclerView = mChatSessionBinding.rvList;
        refreshLayout.bindRecyclerView(recyclerView, mChatSessionAdapter);
        CommentKeyboardLayout commonKeyboard = mChatSessionBinding.commonKeyboard;
        commonKeyboard.setOnCommentKeyboardListener(this);
        commonKeyboard.init(getActivity(), refreshLayout);
        commonKeyboard.show(null);
    }

    //endregion

    //region: OnCommentKeyboardListener

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
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessageType(TextUtils.isEmpty(content) ? 2 : 1);
        messageEntity.setMessageDirection(new Random().nextInt(10) % 3 == 0 ? MessageDirection.LEFT : MessageDirection.RIGHT);
        messageEntity.setMessageText(content);
        mChatSessionAdapter.addData(messageEntity);

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

    //endregion

}