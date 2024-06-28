package com.xxl.hello.im.ui.message.session.privites;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.im.R;
import com.xxl.hello.im.data.router.ChatRouterApi.PrivateChat;

/**
 * 单聊会话
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@Route(path = PrivateChat.PATH)
public class PrivateChatSessionActivity extends SingleFragmentBarActivity<PrivateChatSessionFragment> {

    //region: 成员变量

    //endregion

    //region: 页面生命周期

    @Override
    protected int getToolbarTitle() {
        return R.string.resources_private_chat_title;
    }

    @Override
    public PrivateChatSessionFragment createFragment() {
        return PrivateChatSessionFragment.newInstance();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}