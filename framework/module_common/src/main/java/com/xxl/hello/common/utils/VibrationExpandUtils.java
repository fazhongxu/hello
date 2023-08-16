package com.xxl.hello.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * 震动工具类
 *
 * @author xxl.
 * @date 2023/8/15.
 */
public final class VibrationExpandUtils {


    private VibrationExpandUtils() {

    }

    private static Vibrator sVibrator;

    /**
     * 波形振动
     *
     * @param context
     * @param timings 交替开关定时的模式，从关闭开始。0的定时值将导致定时/振幅对被忽略。
     * @param repeat  指定pattern数组的索引，指定pattern数组中从repeat索引开始的震动进行循环。-1表示只震动一次，非-1表示从 pattern的指定下标开始重复震动。
     */
    @SuppressLint("MissingPermission")
    public static void vibrate(final Context context,
                               final long[] timings,
                               final int repeat) {
        sVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (sVibrator.hasVibrator()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect vibe = VibrationEffect.createWaveform(timings, repeat);
                sVibrator.vibrate(vibe);
            } else {
                sVibrator.vibrate(timings, repeat);
            }
        }
    }

    /**
     * 取消震动
     */
    @SuppressLint("MissingPermission")
    public static void cancel() {
        if (sVibrator != null) {
            sVibrator.cancel();
        }
    }


}