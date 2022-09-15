package com.xxl.hello.service.data.model.event;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;



/**
 * 用户模块EventBus
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public class OnUserEventApi {

    //region: 构造函数

    private OnUserEventApi() {

    }

    //endregion

    //region: 用户信息更新通知事件

    /**
     * 用户信息更新通知事件
     */
    public static final class OnUpdateUserInfoEvent {

        /**
         * 用户信息
         */
        private LoginUserEntity mTargetUserEntity;

        public LoginUserEntity getTargetUserEntity() {
            return mTargetUserEntity;
        }

        private OnUpdateUserInfoEvent(@NonNull final LoginUserEntity targetUserEntity) {
            mTargetUserEntity = targetUserEntity;
        }

        public static final OnUpdateUserInfoEvent obtain(@NonNull final LoginUserEntity targetUserEntity) {
            return new OnUpdateUserInfoEvent(targetUserEntity);
        }
    }

    //endregion

}