package com.xxl.hello.widget.ui.im.render;

import android.graphics.drawable.Drawable;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageUnknowBinding;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;
import com.xxl.kit.StringUtils;

/**
 * 未知消息渲染
 *
 * @author xxl.
 * @date 2025/12/5.
 */
public class UnKnowMessageRender extends BaseMessageRender<WidgetRecycleItemMessageUnknowBinding>   {

    //region: 构造函数

    private UnKnowMessageRender() {

    }

    public final static UnKnowMessageRender obtain() {
        return new UnKnowMessageRender();
    }

    //endregion

    //region: 生命周期

    /**
     * 获取消息标识
     *
     * @return
     */
    @Override
    public String getMessageTag() {
        return String.valueOf(MessageType.UNKNOW);
    }

    /**
     * 获取背景
     *
     * @param messageEntity
     * @return
     */
    @Override
    public Drawable getBackground(MessageEntity messageEntity) {
        return null;
    }

    @Override
    public int getLayoutRes() {
       return R.layout.widget_recycle_item_message_unknow;
    }

    @Override
    public void render(WidgetRecycleItemMessageUnknowBinding messageBinding, MessageEntity messageEntity, OnMessageTemplateListener listener) {
        messageBinding.tvContent.setText(StringUtils.getString(R.string.resources_current_version_not_support_this_message));
    }

    //endregion

}