package com.xxl.hello.service.queue.api;

/**
 * @author xxl.
 * @date 2025/5/7.
 */
public interface ServiceQueue2 {

    /**
     * 资源清理
     */
    void onCleared();

    /**
     * 获取队列状态
     *
     * @return
     */
    int getQueueStatus();

    /**
     * 开始
     */
    void start();

    /**
     * 执行
     */
    void run();

    /**
     * 停止
     */
    void stop();

}