package com.xxl.hello.service.queue.impl;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.utils.ThreadExpandUtils;
import com.xxl.hello.service.BaseService;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ServiceQueueRunningStatus;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.queue.api.ServiceQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xxl.
 * @date 2022/5/27.
 */
public abstract class BaseServiceQueueImpl extends BaseService implements ServiceQueue {

    //region: 成员变量

    /**
     * 线程池
     */
    protected ScheduledThreadPoolExecutor mThreadPoolExecutor;

    /**
     * 队列运行状态
     */
    @ServiceQueueRunningStatus
    private int mQueueRunningStatus = ServiceQueueRunningStatus.NULL;

    //endregion

    //region: 构造函数

    public BaseServiceQueueImpl(@NonNull final Application application,
                                @NonNull final DataRepositoryKit dataRepositoryKit,
                                @NonNull final String threadName) {
        super(application, dataRepositoryKit);
        mThreadPoolExecutor = ThreadExpandUtils.createScheduledThreadPoolExecutor(threadName);
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取核心线程数
     *
     * @return
     */
    public int getCorePoolSize() {
        synchronized (this) {
            return mThreadPoolExecutor.getCorePoolSize();
        }
    }

    /**
     * 设置队列运行状态
     *
     * @param queueRunningStatus
     */
    public void setQueueRunningStatus(@ServiceQueueRunningStatus int queueRunningStatus) {
        synchronized (this) {
            mQueueRunningStatus = queueRunningStatus;
        }
    }

    /**
     * 队列是否为空状态
     *
     * @return
     */
    public boolean isNullStatus() {
        synchronized (this) {
            return mQueueRunningStatus == ServiceQueueRunningStatus.NULL;
        }
    }

    /**
     * 队列是否空闲
     *
     * @return
     */
    public boolean isIdleStatus() {
        synchronized (this) {
            return mQueueRunningStatus == ServiceQueueRunningStatus.IDLE;
        }
    }

    /**
     * 释放资源
     */
    @Override
    public void onCleared() {
        super.onCleared();
        try {
            if (mThreadPoolExecutor != null && !mThreadPoolExecutor.isShutdown()) {
                mThreadPoolExecutor.shutdownNow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取队列运行状态
     *
     * @return
     */
    @Override
    public int getQueueRunningStatus() {
        synchronized (this) {
            return mQueueRunningStatus;
        }
    }

    /**
     * 运行服务
     */
    @Override
    public void runService() {
        execute(this::handleRunService);
    }

    /**
     * 开启服务
     */
    @Override
    public void startService(){
        execute(this::handleStartService);
    }

    /**
     * 退出服务
     */
    @Override
    public void exitService() {
        execute(this::handleExitService);
    }

    /**
     * 检查服务
     */
    @Override
    public void checkService() {
        execute(this::handleCheckService);
    }

    /**
     * 执行任务
     *
     * @param command
     */
    protected void execute(Runnable command) {
        if (command == null) {
            return;
        }
        mThreadPoolExecutor.execute(command);
    }

    //endregion

    //region: 抽象方法

    /**
     * 处理运行服务
     */
    public abstract void handleRunService();

    /**
     * 开始运行服务
     */
    public abstract void handleStartService();

    /**
     * 处理退出服务
     */
    public abstract void handleExitService();

    /**
     * 处理检查服务运行状态
     */
    public abstract void handleCheckService();

    //endregion

    //region: 内部辅助方法

    //endregion

}