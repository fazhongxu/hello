package com.xxl.hello.common.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Log 打印工具
 *
 * @author xxl.
 * @date 2021/7/23.
 */
public class LogUtils {

    //region: 成员变量

    /**
     * 是否是debug模式
     */
    private static boolean sIsDebug;

    //endregion

    //region: 构造函数

    private LogUtils() {

    }

    //endregion

    //region: 提供方法

    /**
     * 初始化
     *
     * @param isDebug
     */
    public static void init(final boolean isDebug) {
        sIsDebug = isDebug;

        final FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("hello")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority,
                                      @Nullable String tag) {
                return sIsDebug;
            }
        });
    }

    public static void i(@NonNull final String message) {
        if (!sIsDebug) {
            return;
        }
        Logger.i(message);
    }

    public static void d(@NonNull final String message) {
        if (!sIsDebug) {
            return;
        }
        Logger.d(message);
    }

    public static void w(@NonNull final String message) {
        if (!sIsDebug) {
            return;
        }
        Logger.w(message);
    }

    public static void e(@NonNull final String message) {
        if (!sIsDebug) {
            return;
        }
        Logger.e(message);
    }

    public static void e(@NonNull final Object object) {
        if (!sIsDebug) {
            return;
        }
        if (object instanceof String) {
            Logger.e((String) object);
        } else {
            final String json = GsonUtils.toJson(object);
            if (!TextUtils.isEmpty(json)) {
                Logger.e(json);
            }
        }
    }

    //endregion


}