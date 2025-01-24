package com.xxl.core.request;

import androidx.annotation.Nullable;

import com.xxl.core.response.ResponseCode;
import com.xxl.kit.OnRequestCallBack;

/**
 * @author xxl.
 * @date 2025/1/24.
 */
public abstract class RequestCallbackWrapper<T> implements OnRequestCallBack<T> {

    public abstract void onResult(int code, @Nullable T result, Throwable throwable);

    @Override
    public void onSuccess(T result) {
        onResult(ResponseCode.RESPONSE_CODE_SUCCESS, result, null);
    }

    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
        onResult(ResponseCode.RESPONSE_CODE_EXCEPTION, null, throwable);
    }

}