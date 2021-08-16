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

package com.xxl.hello.core.data.remote;

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

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }

    public static final class ProtectedApiHeader {

        @Expose
        @SerializedName("access_token")
        private String mAccessToken;

        @Expose
        @SerializedName("api_key")
        private String mApiKey;

        @Expose
        @SerializedName("user_id")
        private String mUserId;

        public ProtectedApiHeader(String apiKey, String userId, String accessToken) {
            this.mApiKey = apiKey;
            this.mUserId = userId;
            this.mAccessToken = accessToken;
        }

        public Map<String, String> getApiHeader() {
            Map<String, String> apiHeaderMap = new LinkedHashMap<>();
            apiHeaderMap.put("api_key", mApiKey);
            apiHeaderMap.put("access_token", mAccessToken);
            apiHeaderMap.put("user_id", mUserId);
            apiHeaderMap.put("language", "zh");
            return apiHeaderMap;
        }

        public String getAccessToken() {
            return mAccessToken;
        }

        public void setAccessToken(String accessToken) {
            mAccessToken = accessToken;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
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
        @SerializedName("api_key")
        private String mApiKey;

        public PublicApiHeader(@NonNull String apiKey) {
            mApiKey = apiKey;
        }

        public Map<String, String> getApiHeader() {
            Map<String, String> apiHeaderMap = new LinkedHashMap<>();
            apiHeaderMap.put("api_key", mApiKey);
            return apiHeaderMap;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
        }
    }
}
