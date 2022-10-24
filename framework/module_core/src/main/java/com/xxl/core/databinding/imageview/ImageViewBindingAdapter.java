package com.xxl.core.databinding.imageview;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.xxl.core.image.loader.ImageLoader;


/**
 * @author xxl.
 * @date 2021/9/2.
 */
public class ImageViewBindingAdapter {

    /**
     * 加载图片
     *
     * @param targetImageView 目标图片
     * @param targetUrl       图片地址
     * @param errorDrawable   加载错误时展示的图片资源
     *                        requireAll 表示 value 对应的参数是否都是必须填的
     */
    @BindingAdapter(value = {"imageUrl", "error"}, requireAll = false)
    public static void loadImage(@NonNull final ImageView targetImageView,
                                 @NonNull final String targetUrl,
                                 @Nullable final Drawable errorDrawable) {
        if (errorDrawable == null) {
            ImageLoader.with(targetImageView)
                    .load(targetUrl)
                    .into(targetImageView);
            return;
        }
        ImageLoader.with(targetImageView)
                .load(targetUrl)
                .error(errorDrawable)
                .into(targetImageView);
    }

    /**
     * 加载图片
     *
     * @param targetImageView 目标图片
     * @param imageRes        图片资源
     * @param errorDrawable   加载错误时展示的图片资源
     *                        requireAll 表示 value 对应的参数是否都是必须填的
     */
    @BindingAdapter(value = {"imageRes", "error"}, requireAll = false)
    public static void loadImage(@NonNull final ImageView targetImageView,
                                 @DrawableRes final int imageRes,
                                 @Nullable final Drawable errorDrawable) {
        if (errorDrawable == null) {
            ImageLoader.with(targetImageView)
                    .load(imageRes)
                    .into(targetImageView);
            return;
        }
        ImageLoader.with(targetImageView)
                .load(imageRes)
                .error(errorDrawable)
                .into(targetImageView);
    }

}