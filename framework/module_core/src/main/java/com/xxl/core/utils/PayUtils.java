package com.xxl.core.utils;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
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
                listener.onPayFailure(null);
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
        final Runnable payRunnable = () -> {
            PayTask payTask = new PayTask(activity);
            Map<String, String> result = payTask.payV2(orderInfo, true);
            AliPayResult payResult = new AliPayResult(result);

            if (payResult.isSuccess()) {
                listener.onPayComplete();
                return;
            }
            if (payResult.isCancel()) {
                listener.onPayCancel();
                return;
            }
            if (payResult.isFailure()) {
                listener.onPayFailure(null);
                return;
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
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