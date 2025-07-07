package com.xxl.hello.service.queue.impl;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.utils.ThreadExpandUtils;
import com.xxl.hello.service.BaseService;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ServiceQueueStatus;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.queue.api.ServiceQueue;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    @ServiceQueueStatus
    private int mQueueStatus = ServiceQueueStatus.NULL;

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
     * 获取队列状态
     *
     * @return
     */
    @ServiceQueueStatus
    @Override
    public int getQueueStatus() {
        return mQueueStatus;
    }

    /**
     * 设置队列状态
     *
     * @param queueStatus
     */
    public void setQueueStatus(@ServiceQueueStatus int queueStatus) {
        synchronized (this) {
            mQueueStatus = queueStatus;
        }
    }

    /**
     * 队列是否为空状态
     *
     * @return
     */
    public boolean isNullStatus() {
        synchronized (this) {
            return getQueueStatus() == ServiceQueueStatus.NULL;
        }
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