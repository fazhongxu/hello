package com.xxl.hello.widget.ui.im.template;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.utils.VideoUtils;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageTemplate;
import com.xxl.hello.service.data.model.entity.im.MessageTemplateType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageVideoBinding;
import com.xxl.kit.ClipboardUtils;
import com.xxl.kit.ToastUtils;

/**
 * 视频消息模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@MessageTemplate(templateType = MessageTemplateType.VIDEO)
public class VideoMessageTemplateProvider extends MessageTemplateProvider {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public static VideoMessageTemplateProvider obtain() {
        return new VideoMessageTemplateProvider();
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
        return "[视频]";
    }

    @Override
    public int getLayoutRes() {
        return R.layout.widget_recycle_item_message_video;
    }

    @Override
    public void bindView(@NonNull View rootView,
                         @NonNull MessageEntity messageEntity,
                         int position,
                         @Nullable OnMessageTemplateListener listener) {
        WidgetRecycleItemMessageVideoBinding imageBinding = DataBindingUtil.bind(rootView);

        ImageLoader.with(rootView.getContext())
                .load(messageEntity.getMediaPath())
                .into(imageBinding.ivCover);

        VideoUtils.detectQrCodeInVideo(messageEntity.getMediaPath(), 1000, new VideoUtils.OnDetectQRCodeCallback() {
            @Override
            public void onQRCodeDetected(String result, long timeUs) {
                Log.e("aaa", "onQRCodeDetected: " + result + " time = " + timeUs);
            }

            @Override
            public void onDetectedComplete(boolean isSuccess) {
                Log.e("aaa", "onDetectedComplete: " + isSuccess);
            }
        });

//        VideoUtils.extractFrames(messageEntity.getMediaPath(), 1000, new VideoUtils.OnExtractFramesCallback() {
//            @Override
//            public void onFrameExtracted(Bitmap frame, long timeUs) {
//                Log.e("aaa", "onFrameExtracted: "+ frame +"  "+timeUs );
//            }
//
//            @Override
//            public void onExtractedComplete() {
//                Log.e("aaa", "onExtractedComplete: " );
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.e("aaa", "onError: " +t);
//            }
//        });

        imageBinding.ivCover.setOnClickListener(v -> {
            if (listener != null && listener.onMessageItemClick(messageEntity)) {
                return;
            }
        });

        imageBinding.ivCover.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null && listener.onMessageItemLongClick(imageBinding.llItemContainer, messageEntity)) {
                    return true;
                }
                ClipboardUtils.copyText(messageEntity.getMediaPath());
                ToastUtils.success(R.string.resources_copied).show();
                return true;
            }
        });
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}