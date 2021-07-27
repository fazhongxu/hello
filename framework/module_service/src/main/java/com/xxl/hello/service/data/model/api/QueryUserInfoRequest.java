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
     * 用户ID
     */
    private static final String REQUEST_KEY_USER_ID = "userId";

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
     * 设置目标用户名称
     *
     * @param targetUserId
     * @return
     */
    public QueryUserInfoRequest setTargetUserId(@NonNull final String targetUserId) {
        sFiled.put(REQUEST_KEY_USER_ID, targetUserId);
        return this;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public String getTargetUserId() {
        return (String) sFiled.get(REQUEST_KEY_USER_ID);
    }

    //endregion

}