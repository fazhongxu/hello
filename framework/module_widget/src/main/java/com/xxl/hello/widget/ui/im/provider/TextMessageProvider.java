package com.xxl.hello.widget.ui.im.provider;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageTemplate;
import com.xxl.hello.service.data.model.entity.im.MessageTemplateType;
import com.xxl.hello.service.data.model.entity.im.MessageType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageTextBinding;

/**
 * 文本消息模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@MessageTemplate(templateType = MessageTemplateType.TEXT)
public class TextMessageProvider extends BaseItemProvider<MessageEntity>  implements MessageProvider {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public static TextMessageProvider obtain() {
        return new TextMessageProvider();
    }

    //endregion

    //region: 页面生命周期

    @Override
    public int getItemViewType() {
        return MessageType.TEXT;
    }

    @Override
    public int getLayoutId() {
        return R.layout.widget_recycle_item_message_text;
    }

    @Override
    public void convert(@NonNull BaseViewHolder viewHolder, MessageEntity messageEntity) {
        WidgetRecycleItemMessageTextBinding textBinding = DataBindingUtil.bind(viewHolder.itemView);
        textBinding.tvContent.setText(messageEntity.getMessageText());
//        textBinding.tvContent.setOnClickListener(v -> {
//            if (listener != null && listener.onMessageItemClick(messageEntity)) {
//                return;
//            }
//        });
//        textBinding.llItemContainer.setOnLongClickListener(v -> {
//            if (listener != null && listener.onMessageItemLongClick(textBinding.llItemContainer,messageEntity)){
//                return true;
//            }
//            return false;
//        });
//        textBinding.tvContent.setOnLongClickListener(v -> {
//            if (listener != null && listener.onMessageItemLongClick(textBinding.llItemContainer,messageEntity)){
//                return true;
//            }
//            return false;
//        });
        textBinding.executePendingBindings();
    }

    /**
     * 获取背景资源
     *
     * @param message
     */
    @Override
    public int getBackgroundDrawableRes(MessageEntity message) {
        return 0;
    }

    /**
     * 绑定视图
     *
     * @param context
     * @param container
     * @param message
     */
    @Override
    public void bindView(Context context, FrameLayout container, MessageEntity message) {
        // TODO: 2025/12/3  
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}