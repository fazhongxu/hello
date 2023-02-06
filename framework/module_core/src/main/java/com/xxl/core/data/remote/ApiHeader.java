/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.xxl.core.data.remote;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 网络请求头Header信息
 *
 * @author xxl.
 * @date 2021/8/16.
 */
public class ApiHeader {

    private ProtectedApiHeader mProtectedApiHeader;

    private PublicApiHeader mPublicApiHeader;

    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
    }

    public Map<String, String> getProtectedApiHeader() {
        return mProtectedApiHeader.getApiHeader();
    }

    public Map<String, String> getPublicApiHeader() {
        return mPublicApiHeader.getApiHeader();
    }

    /**
     * 更新头部信息
     *
     * @param userId
     * @param accessToken
     */
    public void updateProtectedApiHeader(@NonNull final String userId,
                                         @NonNull final String accessToken) {
        if (mProtectedApiHeader != null) {
            mProtectedApiHeader.setUserId(userId);
            mProtectedApiHeader.setAccessToken(accessToken);
        }
    }

    public static final class ProtectedApiHeader {

        @Expose
        @SerializedName("user_agent")
        private String mUserAgent;

        @Expose
        @SerializedName("access_token")
        private String mAccessToken;

        @Expose
        @SerializedName("user_id")
        private String mUserId;

        public ProtectedApiHeader(String userAgent, String userId, String accessToken) {
            this.mUserAgent = userAgent;
            this.mUserId = userId;
            this.mAccessToken = accessToken;
        }

        public Map<String, String> getApiHeader() {
            Map<String, String> apiHeaderMap = new LinkedHashMap<>();
            apiHeaderMap.put("User-Agent", mUserAgent);
            apiHeaderMap.put("access_token", mAccessToken);
            apiHeaderMap.put("user_id", mUserId);
            apiHeaderMap.put("language", "zh");
            return apiHeaderMap;
        }

        public String getUserAgent() {
            return mUserAgent;
        }

        public void setUserAgent(String userAgent) {
            mUserAgent = userAgent;
        }

        public String getAccessToken() {
            return mAccessToken;
        }

        public void setAccessToken(String accessToken) {
            mAccessToken = accessToken;
        }

        public String getUserId() {
            return mUserId;
        }

        public void setUserId(String userId) {
            this.mUserId = userId;
        }
    }

    public static final class PublicApiHeader {

        @Expose
        @SerializedName("user_agent")
        private String mUserAgent;

        public PublicApiHeader(@NonNull String userAgent) {
            mUserAgent = userAgent;
        }

        public Map<String, String> getApiHeader() {
            Map<String, String> apiHeaderMap = new LinkedHashMap<>();
            apiHeaderMap.put("User-Agent", mUserAgent);
            return apiHeaderMap;
        }

        public String getUserAgent() {
            return mUserAgent;
        }

        public void setUserAgent(String userAgent) {
            mUserAgent = userAgent;
        }
    }
}
