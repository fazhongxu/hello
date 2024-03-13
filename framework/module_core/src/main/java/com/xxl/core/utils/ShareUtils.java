package com.xxl.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xxl.core.data.model.entity.pay.WXPayEntity;
import com.xxl.core.listener.OnAuthListener;
import com.xxl.core.listener.OnPayListener;
import com.xxl.kit.AppUtils;
import com.xxl.kit.FileUtils;
import com.xxl.kit.ListUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 登录授权监听
     */
    private static OnAuthListener sOnAuthListener;

    /**
     * 微信的回调Code
     */
    private static final String WE_CHAT_MAP_KEY_CODE = "code";

    /**
     * 应用授权作用域，如获取用户个人信息则填写snsapi_userinfo
     * https://developers.weixin.qq.com/doc/oplatform/Mobile_App/WeChat_Login/Development_Guide.html
     */
    public static final String WE_CHAT_AUTH_SCOPE = "snsapi_userinfo";

    /**
     * 用用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止 csrf 攻击（跨站请求伪造攻击），
     * 建议第三方带上该参数，可设置为简单的随机数加 session 进行校验。在state传递的过程中会将该参数作为url的一部分进行处理，
     * 因此建议对该参数进行url encode操作，防止其中含有影响url解析的特殊字符（如'#'、'&'等）导致该参数无法正确回传。
     */
    public static final String WE_CHAT_AUTH_STATE = "xxl_hello_state";

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
        shareImage(context, SHARE_MEDIA.WEIXIN, FileUtils.getFileByPath(imagePath), "", listener);
    }

    /**
     * 分享图片
     *
     * @param activity    上下文
     * @param listener    分享监听
     * @param platform    平台
     * @param imagePaths  图片路径
     * @param description 描述
     * @param
     */
    public static void shareImages(@NonNull Activity activity,
                                   SHARE_MEDIA platform,
                                   @NonNull final List<String> imagePaths,
                                   @Nullable final String description,
                                   UMShareListener listener) {
        if (ListUtils.getSize(imagePaths) > 1) {
            UMImage[] images = new UMImage[ListUtils.getSize(imagePaths)];
            if (!ListUtils.isEmpty(imagePaths)) {
                for (int i = 0; i < imagePaths.size(); i++) {
                    images[i] = new UMImage(activity, FileUtils.getFileByPath(imagePaths.get(i)));
                }
            }
            String text = !TextUtils.isEmpty(description) ? description : "";
            new ShareAction(activity)
                    .withText(text)
                    .setPlatform(platform)
                    .setCallback(listener)
                    .withMedias(images)
                    .share();
            return;
        } else if (ListUtils.isSingle(imagePaths)) {
            shareImage(activity, platform, FileUtils.getFileByPath(ListUtils.getFirst(imagePaths)), description, listener);
        }
    }

    /**
     * 分享图片
     *
     * @param activity    上下文
     * @param listener    分享监听
     * @param platform    分享平台
     * @param imageFile   图片文件
     * @param description 描述
     * @param
     */
    public static void shareImage(@NonNull Activity activity,
                                  SHARE_MEDIA platform,
                                  @NonNull final File imageFile,
                                  @Nullable final String description,
                                  UMShareListener listener) {
        UMImage image = new UMImage(activity, imageFile);
        image.setTitle(description);
        UMImage thumbImage = new UMImage(activity, imageFile);
        image.setThumb(thumbImage);
        image.setDescription(description);
        new ShareAction(activity)
                .setPlatform(platform)
                .setCallback(listener)
                .withMedia(image)
                .share();
    }

    /**
     * 微信支付
     *
     * @param activity
     * @param wxPayEntity
     * @param listener
     */
    public static void doWeChatPay(@NonNull final Activity activity,
                                   @NonNull final WXPayEntity wxPayEntity,
                                   @NonNull final OnPayListener listener) {
        boolean isInstall = UMShareAPI.get(AppUtils.getApplication()).isInstall(activity, SHARE_MEDIA.WEIXIN);
        if (!isInstall) {
            if (listener != null) {
                listener.onNotInstall();
            }
            return;
        }
        sOnPayListener = listener;
        String appId = PlatformConfig.getPlatform(SHARE_MEDIA.WEIXIN).getAppid();
        IWXAPI api = WXAPIFactory.createWXAPI(activity, appId, false);
        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId = wxPayEntity.getPartnerId();
        request.prepayId = wxPayEntity.getPrepayId();
        request.packageValue = wxPayEntity.getPackageValue();
        request.nonceStr = wxPayEntity.getNonceStr();
        request.timeStamp = wxPayEntity.getTimestamp();
        request.sign = wxPayEntity.getSign();
        api.sendReq(request);
    }

    /**
     * 微信授权
     *
     * @param activity
     * @param listener
     */
    public static void doWeChatAuth(@NonNull final Activity activity,
                                    @NonNull final OnAuthListener listener) {
        doWeChatAuth(activity, WE_CHAT_AUTH_SCOPE, WE_CHAT_AUTH_STATE, listener);
    }

    /**
     * 微信授权
     *
     * @param activity
     * @param scope
     * @param state
     * @param listener
     */
    public static void doWeChatAuth(@NonNull final Activity activity,
                                    @NonNull final String scope,
                                    @NonNull final String state,
                                    @NonNull final OnAuthListener listener) {
        boolean isInstall = UMShareAPI.get(AppUtils.getApplication()).isInstall(activity, SHARE_MEDIA.WEIXIN);
        if (!isInstall) {
            if (listener != null) {
                listener.onNotInstall();
            }
            return;
        }
        sOnAuthListener = listener;
        String appId = PlatformConfig.getPlatform(SHARE_MEDIA.WEIXIN).getAppid();
        IWXAPI api = WXAPIFactory.createWXAPI(activity, appId, false);
        final SendAuth.Req request = new SendAuth.Req();
        request.scope = scope;
        request.state = state;
        api.sendReq(request);
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
     * 微信授权回调（登录）
     *
     * @param resp
     * @return
     */
    public static boolean onWeChatAuthCallback(@NonNull final SendAuth.Resp resp) {
        OnAuthListener listener = sOnAuthListener;
        if (listener != null) {
            if (resp.errCode == WeChatPayErrCode.ERR_OK) {
                Map<String, String> data = new HashMap<>(10);
                data.put(WE_CHAT_MAP_KEY_CODE, resp.code);
                listener.onComplete(SHARE_MEDIA.WEIXIN, 0, data);
            } else if (resp.errCode == WeChatPayErrCode.ERR_USER_CANCEL
                    || resp.errCode == WeChatPayErrCode.ERR_AUTH_DENIED) {
                listener.onCancel(SHARE_MEDIA.WEIXIN, 0);
            } else {
                listener.onError(SHARE_MEDIA.WEIXIN, 0, new Throwable("wechat auth failure"));
            }
            return true;
        }
        return false;
    }

    /**
     * 获取微信的 Code 数据
     *
     * @param data
     * @return
     */
    public static String getWechatCode(@NonNull Map<String, String> data) {
        if (data == null || !data.containsKey(WE_CHAT_MAP_KEY_CODE)) {
            return null;
        }
        return data.get(WE_CHAT_MAP_KEY_CODE);
    }


    /**
     * 销毁
     */
    public static void onDestory() {
        sOnPayListener = null;
        sOnAuthListener = null;
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