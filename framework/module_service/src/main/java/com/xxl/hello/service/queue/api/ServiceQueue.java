package com.xxl.hello.service.queue.api;

/**
 * @author xxl.
 * @date 2022/5/27.
 */
public interface ServiceQueue {

    /**
     * 释放资源
     */
    void onCleared();

    /**
     * 获取队列运行状态
     *
     * @return
     */
    int getQueueRunningStatus();

    /**
     * 运行服务
     */
    void runService();

    /**
     * 开启服务
     */
    void startService();

    /**
     * 退出服务
     */
    void exitService();

    /**
     * 检查服务
     */
    void checkService();

}