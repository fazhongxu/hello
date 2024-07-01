package com.xxl.hello.im.ui.message.session.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.core.widget.recyclerview.UISmartRefreshLayout;
import com.xxl.hello.im.BR;
import com.xxl.hello.im.R;
import com.xxl.hello.im.databinding.ImFragmentChatSessionBinding;
import com.xxl.hello.im.ui.message.session.base.adapter.ChatSessionAdapter;
import com.xxl.hello.widget.ui.view.keyboard.CommentKeyboardLayout;

import javax.inject.Inject;

/**
 * 会话基础类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public abstract class BaseChatSessionFragment<V extends BaseChatSessionViewModel<N>, N extends BaseChatSessionNavigator> extends BaseViewModelFragment<V, ImFragmentChatSessionBinding> {

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
        refreshLayout.bindRecyclerView(recyclerView,mChatSessionAdapter);
        CommentKeyboardLayout commonKeyboard = mChatSessionBinding.commonKeyboard;
        commonKeyboard.init(getActivity(),refreshLayout);
        commonKeyboard.show(null);
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}