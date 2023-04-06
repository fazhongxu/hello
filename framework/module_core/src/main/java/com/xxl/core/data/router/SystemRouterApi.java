package com.xxl.core.data.router;

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

        /**
         * 是否可以刷新（默认可以刷新）
         */
        public static final String PARAMS_KEY_REFRESH_ENABLE = "params_key_refresh_enable";

        public static Builder newBuilder(@NonNull final String url) {
            return new Builder(url);
        }

        public static Builder newBuilder(@NonNull final String url,
                                         final boolean isJoinTimeMills) {
            return new Builder(url, isJoinTimeMills);
        }

        public static class Builder {

            private Bundle mParams = new Bundle();

            public Builder(String url) {
                this(url, true);
            }

            public Builder(String url,
                           boolean isJoinTimeMills) {
                mParams.putString(PARAMS_KEY_URL, isJoinTimeMills ? StringUtils.joinTimeMillis(url) : url);
            }

            /**
             * 设置是否可以分享（默认可分享）
             *
             * @param shareEnable
             * @return
             */
            public Builder setShareEnable(final boolean shareEnable) {
                mParams.putBoolean(PARAMS_KEY_SHARE_ENABLE, shareEnable);
                return this;
            }

            /**
             * 设置是否可以刷新（默认可以刷新）
             *
             * @param refreshEnable
             * @return
             */
            public Builder setRefreshEnable(final boolean refreshEnable) {
                mParams.putBoolean(PARAMS_KEY_REFRESH_ENABLE, refreshEnable);
                return this;
            }

            /**
             * 跳转到webview
             */
            public void navigation() {
                RouterUtils.navigation(COMMON_WEB_PATH, mParams);
            }
        }
    }

    //endregion

    //region: 多媒体预览路由相关

    public static class MediaPreview {

        /**
         * 多媒体预览页面路径地址
         */
        public static final String PATH = SYSTEM_MODULE_NAME + "/media_preview";

        /**
         * 多媒体信息地址
         */
        public static final String PARAMS_KEY_MEDIA_ENTITIES = "params_key_media_entities";

        /**
         * 是否可以分享
         */
        public static final String PARAMS_KEY_SHARE_ENABLE = "params_key_share_enable";

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder {

            private Bundle mParams = new Bundle();

            public Builder() {
            }

            /**
             * 设置是否可以分享（默认可分享）
             *
             * @param shareEnable
             * @return
             */
            public Builder setShareEnable(final boolean shareEnable) {
                mParams.putBoolean(PARAMS_KEY_SHARE_ENABLE, shareEnable);
                return this;
            }

            /**
             * 跳转到多媒体预览
             */
            public void navigation() {
                RouterUtils.navigation(PATH, mParams);
            }
        }
    }

    //endregion
}