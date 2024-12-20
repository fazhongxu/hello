package com.xxl.hello.widget.ui.im.template;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageTemplate;
import com.xxl.hello.service.data.model.entity.im.MessageTemplateType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageTextBinding;

/**
 * 文本消息模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@MessageTemplate(templateType = MessageTemplateType.TEXT)
public class TextMessageTemplateProvider extends MessageTemplateProvider {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public static TextMessageTemplateProvider obtain() {
        return new TextMessageTemplateProvider();
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取摘要内容
     *
     * @param messageEntity
     * @return
     */
    @Override
    public CharSequence getSummaryContent(@NonNull MessageEntity messageEntity) {
        return messageEntity.getMessageText();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.widget_recycle_item_message_text;
    }

    @Override
    public void bindView(@NonNull View rootView,
                         @NonNull MessageEntity messageEntity,
                         int position,
                         @Nullable OnMessageTemplateListener listener) {
        WidgetRecycleItemMessageTextBinding textBinding = DataBindingUtil.bind(rootView);
        int direction = messageEntity.getMessageDirection();
        if (direction == MessageDirection.LEFT) {
            textBinding.llItemContainer.setBackgroundResource(R.drawable.resources_bg_chat_text_left);
        } else {
            textBinding.llItemContainer.setBackgroundResource(R.drawable.resources_bg_chat_text_right);
        }
        textBinding.tvContent.setText(messageEntity.getMessageText());
        textBinding.executePendingBindings();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}