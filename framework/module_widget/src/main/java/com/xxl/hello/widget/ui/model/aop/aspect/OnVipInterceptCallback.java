package com.xxl.hello.widget.ui.model.aop.aspect;

/**
 * @author xxl.
 * @date 2026/10/19.
 */
public interface OnVipInterceptCallback {
    /**
     * VIP 检查结果回调
     *
     * @param isVip 是否 VIP
     * @return true 继续执行原方法；false 中断，由业务自行处理
     */
    boolean onVipCheckResult(boolean isVip);
}