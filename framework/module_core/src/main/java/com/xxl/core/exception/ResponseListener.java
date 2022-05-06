package com.xxl.core.exception;

import androidx.annotation.NonNull;
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

    /**
     * 未知异常
     *
     * @param exception
     * @return
     */
    public abstract boolean onUnKowException(@NonNull final ResponseException exception);

    /**
     * token失效
     *
     * @param exception
     * @return
     */
    public abstract boolean onTokenInvalid(@NonNull final ResponseException exception);

    /**
     * 解析异常
     *
     * @param exception
     * @return
     */
    public abstract boolean onParseException(@NonNull final ResponseException exception);

    /**
     * 网络异常
     *
     * @param exception
     * @return
     */
    public abstract boolean onNetworkException(@NonNull final ResponseException exception);

    /**
     * 异常处理
     *
     * @param exception
     */
    public boolean handleException(@NonNull final ResponseException exception) {
        if (exception.getCode() == ResponseCode.RESPONSE_CODE_UN_KNOW) {
            return onUnKowException(exception);
        }

        if (exception.getCode() == ResponseCode.RESPONSE_CODE_TOKEN_TOKEN_INVALID) {
            return onTokenInvalid(exception);
        }

        if (exception.getCode() == ResponseCode.RESPONSE_CODE_PARSE_EXCEPTION) {
            return onParseException(exception);
        }

        if (exception.getCode() == ResponseCode.RESPONSE_CODE_NETWORK_CONNECT) {
            return onNetworkException(exception);
        }

        // TODO: 2021/11/26 登录token失效，解析异常，网络异常，Http异常等
        return false;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}