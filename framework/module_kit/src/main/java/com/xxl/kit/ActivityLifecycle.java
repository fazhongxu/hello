package com.xxl.kit;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Activity 页面生命周期监听
 *
 * @author xxl.
 * @date 2021/7/27.
 */
public interface ActivityLifecycle extends Application.ActivityLifecycleCallbacks {

    /**
     * 获取顶部activity
     *
     * @return
     */
    Activity getTopActivity();

    /**
     * 获取activity集合
     *
     * @return
     */
    List<Activity> getActivityList();

    /**
     * 添加前后台状态切换监听
     *
     * @param listener
     */
    void addOnAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener);

    /**
     * 移除前后台状态切换监听
     *
     * @param listener
     */
    void removeOnAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener);

    /**
     * app是否进入到前台
     *
     * @return
     */
    boolean isForeground();
}