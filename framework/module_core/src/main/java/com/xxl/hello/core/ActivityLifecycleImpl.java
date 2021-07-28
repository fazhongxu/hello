package com.xxl.hello.core;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.core.listener.ActivityLifecycle;
import com.xxl.hello.core.listener.OnAppStatusChangedListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Activity 页面生命周期监听实现
 *
 * @author xxl.
 * @date 2021/7/26.
 */
public class ActivityLifecycleImpl implements ActivityLifecycle {

    //region: 成员变量

    /**
     * Activity集合
     */
    private final LinkedList<Activity> mActivityList = new LinkedList<>();

    /**
     * app前后台状态监听
     */
    private final List<OnAppStatusChangedListener> mStatusListeners = new CopyOnWriteArrayList<>();

    /**
     * 前台页面数量
     */
    private int mForegroundCount = 0;

    /**
     * 配置页面数量
     */
    private int mConfigCount = 0;

    /**
     * 是否处于后台
     */
    private boolean mIsBackground = false;

    //endregion

    //region: 构造函数

    public ActivityLifecycleImpl() {

    }

    //endregion

    //region: 页面生命周期

    @Override
    public void onActivityCreated(@NonNull Activity activity,
                                  @Nullable Bundle savedInstanceState) {
        if (mActivityList.size() == 0) {
            postStatus(activity, true);
        }
        setTopActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!mIsBackground) {
            setTopActivity(activity);
        }
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
            postStatus(activity, true);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                mIsBackground = true;
                postStatus(activity, false);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity,
                                            @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivityList.remove(activity);
    }

    /**
     * 更新app前后台状态
     *
     * @param activity
     * @param isForeground
     */
    private void postStatus(final Activity activity, final boolean isForeground) {
        if (mStatusListeners.isEmpty()) {
            return;
        }
        for (OnAppStatusChangedListener statusListener : mStatusListeners) {
            if (isForeground) {
                statusListener.onForeground(activity);
            } else {
                statusListener.onBackground(activity);
            }
        }
    }

    /**
     * 设置顶部activity
     *
     * @param activity
     */
    private void setTopActivity(final Activity activity) {
        if (mActivityList.contains(activity)) {
            if (!mActivityList.getFirst().equals(activity)) {
                mActivityList.remove(activity);
                mActivityList.addFirst(activity);
            }
        } else {
            mActivityList.addFirst(activity);
        }
    }

    /**
     * 获取顶部activity
     *
     * @return
     */
    @Override
    public Activity getTopActivity() {
        List<Activity> activityList = getActivityList();
        for (Activity activity : activityList) {
            if (activity == null || activity.isFinishing()) {
                continue;
            }
            return activity;
        }
        return null;
    }

    /**
     * 获取activity集合
     *
     * @return
     */
    @Override
    public List<Activity> getActivityList() {
        if (!mActivityList.isEmpty()) {
            return new LinkedList<>(mActivityList);
        }
        return new ArrayList<>();
    }


    /**
     * 添加前后台状态切换监听
     *
     * @param listener
     */
    @Override
    public void addOnAppStatusChangedListener(final OnAppStatusChangedListener listener) {
        mStatusListeners.add(listener);
    }

    /**
     * 移除前后台状态切换监听
     *
     * @param listener
     */
    @Override
    public void removeOnAppStatusChangedListener(final OnAppStatusChangedListener listener) {
        mStatusListeners.remove(listener);
    }

    /**
     * app是否进入到前台
     *
     * @return
     */
    @Override
    public boolean isForeground() {
        return !mIsBackground;
    }

    //endregion


}