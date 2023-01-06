package com.xxl.hello.service.handle.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Scheme 处理
 *
 * @author xxl.
 * @date 2023/1/6.
 */
public interface AppSchemeService {

    /**
     * 导航
     *
     * @param context
     * @param payload
     * @param isToast
     * @return
     */
    boolean navigationToScheme(@NonNull final Context context,
                               @NonNull final String payload,
                               final boolean isToast);

    /**
     * 导航
     *
     * @param context
     * @param path
     * @param isToast
     * @return
     */
    boolean navigation(@NonNull final Context context,
                       @NonNull final String path,
                       final boolean isToast);

}