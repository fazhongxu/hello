package com.xxl.kit;

import android.app.Activity;

/**
 * app前后台状态切换监听
 *
 * @author xxl.
 * @date 2021/7/26.
 */
public interface OnAppStatusChangedListener {

    /**
     * 进入前台
     *
     * @param activity
     */
    void onForeground(Activity activity);

    /**
     * 进入后台
     *
     * @param activity
     */
    void onBackground(Activity activity);
}