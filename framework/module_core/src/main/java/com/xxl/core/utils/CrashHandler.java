package com.xxl.core.utils;

import android.app.Application;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.kit.AppUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.TimeUtils;
import com.xxl.kit.ToastUtils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 全局异常捕获处理
 *
 * @author xxl.
 * @date 2022/9/14.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String PRE_NAME = "com.xxl.pre";

    public static final String KEY_APP_LAST_CRASH_TIME = "key_app_last_crash_time";
    public static final String KEY_APP_LAST_CRASH_MESSAGE = "key_app_last_crash_message";
    public static final long KILL_APP_TIME_INTERVAL_MILLS = 5000;

    private static CrashHandler sInstance;

    /**
     * 上下文
     */
    private Application mApplication;

    private String TAG = CrashHandler.class.getSimpleName();

    /**
     * 是否是debug
     */
    private boolean mIsDebug;

    /**
     * 系统默认异常处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    /**
     * 线程池
     */
    private ThreadPoolExecutor mThreadPoolExecutor;

    private CrashHandler() {

    }

    /**
     * 获取单例
     *
     * @return
     */
    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    sInstance = new CrashHandler();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化
     *
     * @param application
     * @param tag
     * @param isDebug
     */
    public void init(@NonNull final Application application,
                     @NonNull final String tag,
                     final boolean isDebug) {
        mApplication = application;
        TAG = tag;
        mIsDebug = isDebug;
        mThreadPoolExecutor = ThreadExpandUtils.createDefaultThreadPoolExecutor(String.format("%s Thread", CrashHandler.class.getSimpleName()));
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 获取app最后崩溃的信息
     *
     * @return
     */
    public String getAppLastCrashMessage() {
        return CacheUtils.decodeString(PRE_NAME, KEY_APP_LAST_CRASH_MESSAGE);
    }

    /**
     * 清除app最后崩溃的信息
     *
     * @return
     */
    public void clearAppLastCrashMessage() {
        CacheUtils.removeValueForKey(PRE_NAME, KEY_APP_LAST_CRASH_MESSAGE);
    }

    @Override
    public void uncaughtException(@Nullable Thread thread,
                                  @Nullable Throwable throwable) {
        final Runnable runnable = () -> {
            LogUtils.e("程序发生了一点小意外 " + (thread == null ? "" : throwable.getMessage()));
            if (handleException(throwable)) {
                return;
            }
            mDefaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
        };
        mThreadPoolExecutor.execute(runnable);
    }

    /**
     * 处理异常（重启，保存异常）
     *
     * @param throwable
     */
    private boolean handleException(@Nullable Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        CacheUtils.encode(PRE_NAME, KEY_APP_LAST_CRASH_TIME, TimeUtils.currentServiceTimeMillis());
        CacheUtils.encode(PRE_NAME, KEY_APP_LAST_CRASH_MESSAGE, throwable.toString());
        if (TimeUtils.currentServiceTimeMillis() - CacheUtils.decodeLong(PRE_NAME, KEY_APP_LAST_CRASH_TIME) <= KILL_APP_TIME_INTERVAL_MILLS) {
            System.exit(0);
        } else {
            if (mIsDebug) {
                Looper.prepare();
                ToastUtils.warning(throwable.getMessage()).show();
                Looper.loop();
                AppUtils.exitApp();
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}