package com.xxl.hello.user.data.local;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.prefs.api.UserLocalPreferences;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;

/**
 * 用户模块本地数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public interface UserLocalDataStoreSource {

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

    /**
     * 设置当前登录的用户信息
     *
     * @param loginUserEntity
     * @return
     */
    boolean setCurrentLoginUserEntity(@NonNull final LoginUserEntity loginUserEntity);

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    LoginUserEntity getCurrentLoginUserEntity();

    /**
     * 获取用户token
     *
     * @return
     */
    String getUserToken();
}