package com.xxl.hello.widget.ui.im.render;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageImageBinding;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;
import com.xxl.kit.ClipboardUtils;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.ToastUtils;

/**
 * 图片消息渲染
 *
 * @author xxl.
 * @date 2025/12/5.
 */
public class ImageMessageRender extends BaseMessageRender<WidgetRecycleItemMessageImageBinding> {

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

    private ImageMessageRender() {

    }

    public final static ImageMessageRender obtain() {
        return new ImageMessageRender();
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
        return String.valueOf(MessageType.IMAGE);
    }

    /**
     * 获取背景
     *
     * @param messageEntity
     */
    @Override
    public Drawable getBackground(MessageEntity messageEntity) {
        return null;
    }

    /**
     * 获取资源视图
     *
     * @return
     */
    @Override
    public int getLayoutRes() {
        return R.layout.widget_recycle_item_message_image;
    }

    /**
     * 渲染
     *
     * @param messageBinding
     * @param messageEntity
     * @param listener
     */
    @Override
    public void render(WidgetRecycleItemMessageImageBinding messageBinding, MessageEntity messageEntity, OnMessageTemplateListener listener) {
        setupImageView(messageBinding, messageEntity);
        messageBinding.ivImage.setOnClickListener(v -> {
            if (listener != null && listener.onMessageItemClick(messageEntity)) {
                return;
            }
        });
        messageBinding.ivImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null && listener.onMessageItemLongClick(messageBinding.llItemContainer, messageEntity)) {
                    return true;
                }
                ClipboardUtils.copyText(messageEntity.getMediaPath());
                ToastUtils.success(R.string.resources_copied).show();
                return true;
            }
        });
    }

    /**
     * 设置图片视图
     *
     * @param imageBinding
     * @param messageEntity
     */
    protected void setupImageView(WidgetRecycleItemMessageImageBinding imageBinding,
                                  MessageEntity messageEntity) {
        ImageLoader.with(imageBinding.ivImage)
                .asBitmap()
                .load(messageEntity.getMediaPath())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageBinding.ivImage.setImageBitmap(resource);
                        adjustViewBounds(imageBinding.ivImage, resource.getWidth(), resource.getHeight());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * 动态调整view的大小
     *
     * @param targetView
     * @param width
     * @param height
     */
    protected void adjustViewBounds(View targetView, int width, int height) {
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
        ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
        layoutParams.width = targetWidth;
        layoutParams.height = targetHeight;
        targetView.setLayoutParams(layoutParams);
    }

    //endregion

}