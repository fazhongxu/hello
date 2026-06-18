package com.xxl.hello.widget.ui.im.message.session.base.menu;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.ui.im.message.session.base.BaseChatSessionFragment;
import com.xxl.kit.RouterUtils;

/**
 * 图片编辑
 *
 * @author xxl.
 * @date 2025/6/30.
 */
public class OnEditOperate implements OnMenuItemOperate<BaseChatSessionFragment> {

    /**
     * 点击操作
     *
     * @param fragment
     * @param messageEntity
     */
    @Override
    public void handle(@NonNull BaseChatSessionFragment fragment,
                       @NonNull MessageEntity messageEntity) {
        if (messageEntity.getMessageType() == MessageType.IMAGE) {
            // 使用 Fragment 启动，以便在 Fragment 的 onActivityResult 中接收返回结果
            Postcard postcard = RouterUtils.buildPostcard(WidgetRouterApi.ImageEdit.PATH);
            RouterUtils.completion(postcard);
            Intent intent = new Intent(fragment.getActivity(), postcard.getDestination());
            intent.putExtra(WidgetRouterApi.ImageEdit.PARAMS_KEY_IMAGE_PATH, messageEntity.getMediaPath());
            fragment.startActivityForResult(intent, WidgetRouterApi.ImageEdit.IMAGE_EDIT_REQUEST_CODE);
        }
    }
}
