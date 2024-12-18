package com.xxl.hello.widget.ui.im.message.session.privites;

import com.xxl.hello.widget.ui.im.message.session.base.BaseChatSessionFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 单聊会话
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@AndroidEntryPoint
public class PrivateChatSessionFragment extends BaseChatSessionFragment<PrivateChatSessionViewModel,PrivateChatSessionNavigator>
        implements PrivateChatSessionNavigator {

    //region: 成员变量

    /**
     * 单聊
     */
    private PrivateChatSessionViewModel mChatSessionViewModel;

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

    //region: 内部辅助方法

    //endregion

}