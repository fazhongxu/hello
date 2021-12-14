package com.xxl.core.utils;

/**
 * View 相关工具类 findViewById,设置padding,margin,背景色，文本，获取layoutParams等
 *
 * @author xxl.
 * @date 2021/12/14.
 */
public final class ViewUtils {

    /**
     * 最近一次点击的时间戳（毫秒）
     */
    private static long sLastClickTimeMillis = 0;

    /**
     * 防止重复点击的间隔时间（毫秒）
     */
    private static final long DEFAULT_CLICK_MILLIS = 500L;

    private ViewUtils() {

    }

    /**
     * 判断是否是快速点击（重复点击）
     *
     * @return
     */
    public static boolean isFastClick() {
        return isFastClick(DEFAULT_CLICK_MILLIS);
    }

    /**
     * 判断是否是快速点击（重复点击）
     *
     * @return
     */
    public static boolean isFastClick(final long clickTimeMillis) {
        final long currentTimeMillis = System.currentTimeMillis();
        final boolean isFastClick = currentTimeMillis - sLastClickTimeMillis < (clickTimeMillis > 0 ? clickTimeMillis : DEFAULT_CLICK_MILLIS);
        sLastClickTimeMillis = currentTimeMillis;
        return isFastClick;
    }
}