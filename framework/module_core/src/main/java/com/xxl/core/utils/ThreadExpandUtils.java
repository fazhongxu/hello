package com.xxl.core.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程扩展工具类
 * <pre>
 * CPU密集型的任务配置尽可能少的线程数量：
 * 一般公式：CPU核数+1个线程的线程数
 *
 * corePoolSize 一般为 CPU核心数 Runtime.getRuntime().availableProcessors() +1
 *
 * 由于IO密集型任务的线程并不是一直在执行任务，则应配置尽可能多的线程，如CPU核数*2
 *
 * maximumPoolSize 一般为 CPU核心数 Runtime.getRuntime().availableProcessors() * 2 （4 核 8线程 之类的）
 *
 *  public ThreadPoolExecutor(int corePoolSize, // 线程池长期维持的线程数，即使线程处于Idle空闲状态，也不会回收
 *                            int maximumPoolSize,// 线程数上限
 *                            long keepAliveTime,   // 超过 corePoolSize 线程数量，超过这个时间，线程就会被回收
 *                            TimeUnit unit,        // keepAliveTime 时间单位
 *                            BlockingQueue<Runnable> workQueue,// 任务的排队队列
 *                            ThreadFactory threadFactory, // 新线程创建方式
 *                            RejectedExecutionHandler handler // 拒绝策略
 *  }
 *  </pre>
 *
 * @author xxl.
 * @date 2022/05/01.
 */
public final class ThreadExpandUtils {

    /**
     * CPU核心数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池长期维持的线程数，即使线程处于Idle空闲状态，也不会回收
     * 一般公示：CPU核数 +1
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    /**
     * 线程数上限
     * 一般公示：CPU核数*2
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2;

    /**
     * 超过 corePoolSize 线程数量，超过这个时间，线程就会被回收
     */
    private static final long KEEP_ALIVE_TIME = TimeUnit.SECONDS.toSeconds(60);

    /**
     * 创建默认线程池
     *
     * @return
     */
    public static ThreadPoolExecutor createDefaultThreadPoolExecutor() {
        return createDefaultThreadPoolExecutor(String.format("%s", "Default thread"));
    }

    /**
     * 创建默认线程池
     *
     * @param threadName 线程名称
     * @return
     */
    public static ThreadPoolExecutor createDefaultThreadPoolExecutor(@NonNull final String threadName) {
        return new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(512),
                threadFactory(threadName, true));
    }

    /**
     * 创建有计划的线程池
     *
     * @param threadName 线程名称
     * @return
     */
    public static ScheduledThreadPoolExecutor createScheduledThreadPoolExecutor(@NonNull final String threadName) {
        return new ScheduledThreadPoolExecutor(CORE_POOL_SIZE, threadFactory(threadName, true));
    }

    /**
     * 创建线程池
     *
     * @param corePoolSize
     * @param threadName
     * @return
     */
    public static ExecutorService createPoolExecutor(final int corePoolSize,
                                                     final String threadName) {
        return new ThreadPoolExecutor(corePoolSize,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(512),
                threadFactory(threadName, true));
    }

    /**
     * 线程工厂
     *
     * @param name   线程名称
     * @param daemon 是否需要守护进程
     * @return
     */
    public static ThreadFactory threadFactory(String name, boolean daemon) {
        return runnable -> {
            Thread result = new Thread(runnable, name);
            result.setDaemon(daemon);
            return result;
        };
    }
}
