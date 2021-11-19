package com.xxl.hello.service.data.local.prefs.api;

/**
 * 获取用户设置信息存储
 *
 * @author xxl.
 * @date 2021/7/20.
 */
public interface UserLocalPreferences {

    /**
     * 设置用户同意"隐私协议"的状态
     *
     * @return
     */
    boolean setAgreePrivacyPolicyStatus(final boolean isAgree);

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    boolean isAgreePrivacyPolicy();
}