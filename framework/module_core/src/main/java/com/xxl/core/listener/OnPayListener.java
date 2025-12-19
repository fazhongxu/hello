package com.xxl.core.listener;

/**
 * 支付相关监听
 *
 * @author xxl.
 * @date 2023/3/1.
 */
public interface OnPayListener {

    /**
     * 支付成功
     */
    void onPaySuccess();

    /**
     * 支付失败
     *
     * @param throwable
     */
    void onPayFailure(Throwable throwable);

    /**
     * 支付取消
     */
    void onPayCancel();

    /**
     * 未安装客户端
     */
    void onNotInstall();

}