package com.xxl.core.service.download;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 下载状态
 *
 * @author xxl.
 * @date 2022/7/4.
 */
@IntDef({DownloadState.STATE_OTHER,
        DownloadState.STATE_FAIL,
        DownloadState.STATE_COMPLETE,
        DownloadState.STATE_STOP,
        DownloadState.STATE_WAIT,
        DownloadState.STATE_RUNNING,
        DownloadState.STATE_PRE,
        DownloadState.STATE_POST_PRE,
        DownloadState.STATE_CANCEL,
})
@Retention(RetentionPolicy.SOURCE)
public @interface DownloadState {

    /**
     * 其它状态
     */
    int STATE_OTHER = -1;

    /**
     * 失败状态
     */
    int STATE_FAIL = 0;

    /**
     * 完成状态
     */
    int STATE_COMPLETE = 1;

    /**
     * 停止状态
     */
    int STATE_STOP = 2;

    /**
     * 等待状态
     */
    int STATE_WAIT = 3;

    /**
     * 正在执行
     */
    int STATE_RUNNING = 4;

    /**
     * 预处理
     */
    int STATE_PRE = 5;

    /**
     * 预处理完成
     */
    int STATE_POST_PRE = 6;

    /**
     * 删除任务
     */
    int STATE_CANCEL = 7;
}