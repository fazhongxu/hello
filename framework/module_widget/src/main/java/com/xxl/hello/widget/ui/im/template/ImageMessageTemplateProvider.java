package com.xxl.hello.widget.ui.im.template;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageTemplate;
import com.xxl.hello.service.data.model.entity.im.MessageTemplateType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageImageBinding;
import com.xxl.kit.ClipboardUtils;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.ToastUtils;

/**
 * 图片消息模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@MessageTemplate(templateType = MessageTemplateType.IMAGE)
public class ImageMessageTemplateProvider extends MessageTemplateProvider {

    //region: 成员变量

    /**
     * 默认宽度
     */
    private static final int PREVIEW_DEFAULT_WIDTH = DisplayUtils.dp2px(80);

    /**
     * 最大宽度
     */
    private static final int PREVIEW_MAX_WIDTH = DisplayUtils.dp2px(100);

    /**
     * 最大高度
     */
    private static final int PREVIEW_MAX_HEIGHT = DisplayUtils.dp2px(160);

    //endregion

    //region: 构造函数

    public static ImageMessageTemplateProvider obtain() {
        return new ImageMessageTemplateProvider();
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
        return "[图片]";
    }

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

        setupImageLayout(imageBinding, messageEntity);

        imageBinding.ivImage.setOnClickListener(v -> {
            if (listener != null && listener.onMessageItemClick(messageEntity)) {
                return;
            }
        });
        imageBinding.ivImage.setOnLongClickListener(new View.OnLongClickListener() {
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

    //region: 页面视图渲染

    /**
     * 设置图片视图
     *
     * @param imageBinding
     * @param messageEntity
     */
    private void setupImageLayout(WidgetRecycleItemMessageImageBinding imageBinding,
                                  MessageEntity messageEntity) {
        ImageLoader.with(imageBinding.ivImage)
                .asBitmap()
                .load(messageEntity.getMediaPath())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        int targetWidth;
                        int targetHeight;
                        if (width == 0 || height == 0) {
                            targetWidth = PREVIEW_DEFAULT_WIDTH;
                            targetHeight = PREVIEW_DEFAULT_WIDTH;
                        } else {
                            if (width > height) {// 按比例缩放
                                targetWidth = PREVIEW_MAX_WIDTH;
                                targetHeight = targetWidth * height / width;
                            } else {
                                targetHeight = PREVIEW_MAX_HEIGHT;
                                targetWidth = width * targetHeight / height;
                            }
                            if (targetWidth < PREVIEW_DEFAULT_WIDTH) {// 缩放后如果宽度过小，需要调宽，高度裁剪掉 不加这个就类似adjustViewBounds
                                targetWidth = PREVIEW_DEFAULT_WIDTH;
                                targetHeight = targetWidth * height / width;//按比例缩放后的高
                                targetHeight = Math.min(targetHeight, PREVIEW_MAX_HEIGHT);
                            }
                        }
                        ViewGroup.LayoutParams layoutParams = imageBinding.ivImage.getLayoutParams();
                        layoutParams.width = targetWidth;
                        layoutParams.height = targetHeight;
                        imageBinding.ivImage.setLayoutParams(layoutParams);
                        imageBinding.ivImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}