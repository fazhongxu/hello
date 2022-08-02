package com.xxl.hello.widget.view.record;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

/**
 * 动画工具类
 *
 * @author xxl.
 * @date 2022/7/8.
 */
public class AnimatorUtils {

    private AnimatorUtils() {

    }

    /**
     * 重置ValueAnimator动画时长 防止部分手机修改动画时长导致动画不执行
     *
     * @param valueAnimator
     */
    @SuppressLint("DiscouragedPrivateApi")
    public static void resetDurationScale(@NonNull final ValueAnimator valueAnimator) {
        try {
            Method method = ValueAnimator.class.getDeclaredMethod("setDurationScale", Float.TYPE);
            method.invoke(valueAnimator, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}