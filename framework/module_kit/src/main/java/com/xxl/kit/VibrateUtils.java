package com.xxl.kit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RequiresPermission;

import com.xxl.kit.AppUtils;

import static android.Manifest.permission.VIBRATE;

/**
 * @author xxl.
 * @date 2021/12/01.
 */
public final class VibrateUtils {

    private static Vibrator vibrator;

    private VibrateUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @SuppressLint("MissingPermission")
    public static void vibrate() {
        vibrate(55);
    }

    /**
     * Vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param milliseconds The number of milliseconds to vibrate.
     */
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long milliseconds) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) {
            return;
        }
        vibrator.vibrate(milliseconds);
    }

    /**
     * Vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param pattern An array of longs of times for which to turn the vibrator on or off.
     * @param repeat  The index into pattern at which to repeat, or -1 if you don't want to repeat.
     */
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long[] pattern, final int repeat) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) {
            return;
        }
        vibrator.vibrate(pattern, repeat);
    }

    /**
     * Cancel vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     */
    @RequiresPermission(VIBRATE)
    public static void cancel() {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) {
            return;
        }
        vibrator.cancel();
    }

    private static Vibrator getVibrator() {
        if (vibrator == null) {
            vibrator = (Vibrator) AppUtils.getApplication().getSystemService(Context.VIBRATOR_SERVICE);
        }
        return vibrator;
    }
}
