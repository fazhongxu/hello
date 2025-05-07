package com.xxl.hello.service.queue.impl;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.utils.ThreadExpandUtils;
import com.xxl.hello.service.BaseService;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ServiceQueueRunningStatus;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.queue.api.ServiceQueue2;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xxl.
 * @date 2022/5/27.
 */
public abstract class BaseServiceQueueImpl2 extends BaseService implements ServiceQueue2 {

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

    public BaseServiceQueueImpl2(@NonNull final Application application,
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
     * 获取队列状态
     *
     * @return
     */
    @Override
    @ServiceQueueRunningStatus
    public int getQueueStatus() {
        return mQueueRunningStatus;
    }

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
     * 开始
     */
    @Override
    public void start() {
        mThreadPoolExecutor.scheduleAtFixedRate(this::run, 1, 10, TimeUnit.SECONDS);
    }

    /**
     * 执行
     */
    @Override
    public void run() {
        execute(this::doWork);
    }

    /**
     * 停止
     */
    @Override
    public void stop() {
        try {
            if (mThreadPoolExecutor != null) {
                mThreadPoolExecutor.shutdownNow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * 执行
     */
    public abstract void doWork();

    //endregion

    //region: 内部辅助方法

    //endregion

}