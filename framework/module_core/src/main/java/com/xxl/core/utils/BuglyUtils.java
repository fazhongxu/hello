package com.xxl.core.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * bugly 工具类
 *
 * @author xxl.
 * @date 2023/4/10.
 */
public class BuglyUtils {

    /**
     * 初始化
     *
     * @param application
     * @param appId
     * @param isDebug
     */
    public static void init(@NonNull final Application application,
                            @NonNull final String appId,
                            final boolean isDebug) {
        CrashReport.initCrashReport(application, appId, isDebug);
    }

    /**
     * 测试java异常
     */
    public static void testJavaCrash() {
        CrashReport.testJavaCrash();
    }

    /**
     * 测试native异常
     */
    public static void testNativeCrash() {
        CrashReport.testNativeCrash();
    }

    /**
     * 测试anr异常
     */
    public static void testANRCrash() {
        CrashReport.testANRCrash();
    }

    /**
     * 手动上传异常（上传后可在bugly后台错误分析查看）
     *
     * @param throwable
     */
    public static void postCatchedException(Throwable throwable) {
        CrashReport.postCatchedException(throwable);
    }

    /**
     * 手动上传异常 上传后可在bugly后台错误分析查看）
     *
     * @param throwable
     * @param thread
     */

    public static void postCatchedException(Throwable throwable, Thread thread) {
        CrashReport.postCatchedException(throwable, thread);
    }

    private BuglyUtils() {

    }


}