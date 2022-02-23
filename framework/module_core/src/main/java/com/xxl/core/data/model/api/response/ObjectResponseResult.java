package com.xxl.core.data.model.api.response;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author xxl.
 * @date 2022/2/23.
 */
public class ObjectResponseResult<T> extends ResponseResult<T> {

    //region: 成员变量

    @SerializedName("object")
    private T mData;

    //endregion

    //region: 构造函数

    private ObjectResponseResult() {

    }

    /**
     * 获取响应数据
     *
     * @return
     */
    @Override
    public T getData() {
        return mData;
    }

    public final static ObjectResponseResult obtain() {
        return new ObjectResponseResult();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}