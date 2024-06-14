package com.xxl.hello.widget.im;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.im.MessageEntity;
import com.xxl.hello.service.im.MessageTemplate;
import com.xxl.hello.service.im.MessageTemplateType;
import com.xxl.hello.widget.R;

/**
 * 图片消息模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@MessageTemplate(templateType = MessageTemplateType.IMAGE)
public class ImageMessageTemplateProvider extends MessageTemplateProvider {

    //region: 成员变量

    //endregion

    //region: 构造函数

    //endregion

    //region: 页面生命周期

    @Override
    public int getLayoutRes() {
        return R.layout.widget_recycle_item_message_text;
    }

    @Override
    public void bindView(@NonNull View rootView,
                         @NonNull MessageEntity messageEntity,
                         int position,
                         @Nullable OnMessageTemplateListener listener) {

    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}