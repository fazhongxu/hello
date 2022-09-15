package com.xxl.kit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 分享工具类
 *
 * @author xxl.
 * @date 2022/7/20.
 */
public class ShareUtils {

    /**
     * 预初始化
     *
     * @param context
     * @param appkey
     * @param channel
     * @param isDebug
     */
    public static void preInit(@NonNull final Context context,
                               @NonNull final String appkey,
                               @NonNull final String channel,
                               final boolean isDebug) {
        UMConfigure.setLogEnabled(isDebug);
        UMConfigure.preInit(context, appkey, channel);
    }

    /**
     * 初始化
     *
     * @param context
     * @param appkey
     * @param channel
     * @param pushSecret
     * @param isDebug
     */
    public static void init(@NonNull final Context context,
                            @NonNull final String appkey,
                            @NonNull final String pushSecret,
                            @NonNull final String channel,
                            final boolean isDebug) {
        UMConfigure.setLogEnabled(isDebug);
        UMConfigure.init(context, appkey, channel, UMConfigure.DEVICE_TYPE_PHONE, pushSecret);
    }

    /**
     * onActivityResult
     *
     * @param context
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(@NonNull final Context context,
                                        final int requestCode,
                                        final int resultCode,
                                        @Nullable final Intent data) {
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 分享图片到微信
     *
     * @param context   上下文
     * @param imagePath 图片路径
     */
    public static void shareImageToWeChat(@NonNull Activity context,
                                          @NonNull final String imagePath,
                                          @Nullable final UMShareListener listener) {
        shareImage(context, SHARE_MEDIA.WEIXIN, imagePath, "", "", "", listener);
    }

    /**
     * 分享图片
     *
     * @param activity    上下文
     * @param listener    分享监听
     * @param shareMedia  分享图片
     * @param thumb       图片缩略图
     * @param imagePath   图片路径
     * @param title       标题
     * @param description 描述
     * @param
     */
    public static void shareImage(@NonNull Activity activity,
                                  SHARE_MEDIA shareMedia,
                                  @NonNull final String imagePath,
                                  @Nullable final String thumb,
                                  @Nullable final String title,
                                  @Nullable final String description,
                                  UMShareListener listener) {
        UMImage image = new UMImage(activity, imagePath);
        image.setTitle(title);

        if (!TextUtils.isEmpty(thumb)) {
            UMImage thumbImage = new UMImage(activity, imagePath);
            image.setThumb(thumbImage);
        }
        image.setDescription(description);
        new ShareAction(activity)
                .setPlatform(shareMedia)
                .setCallback(listener)
                .withMedia(image)
                .share();
    }

    private ShareUtils() {

    }

}