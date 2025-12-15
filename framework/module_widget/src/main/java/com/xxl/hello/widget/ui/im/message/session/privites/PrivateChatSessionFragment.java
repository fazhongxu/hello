package com.xxl.hello.widget.ui.im.message.session.privites;

import com.xxl.hello.service.data.model.enums.ChatEnumsApi.SessionType;
import com.xxl.hello.widget.ui.im.message.session.base.BaseChatSessionFragment;

import javax.inject.Inject;

/**
 * 单聊会话
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class PrivateChatSessionFragment extends BaseChatSessionFragment<PrivateChatSessionViewModel, PrivateChatSessionNavigator>
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

    /**
     * 获取会话类型
     *
     * @return
     */
    @SessionType
    @Override
    public int getSessionType() {
        return SessionType.PRIVATE;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}