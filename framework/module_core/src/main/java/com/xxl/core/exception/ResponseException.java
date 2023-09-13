package com.xxl.core.exception;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.xxl.core.ui.state.UnKnowExceptionState;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * @author xxl.
 * @date 2021/11/26.
 */
public class ResponseException extends Exception {

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
     * 返回的数据
     */
    @SerializedName("data")
    private Object mData;

    //endregion

    //region: 构造函数

    public ResponseException() {

    }

    public ResponseException(final int code,
                             @NonNull final String message) {
        mCode = code;
        mMessage = message;
    }

    public static ResponseException create(final int code,
                                           @NonNull final String message) {
        return new ResponseException(code, message);
    }

    //endregion

    //region: 提供方法

    /**
     * Token失效
     *
     * @return
     */
    public boolean isTokenInvalid() {
        return mCode == ResponseCode.RESPONSE_CODE_TOKEN_TOKEN_INVALID;
    }

    /**
     * 异常转换
     *
     * @param throwable
     * @return
     */
    public static ResponseException converter(@Nullable final Throwable throwable) {
        if (throwable instanceof ResponseException) {
            return (ResponseException) throwable;
        }
        return handleException(throwable);
    }

    /**
     * 处理异常
     *
     * @param throwable
     * @return
     */
    public static ResponseException handleException(@Nullable final Throwable throwable) {
        if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException) {
            return create(ResponseCode.RESPONSE_CODE_PARSE_EXCEPTION, throwable.getMessage());
        }

        if (throwable instanceof ConnectException) {
            return create(ResponseCode.RESPONSE_CODE_NETWORK_CONNECT, throwable.getMessage());
        }
        if (throwable instanceof UnknownHostException
                || throwable instanceof SocketTimeoutException) {
            return create(ResponseCode.RESPONSE_CODE_HTTP_ERROR, throwable.getMessage());
        }

        // TODO: 2021/11/26 处理token失效等异常
        return create(ResponseCode.RESPONSE_CODE_UN_KNOW, throwable.getMessage());
    }

    public int getCode() {
        return mCode;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}