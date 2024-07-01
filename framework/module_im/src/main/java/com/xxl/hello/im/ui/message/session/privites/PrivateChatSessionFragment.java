package com.xxl.hello.im.ui.message.session.privites;

import androidx.annotation.Nullable;

import com.xxl.hello.im.ui.message.session.base.BaseChatSessionFragment;

import javax.inject.Inject;

/**
 * 单聊会话
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class PrivateChatSessionFragment extends BaseChatSessionFragment<PrivateChatSessionViewModel,PrivateChatSessionNavigator>
        implements PrivateChatSessionNavigator {

    //region: 成员变量

    /**
     * 单聊
     */
    @Inject
    PrivateChatSessionViewModel mChatSessionViewModel;

    //endregion

    //region: 构造函数

    public final static PrivateChatSessionFragment newInstance() {
        return new PrivateChatSessionFragment();
    }

    @Override
    protected PrivateChatSessionViewModel createViewModel() {
        mChatSessionViewModel = createViewModel(PrivateChatSessionViewModel.class);
        mChatSessionViewModel.setNavigator(this);
        return mChatSessionViewModel;
    }

    //endregion

    //region: 页面生命周期

    //endregion

    //region: OnCommentKeyboardListener

    /**
     * 发送点击
     *
     * @param content
     */
    @Override
    public void onSendClick(@Nullable CharSequence content) {
        // TODO: 2024/7/1
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}