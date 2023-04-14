package com.xxl.core.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import com.didichuxing.doraemonkit.DoKit;

/**
 * DoKit 辅助类
 *
 * @author xxl.
 * @date 2023/4/14.
 */
public class DoKitHelper {

    private DoKitHelper() {

    }

    /**
     * 初始化
     *
     * @param application
     * @param isDebug
     */
    public static void init(@NonNull final Application application,
                            final boolean isDebug) {
        if (isDebug) {
            new DoKit.Builder(application)
                    .build();
        }
    }

}