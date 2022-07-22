package com.xxl.kit;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
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
    public static final String DESCRIPTION = "Kdescription";

    /**
     * 微信分享到朋友圈类名
     */
    public static final String WE_CHAT_MOMENT_CLASS_NAME = "com.tencent.mm.ui.tools.ShareToTimeLineUI";

    /**
     * 微信分享到会话类名
     */
    public static final String WE_CHAT_CLASS_NAME = "com.tencent.mm.ui.tools.ShareImgUI";

    /**
     * 分享单张图片到微信好友
     *
     * @param context   上下文
     * @param imagePath 图片路径
     */
    public static void shareImageToWeChatFriend(@NonNull final Context context,
                                                @NonNull final String imagePath) {
        shareImageToWeChatFriend(context, PathUtils.getUriByFilePath(imagePath), "");
    }

    /**
     * 分享单张图片到微信好友
     *
     * @param context     上下文
     * @param imagePath   图片路径
     * @param description 描述文本
     */
    public static void shareImageToWeChatFriend(@NonNull final Context context,
                                                @NonNull final String imagePath,
                                                @Nullable final String description) {
        shareImageToWeChatFriend(context, PathUtils.getUriByFilePath(imagePath), description);
    }

    /**
     * 分享单张图片到微信好友
     *
     * @param context     上下文
     * @param imageUri    图片路径
     * @param description 描述文本
     */
    public static void shareImageToWeChatFriend(@NonNull final Context context,
                                                @NonNull final Uri imageUri,
                                                @Nullable String description) {
        if (AppUtils.isAppInstalled(WE_CHAT_PACKAGE_NAME)) {
            Intent intent = new Intent();
            ComponentName cop = new ComponentName(WE_CHAT_PACKAGE_NAME, WE_CHAT_CLASS_NAME);
            intent.setComponent(cop);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(Intent.createChooser(intent, TextUtils.isEmpty(description) ? "" : description));
        } else {
            ToastUtils.warning("请先安装微信客户端").show();
        }
    }

    /**
     * 分享单张图片到朋友圈
     *
     * @param context   上下文
     * @param imagePath 图片路径
     */
    public static void shareSingleImageToWeChatMoment(@NonNull final Context context,
                                                      @NonNull final String imagePath) {
        shareSingleImageToWeChatMoment(context, PathUtils.getUriByFilePath(imagePath), "");
    }

    /**
     * 分享单张图片到朋友圈
     *
     * @param context     上下文
     * @param imagePath   图片路径
     * @param description 描述文本
     */
    public static void shareSingleImageToWeChatMoment(@NonNull final Context context,
                                                      @NonNull final String imagePath,
                                                      @Nullable final String description) {
        shareSingleImageToWeChatMoment(context, PathUtils.getUriByFilePath(imagePath), description);
    }

    /**
     * 分享单张图片到朋友圈
     *
     * @param context  上下文
     * @param imageUri 图片路径
     */
    public static void shareSingleImageToWeChatMoment(@NonNull final Context context,
                                                      @NonNull final Uri imageUri,
                                                      @Nullable final String description) {
        if (AppUtils.isAppInstalled(WE_CHAT_PACKAGE_NAME)) {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName(WE_CHAT_PACKAGE_NAME, WE_CHAT_MOMENT_CLASS_NAME);
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.putExtra(DESCRIPTION, TextUtils.isEmpty(description) ? "" : description);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            ToastUtils.warning("请先安装微信客户端").show();
        }
    }

    /**
     * 分享多张图片到朋友圈
     *
     * @param context    上下文
     * @param imagePaths 图片路径
     */
    public static void shareMultiImageToWeChatMoment(@NonNull final Context context,
                                                     @NonNull final List<String> imagePaths) {
        final ArrayList<Uri> imageUris = new ArrayList<>();
        if (!ListUtils.isEmpty(imagePaths)) {
            for (String imagePath : imagePaths) {
                imageUris.add(PathUtils.getUriByFilePath(imagePath));
            }
        }
        shareMultiImageToWeChatMoment(context, imageUris);
    }

    /**
     * 分享多张图片到朋友圈
     *
     * @param context   上下文
     * @param imageUris 图片路径
     */
    public static void shareMultiImageToWeChatMoment(@NonNull final Context context,
                                                     @NonNull final ArrayList<Uri> imageUris) {
        if (AppUtils.isAppInstalled(WE_CHAT_PACKAGE_NAME)) {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName(WE_CHAT_PACKAGE_NAME, WE_CHAT_MOMENT_CLASS_NAME);
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            intent.putExtra(DESCRIPTION, "share");
            context.startActivity(intent);
        } else {
            ToastUtils.warning("请先安装微信客户端").show();
        }
    }

    private MomentShareUtils() {

    }

}