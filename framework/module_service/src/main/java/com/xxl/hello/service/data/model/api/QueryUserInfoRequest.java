package com.xxl.hello.service.data.model.api;

import androidx.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询用户信息请求参数
 *
 * @author xxl.
 * @date 2021/7/27.
 */
public class QueryUserInfoRequest {

    //region: 成员变量

    private static final Map<String, Object> sFiled = new LinkedHashMap<>();

    /**
     * 用户昵称
     */
    private static final String REQUEST_KEY_USER_NAME = "username";

    //endregion

    //region: 构造函数

    private QueryUserInfoRequest() {

    }

    public final static QueryUserInfoRequest obtain() {
        return new QueryUserInfoRequest();
    }

    //endregion

    //region: get or set

    /**
     * 设置目标用户昵称
     *
     * @param targetUserName
     * @return
     */
    public QueryUserInfoRequest setTargetUserName(@NonNull final String targetUserName) {
        sFiled.put(REQUEST_KEY_USER_NAME, targetUserName);
        return this;
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public String getTargetUserName() {
        return (String) sFiled.get(REQUEST_KEY_USER_NAME);
    }

    //endregion

}