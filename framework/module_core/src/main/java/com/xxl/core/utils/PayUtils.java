package com.xxl.core.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xxl.core.data.model.entity.pay.AliPayResult;
import com.xxl.core.data.model.entity.pay.WXPayEntity;
import com.xxl.core.listener.OnPayListener;
import com.xxl.kit.AppUtils;

import java.util.Map;

/**
 * @author xxl.
 * @date 2024/4/17.
 */
public class PayUtils {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 支付监听
     */
    private static OnPayListener sOnPayListener;

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
        if (!isInstallWeChat()) {
            listener.onNotInstall();
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
     * 微信支付回调
     *
     * @param resp
     */
    public static boolean onWeChatPayCallback(@NonNull final BaseResp resp) {
        OnPayListener listener = sOnPayListener;
        if (listener != null) {
            if (resp.errCode == WeChatPayErrCode.ERR_OK) {
                listener.onPaySuccess();
            } else if (resp.errCode == WeChatPayErrCode.ERR_AUTH_DENIED) {
                listener.onPayFailure(null);
            } else if (resp.errCode == WeChatPayErrCode.ERR_USER_CANCEL) {
                listener.onPayCancel();
            }
            return true;
        }
        return false;
    }

    /**
     * 支付宝支付
     *
     * @param activity
     * @param orderInfo
     * @param listener
     */
    public static void doAliPay(@NonNull Activity activity,
                                @NonNull String orderInfo,
                                @NonNull OnPayListener listener) {
        if (!isInstallAliPay()) {
            listener.onNotInstall();
            return;
        }
        final Runnable payRunnable = () -> {
            PayTask payTask = new PayTask(activity);
            Map<String, String> result = payTask.payV2(orderInfo, true);
            AliPayResult payResult = new AliPayResult(result);
            if (payResult.isSuccess()) {
                HANDLER.post(listener::onPaySuccess);
                return;
            }
            if (payResult.isFailure()) {
                HANDLER.post(() -> listener.onPayFailure(null));
                return;
            }
            if (payResult.isCancel()) {
                HANDLER.post(listener::onPayCancel);
                return;
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 是否安装微信
     *
     * @return
     */
    public static boolean isInstallWeChat() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("weixin://"));
            return intent.resolveActivity(AppUtils.getApplication().getPackageManager()) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否安装支付宝
     *
     * @return
     */
    public static boolean isInstallAliPay() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("alipays://platformapi/startApp"));
            return intent.resolveActivity(AppUtils.getApplication().getPackageManager()) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 销毁
     */
    public static void onDestroy() {
        sOnPayListener = null;
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

    private PayUtils() {

    }

}