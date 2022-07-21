package com.xxl.kit;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * 分享图片到微信/朋友圈的工具类
 *
 * @author xxl.
 * @date 2022/7/21.
 */
public final class MomentShareUtils {

    /**
     * 微信包名
     */
    public static final String WE_CHAT_PACKAGE_NAME = "com.tencent.mm";

    /**
     * 文本描述
     */
    public static final String KDESCRIPTION = "Kdescription";

    /**
     * 微信分享到朋友圈类名
     */
    public static final String WE_CHAT_CIRCLE_CLASS_NAME = "com.tencent.mm.ui.tools.ShareToTimeLineUI";

    /**
     * 微信分享到会话类名
     */
    public static final String WE_CHAT_CLASS_NAME = "com.tencent.mm.ui.tools.ShareImgUI";

    public static void shareImagesToWeChat(@NonNull final Activity context,
                                           @NonNull final List<Uri> imageUris) {
        // TODO: 2022/7/21
    }

    /**
     * 分享图片到微信
     *
     * @param context
     * @param imagePath
     * @param description
     */
    public static void shareImagesToWeChat(@NonNull final Activity context,
                                           @NonNull final String imagePath,
                                           @Nullable final String description) {
        shareImagesToWeChat(context, PathUtils.getUriByFilePath(imagePath), description);
    }

    /**
     * 分享图片到微信(无需SDK)
     *
     * @param context
     * @param imageUri
     * @param description
     */
    public static void shareImagesToWeChat(@NonNull final Activity context,
                                           @NonNull final Uri imageUri,
                                           @Nullable final String description) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(WE_CHAT_PACKAGE_NAME, WE_CHAT_CLASS_NAME);
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");

        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(KDESCRIPTION, description);
        }

        intent.putExtra(Intent.EXTRA_STREAM, imageUri);

        context.startActivity(intent);
    }

    /**
     * 分享图片到微信朋友圈(无需SDK)
     *
     * @param context
     * @param imagePath
     * @param description
     */
    public static void shareImagesToWeChatCircle(@NonNull final Activity context,
                                                 @NonNull final String imagePath,
                                                 @Nullable final String description) {
        shareImagesToWeChatCircle(context, PathUtils.getUriByFilePath(imagePath), description);
    }

    /**
     * 分享图片到微信朋友圈(无需SDK)
     *
     * @param context
     * @param imageUri
     * @param description
     */
    public static void shareImagesToWeChatCircle(@NonNull final Activity context,
                                                 @NonNull final Uri imageUri,
                                                 @Nullable final String description) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(WE_CHAT_PACKAGE_NAME, WE_CHAT_CIRCLE_CLASS_NAME);
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");

        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(KDESCRIPTION, description);
        }

        intent.putExtra(Intent.EXTRA_STREAM, imageUri);

        context.startActivity(intent);
    }


    private MomentShareUtils() {

    }

}