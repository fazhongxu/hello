package com.xxl.core.data.router;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.xxl.kit.RouterUtils;
import com.xxl.kit.StringUtils;

/**
 * 系统模块路由
 *
 * @author xxl.
 * @date 2022/6/23.
 */
public final class SystemRouterApi {

    /**
     * 系统模块名称
     */
    public static final String SYSTEM_MODULE_NAME = "/system_module_name";

    private SystemRouterApi() {

    }

    //region: Web路由相关

    public static class WebView {

        /**
         * 常用Web页面路径地址
         */
        public static final String COMMON_WEB_PATH = SYSTEM_MODULE_NAME + "/web";

        /**
         * url地址
         */
        public static final String PARAMS_KEY_URL = "params_key_url";

        /**
         * 是否可以分享
         */
        public static final String PARAMS_KEY_SHARE_ENABLE = "params_key_share_enable";

        public static Builder newBuilder(@NonNull final String url) {
            return new Builder(url);
        }

        public static class Builder {

            private Bundle mParams = new Bundle();

            public Builder(String url) {
                this(url, true);
            }

            public Builder(String url,
                           boolean isJoinTimeMills) {
                mParams.putString(PARAMS_KEY_URL, isJoinTimeMills ? StringUtils.joinTimeMillis(url) : url);
                setShareEnable(true);
            }

            /**
             * 设置是否可以分享
             *
             * @param shareEnable
             * @return
             */
            public Builder setShareEnable(final boolean shareEnable) {
                mParams.putBoolean(PARAMS_KEY_SHARE_ENABLE, shareEnable);
                return this;
            }

            /**
             * 跳转到webview
             *
             * @param activity
             */
            public void navigation(@NonNull final Activity activity) {
                RouterUtils.navigation(activity, COMMON_WEB_PATH, mParams);
            }
        }
    }


    //endregion
}