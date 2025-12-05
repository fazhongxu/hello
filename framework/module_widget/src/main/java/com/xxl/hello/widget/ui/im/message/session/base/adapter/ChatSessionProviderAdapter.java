package com.xxl.hello.widget.ui.im.message.session.base.adapter;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseMultiAdapter;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.ui.im.provider.TextMessageProvider;

import java.util.List;

/**
 * 会话列表适配器
 * 思路：多条目方式实现消息，多条目为2个 左边一个视图，右边一个视图，然后有个消息容器加载消息，也是通过类型绑定不同消息类型，
 * 这样的好处是减少消息里面的频繁设置背景这些，把设置背景抽取到外层调用
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class ChatSessionProviderAdapter extends BaseMultiAdapter<MessageEntity, ChatSessionRecycleItemListener> {

    //region: 成员变量

    /**
     * 时间间隔
     */
    private static final long TIME_SPAN = 3 * 60 * 1000L;

    //endregion

    //region: 构造函数

    public ChatSessionProviderAdapter() {
        super();
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void registerItemProvider() {
        registerItemProvider(TextMessageProvider.obtain());
    }

    @Override
    protected int getItemType(@NonNull List<? extends MessageEntity> list, int position) {
        return list.get(position).getMessageType();
    }

    //endregion

    //region: 页面视图渲染

    //endregion


}