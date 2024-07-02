package com.xxl.hello.im.ui.template;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.xxl.hello.im.R;
import com.xxl.hello.im.data.model.entity.MessageEntity;
import com.xxl.hello.im.data.model.entity.MessageTemplate;
import com.xxl.hello.im.data.model.entity.MessageTemplateType;
import com.xxl.hello.im.databinding.ImRecycleItemMessageTextBinding;

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

    @Override
    public int getLayoutRes() {
        return R.layout.im_recycle_item_message_text;
    }

    @Override
    public void bindView(@NonNull View rootView,
                         @NonNull MessageEntity messageEntity,
                         int position,
                         @Nullable OnMessageTemplateListener listener) {
        ImRecycleItemMessageTextBinding textBinding = DataBindingUtil.bind(rootView);
        textBinding.tvContent.setText(messageEntity.getMessageText());
        textBinding.executePendingBindings();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}