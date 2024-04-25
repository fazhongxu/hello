package com.xxl.core.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.xxl.kit.ToastUtils;

/**
 * 异常捕获类，CrashHandler 捕获了，但是还是崩溃了，这个居然可以捕获住不崩溃，记录待分析
 */
public class MyCrashHandler implements Thread.UncaughtExceptionHandler {

    public MyCrashHandler() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                        Log.e("aaa", "run: " + 123);
                    } catch (Throwable throwable) {
                        ToastUtils.warning(throwable.getMessage()).show();
                        Log.e("aaa", "run: " + throwable.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        Log.e("aaa", "run: ---" + e.getMessage());
    }
}
