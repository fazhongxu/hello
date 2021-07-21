package com.xxl.hello.user.data.local;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.LoginUserEntity;

/**
 * 用户模块本地数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public class UserLocalDataStoreSourceIml implements UserLocalDataStoreSource {

    public UserLocalDataStoreSourceIml() {
        // 可以构造入 MMKV 相关 的本地存储
    }

    //region: 与登录用户信息相关

    /**
     * 设置当前登录的用户信息
     *
     * @param loginUserEntity
     * @return
     */
    @Override
    public boolean setCurrentLoginUserEntity(@NonNull final LoginUserEntity loginUserEntity) {

        return false;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @Override
    public LoginUserEntity getCurrentLoginUserEntity() {
        return null;
    }

    //endregion
}