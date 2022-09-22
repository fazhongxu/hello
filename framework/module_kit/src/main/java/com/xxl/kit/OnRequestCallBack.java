package com.xxl.kit;

import androidx.annotation.Nullable;

/**
 * 通用接口回调
 *
 * @author xxl.
 * @date 2022/5/28.
 */
public interface OnRequestCallBack<T> {

    /**
     * 请求成功
     *
     * @param t
     */
    void onSuccess(@Nullable final T t);

    /**
     * 请求失败
     *
     * @param throwable
     */
    default void onFailure(@Nullable final Throwable throwable) {
        LogUtils.e(throwable);
    }
}