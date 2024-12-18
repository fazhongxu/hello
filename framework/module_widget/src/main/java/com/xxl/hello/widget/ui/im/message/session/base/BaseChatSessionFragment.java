package com.xxl.hello.widget.ui.im.message.session.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.core.widget.recyclerview.OnRefreshDataListener;
import com.xxl.core.widget.recyclerview.UISmartRefreshLayout;
import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetFragmentChatSessionBinding;
import com.xxl.hello.widget.ui.im.message.session.base.adapter.ChatSessionAdapter;
import com.xxl.hello.widget.ui.view.keyboard.CommentKeyboardLayout;
import com.xxl.hello.widget.ui.view.keyboard.OnCommentKeyboardListener;
import com.xxl.kit.StringUtils;

import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 会话基础类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public abstract class BaseChatSessionFragment<V extends BaseChatSessionViewModel<N>, N extends BaseChatSessionNavigator> extends BaseViewModelFragment<V, WidgetFragmentChatSessionBinding> implements OnRefreshDataListener, OnCommentKeyboardListener {

    //region: 成员变量

    /**
     * 会话视图
     */
    private WidgetFragmentChatSessionBinding mChatSessionBinding;

    /**
     * 会话列表适配器
     */
    @Inject
    ChatSessionAdapter mChatSessionAdapter;

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
        refreshLayout.setRefreshDataListener(this);
        refreshLayout.bindRecyclerView(recyclerView, mChatSessionAdapter);
        CommentKeyboardLayout commonKeyboard = mChatSessionBinding.commonKeyboard;
        commonKeyboard.setOnCommentKeyboardListener(this);
        commonKeyboard.init(getActivity(), refreshLayout);
        commonKeyboard.show(null);
    }

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
        MessageEntity messageEntity = new MessageEntity(null);
        messageEntity.setMessageType(StringUtils.isTrimEmpty(content) ? 2 : 1);
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