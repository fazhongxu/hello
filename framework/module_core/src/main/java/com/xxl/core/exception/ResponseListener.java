package com.xxl.core.exception;

import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2021/11/26.
 */
public abstract class ResponseListener {

    //region: 成员变量

    //endregion

    //region: 构造函数

    //endregion

    //region: 提供方法

    public abstract boolean onUnKowException(@Nullable final ResponseException exception);

    /**
     * 异常处理
     *
     * @param exception
     */
    public boolean handleException(@Nullable final ResponseException exception) {
        if (exception.getCode() == ResponseCode.RESPONSE_CODE_UN_KNOW) {
            return onUnKowException(exception);
        }
        // TODO: 2021/11/26 登录token失效，解析异常，网络异常，Http异常等
        return false;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}