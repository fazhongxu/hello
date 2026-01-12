package com.xxl.core.data.model.api.response;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author xxl.
 * @date 2022/2/23.
 */
public class DataResponseResult<T> extends ResponseResult<T> {

    //region: 成员变量

    @SerializedName("data")
    private T mData;

    //endregion

    //region: get or set

    /**
     * 获取响应数据
     *
     * @return
     */
    @Override
    public T getData() {
        return mData;
    }

    //endregion
}