package com.xxl.hello.widget.ui.im.message.session.base.menu;

import androidx.annotation.NonNull;

import com.arthenica.ffmpegkit.MediaInformationSession;
import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.ui.im.message.session.base.BaseChatSessionFragment;
import com.xxl.kit.ClipboardUtils;
import com.xxl.kit.FFmpegUtils;
import com.xxl.kit.GsonUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.ToastUtils;

/**
 * 分享
 *
 * @author xxl.
 * @date 2025/6/30.
 */
public class OnShareOperate implements OnMenuItemOperate<BaseChatSessionFragment> {

    /**
     * 点击操作
     *
     * @param fragment
     * @param messageEntity
     */
    @Override
    public void handle(@NonNull BaseChatSessionFragment fragment,
                       @NonNull MessageEntity messageEntity) {
        if (messageEntity.getMessageType() == MessageType.IMAGE
                || messageEntity.getMessageType() == MessageType.VIDEO) {
            if (NetworkConfig.Companion.isNetworkDebug()) {
                FFmpegUtils.getMediaInformationAsync(messageEntity.getMediaPath(), new OnRequestCallBack<MediaInformationSession>() {
                    @Override
                    public void onSuccess(MediaInformationSession session) {
                        LogUtils.d("媒体信息 " + GsonUtils.toJson(session));
                    }
                });
            }
            ClipboardUtils.copyText(messageEntity.getMediaPath());
            ToastUtils.success(R.string.resources_path_copied).show();
        } else if (messageEntity.getMessageType() == MessageType.TEXT) {
            ClipboardUtils.copyText(messageEntity.getMessageText());
            ToastUtils.success(R.string.resources_copied).show();
        }

    }
}