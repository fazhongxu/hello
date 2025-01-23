package com.xxl.hello.widget.ui.im.message.list;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.ChatRouterApi;

/**
 * 消息列表
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@Route(path = ChatRouterApi.MessageList.PATH)
public class MessageActivity extends SingleFragmentBarActivity<MessageFragment>{

    //region: 成员变量

    //endregion

    //region: 页面生命周期

    @Override
    protected int getToolbarTitle() {
        return R.string.resources_message_title;
    }

    @Override
    public MessageFragment createFragment() {
        return MessageFragment.newInstance();
    }

    //endregion


    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}