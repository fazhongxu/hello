package com.xxl.hello;

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
     */
    public static void init(@NonNull final Application application) {
        new DoKit.Builder(application)
                .build();
    }

}