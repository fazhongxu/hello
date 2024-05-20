package com.xxl.hello.service.handle.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Scheme 处理
 *
 * @author xxl.
 * @date 2023/1/6.
 */
public interface QRCodeService {

    /**
     * 解析
     *
     * @param context
     * @param result
     * @param isToast
     * @return
     */
    boolean resolve(@NonNull final Context context,
                    @NonNull final String result,
                    final boolean isToast);

}