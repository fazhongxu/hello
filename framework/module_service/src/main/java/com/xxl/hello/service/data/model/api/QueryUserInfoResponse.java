package com.xxl.hello.service.data.model.api;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;



/**
 * 查询用户信息响应数据
 *
 * @author xxl.
 * @date 2021/7/27.
 */
@Keep
public class QueryUserInfoResponse {

    //region: 成员变量

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


    //endregion

    //region: 构造函数

    private QueryUserInfoResponse() {

    }

    public final static QueryUserInfoResponse obtain() {
        return new QueryUserInfoResponse();
    }

    //endregion

    //region: get or set

    public String getUserId() {
        return mUserId;
    }

    public String getNickName() {
        return mNickName;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    //endregion

    //region: Inner Class Content

    @Keep
    public static class Content {


    }

    //endregion

}