package com.xxl.hello.core.utils;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2021/7/27.
 */
public class ToastUtils {

    private ToastUtils() {

    }

    public static void info(@NonNull final String message) {
        show(message, Toast.LENGTH_LONG);
    }

    public static void show(@NonNull final String message) {
        show(message, Toast.LENGTH_LONG);
    }

    private static void show(@Nullable final CharSequence text, final int duration) {
        show(null, text, duration);
    }

    private static void show(@Nullable final View view,
                             @Nullable final CharSequence text,
                             final int duration) {
        Toast.makeText(AppUtils.getApplication(), text, duration).show();
    }
}