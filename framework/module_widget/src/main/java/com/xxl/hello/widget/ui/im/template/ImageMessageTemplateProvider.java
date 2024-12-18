package com.xxl.hello.widget.ui.im.template;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageTemplate;
import com.xxl.hello.service.data.model.entity.im.MessageTemplateType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageImageBinding;

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

    public static ImageMessageTemplateProvider obtain() {
        return new ImageMessageTemplateProvider();
    }

    //endregion

    //region: 页面生命周期

    @Override
    public int getLayoutRes() {
        return R.layout.widget_recycle_item_message_image;
    }

    @Override
    public void bindView(@NonNull View rootView,
                         @NonNull MessageEntity messageEntity,
                         int position,
                         @Nullable OnMessageTemplateListener listener) {
        WidgetRecycleItemMessageImageBinding imageBinding = DataBindingUtil.bind(rootView);
        imageBinding.executePendingBindings();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}