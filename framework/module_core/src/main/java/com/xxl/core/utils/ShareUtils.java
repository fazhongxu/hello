package com.xxl.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xxl.core.listener.OnPayListener;

/**
 * 分享工具类
 *
 * @author xxl.
 * @date 2022/7/20.
 */
public class ShareUtils {

    /**
     * 支付监听
     */
    private static OnPayListener sOnPayListener;

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

    /**
     * 微信支付
     *
     * @param orderNo
     * @param listener
     */
    public static void doWeChatPay(@NonNull final String orderNo,
                                   @NonNull final OnPayListener listener) {
        // TODO: 2023/3/1 检查是否安装微信，未安装微信返回监听 onNotInstall ，否则调起微信支付
        sOnPayListener = listener;
    }

    /**
     * 销毁
     */
    public static void onDestory() {
        sOnPayListener = null;
    }

    /**
     * 微信支付回调
     *
     * @param resp
     */
    public static boolean onWeChatPayCallback(@NonNull final BaseResp resp) {
        OnPayListener listener = sOnPayListener;
        if (listener != null) {
            if (resp.errCode == WeChatPayErrCode.ERR_OK) {
                listener.onPayComplete();
            } else if (resp.errCode == WeChatPayErrCode.ERR_USER_CANCEL) {
                listener.onPayCancel();
            } else if (resp.errCode == WeChatPayErrCode.ERR_AUTH_DENIED) {
                listener.onPayFailure();
            }
            return true;
        }
        return false;
    }

    /**
     * 微信支付错误码
     */
    public final static class WeChatPayErrCode {

        /**
         * 用户同意
         */
        public static int ERR_OK = 0;

        /**
         * 用户拒绝授权
         */
        public static int ERR_AUTH_DENIED = -1;

        /**
         * 用户取消
         */
        public static int ERR_USER_CANCEL = -2;
    }

    private ShareUtils() {

    }

}