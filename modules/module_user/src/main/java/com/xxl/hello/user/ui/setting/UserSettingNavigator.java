package com.xxl.hello.user.ui.setting;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.LoginUserEntity;

/**
 * 用户设置
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public interface UserSettingNavigator {

    /**
     * 请求添加上传资源到数据库完成
     *
     * @param isSuccess
     */
    void onRequestPutResourcesUploadQueueDBEntities(Boolean isSuccess);

    /**
     * 更新用户信息完成
     *
     * @param targetUserEntity
     */
    void onUpdateUserInfoComplete(@NonNull final LoginUserEntity targetUserEntity);

    /**
     * 用户头像点击
     */
    void onUserAvatarClick();

    /**
     * 切换网络环境点击
     */
    void onSwitchEnvironmentClick();


}