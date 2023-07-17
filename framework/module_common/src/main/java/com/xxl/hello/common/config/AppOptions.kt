package com.xxl.hello.common.config

/**
 * 配置信息相关
 *
 * @author xxl.
 * @date 2023/7/17.
 */
class AppOptions {


    //region: 通用配置相关

    //endregion

    //region: 分享配置相关

    //endregion

    //region: VIP配置相关

    /**
     * VIP配置信息
     */
    class VipConfig private constructor() {
        companion object {

            /**
             * 长按用户设置功能ID
             */
            const val LONG_CLICK_USER_SETTING_VIP_FUNCTION_ID = "10002"
        }
    }

    //endregion

}