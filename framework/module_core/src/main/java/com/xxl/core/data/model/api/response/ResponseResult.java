package com.xxl.core.data.model.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * 请求的响应结果
 *
 * @author xxl.
 * @date 2022/2/23.
 */
public abstract class ResponseResult<T> {

    //region: 成员变量

    /**
     * 请求Code
     */
    @SerializedName("code")
    private int mCode;

    /**
     * 返回的信息
     */
    @SerializedName("message")
    private String mMessage;

    /**
     * 请求返回的状态
     */
    @SerializedName("status")
    private String mStatus;

    //endregion

    //region: 构造函数

    public ResponseResult() {

    }

    //endregion

    //region: 提供方法

    /**
     * 获取响应数据
     *
     * @return
     */
    public abstract T getData();

    /**
     * 获取请求Code
     *
     * @return
     */
    public int getCode() {
        return mCode;
    }

    /**
     * 获取返回信息
     *
     * @return
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * 获取请求返回的状态
     *
     * @return
     */
    public String getStatus() {
        return mStatus;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}