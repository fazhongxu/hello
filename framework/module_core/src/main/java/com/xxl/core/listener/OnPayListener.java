package com.xxl.core.listener;

/**
 * 支付相关监听
 *
 * @author xxl.
 * @date 2023/3/1.
 */
public interface OnPayListener {

    /**
     * 支付完成
     */
    void onPayComplete();

    /**
     * 支付取消
     */
    void onPayCancel();

    /**
     * 支付失败
     */
    void onPayFailure();

    /**
     * 未安装客户端
     */
    void onNotInstall();

}