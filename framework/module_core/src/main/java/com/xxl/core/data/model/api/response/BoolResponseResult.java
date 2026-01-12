package com.xxl.core.data.model.api.response;

/**
 *
 * @author xxl.
 * @date 2022/2/23.
 */
public class BoolResponseResult<T> extends ResponseResult<Boolean> {

    //region: get or set

    /**
     * 获取响应数据
     *
     * @return
     */
    @Override
    public Boolean getData() {
        return isSuccess();
    }

    //endregion
}