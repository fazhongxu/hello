package com.xxl.hello.service.data.model.api.user;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.xxl.core.data.model.api.response.DataResponseResult;


/**
 * 查询用户信息响应数据
 *
 * @author xxl.
 * @date 2021/7/27.
 */
@Keep
public class QueryUserInfoResponse extends DataResponseResult<QueryUserInfoResponse.Content> {

    //region: Content

    @Keep
    public static class Content{

        /**
         * 用户ID
         */
        @SerializedName("id")
        private String mUserId;

        /**
         * 用户昵称
         */
        @SerializedName("login")
        private String mNickName;

        /**
         * 用户头像
         */
        @SerializedName("avatar_url")
        private String mAvatarUrl;

        public String getUserId() {
            return mUserId;
        }

        public String getNickName() {
            return mNickName;
        }

        public String getAvatarUrl() {
            return mAvatarUrl;
        }
    }

    //endregion

}