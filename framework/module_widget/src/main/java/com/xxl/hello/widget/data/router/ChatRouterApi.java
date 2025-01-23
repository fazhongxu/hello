package com.xxl.hello.widget.data.router;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.xxl.kit.RouterUtils;

/**
 * 会话模块路由
 *
 * @author xxl.
 * @date 2022/6/23.
 */
public final class ChatRouterApi {

    /**
     * im组件模块名称
     */
    public static final String IM_MODULE_NAME = "/widget_module_name";

    private ChatRouterApi() {

    }

    //region: 消息相关

    public static class MessageList {

        /**
         * 消息列表页面路径地址
         */
        public static final String PATH = IM_MODULE_NAME + "/message_list";

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder {

            private Bundle mParams = new Bundle();

            public Builder() {

            }

            /**
             * 跳转到消息列表
             */
            public void navigation() {
                RouterUtils.navigation(PATH, mParams);
            }
        }
    }

    //endregion

    //region: 聊天会话相关

    public static class PrivateChat {

        /**
         * 单聊页面路径地址
         */
        public static final String PATH = IM_MODULE_NAME + "/private_chat";

        /**
         * 目标ID
         */
        public static final String PARAMS_KEY_TARGET_ID = "params_key_target_id";

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder {

            private Bundle mParams = new Bundle();

            public Builder() {

            }

            /**
             * 设置目标ID
             *
             * @param targetId
             * @return
             */
            public Builder setTargetId(@NonNull String targetId) {
                mParams.putString(PARAMS_KEY_TARGET_ID,targetId);
                return this;
            }

            /**
             * 跳转到会话
             */
            public void navigation() {
                RouterUtils.navigation(PATH, mParams);
            }
        }
    }

    //endregion
}