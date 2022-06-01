package com.xxl.hello.service.data.model.api;

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
public class QueryUserInfoResponseTest extends DataResponseResult<QueryUserInfoResponseTest> {

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

    private QueryUserInfoResponseTest() {

    }

    public final static QueryUserInfoResponseTest obtain() {
        return new QueryUserInfoResponseTest();
    }

    //endregion

    //region: get or set

    //endregion

    //region: Inner Class Content

    @Keep
    public static class Content {


    }

    //endregion

}