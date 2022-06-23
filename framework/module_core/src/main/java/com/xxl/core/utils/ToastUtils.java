package com.xxl.core.utils;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.kit.AppUtils;

/**
 * @author xxl.
 * @date 2021/7/27.
 */
public class ToastUtils {

    private ToastUtils() {

    }

    public static void info(@NonNull final String message) {
        makeText(message, Toast.LENGTH_LONG);
    }

    public static Toast normal(@NonNull final String message) {
        return makeText(message, Toast.LENGTH_SHORT);
    }

    public static Toast show(@NonNull final String message) {
        Toast toast = makeText(message, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    private static Toast makeText(@Nullable final CharSequence text, final int duration) {
        return makeText(null, text, duration);
    }

    private static Toast makeText(@Nullable final View view,
                                  @Nullable final CharSequence text,
                                  final int duration) {
        return Toast.makeText(AppUtils.getApplication(), text, duration);
    }
}