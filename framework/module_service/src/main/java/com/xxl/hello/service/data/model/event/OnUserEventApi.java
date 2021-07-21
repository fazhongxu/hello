package com.xxl.hello.service.data.model.event;

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

        private OnUpdateUserInfoEvent() {

        }

        public static final OnUpdateUserInfoEvent obtain() {
            return new OnUpdateUserInfoEvent();
        }
    }

    //endregion

}