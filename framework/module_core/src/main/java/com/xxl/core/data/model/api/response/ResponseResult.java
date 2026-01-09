package com.xxl.core.data.model.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * 请求的响应结果
 *
 * @author xxl.
 * @date 2022/2/23.
 */
public class ResponseResult<T> {

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
     * 数据
     */
    @SerializedName("data")
    private T mData;

    //endregion

    //region: 构造函数

    public ResponseResult() {

    }

    //endregion

    //region: 提供方法

    /**
     * 获取数据
     *
     * @return
     */
    public T getData() {
        return mData;
    }

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

    //endregion

    //region: 内部辅助方法

    //endregion

}