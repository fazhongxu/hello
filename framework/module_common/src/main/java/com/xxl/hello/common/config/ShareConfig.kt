package com.xxl.hello.common.config

/**
 * 分享相关配置
 *
 * @author xxl.
 * @date 2023/3/2.
 */
class ShareConfig private constructor() {

    companion object {

        /**
         * app key
         */
        const val APP_KEY = "62d8efb288ccdf4b7ed9b4c3"

        /**
         * app secret
         */
        const val APP_SECRET = "pntsbzgny2vpyhitmoasmtuovlqveyyq"

        /**
         * 渠道
         */
        const val CHANNEL = "android"

        /**
         * 资源分享器页面返回结果处理的key
         */
         const val SHARE_PICKER_ACTIVITY_RESULT_KEY = "share_picker_activity_result_key"
    }
}