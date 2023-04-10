package com.xxl.core.manager;

import com.xxl.core.utils.BuglyUtils;
import com.xxl.kit.LogUtils;

public final class ExceptionServiceManager {

    /**
     * 上传异常信息
     *
     * @param tag 异常标签
     * @param e   异常信息
     */
    public final static void postCaughtException(final String tag,
                                                 final Exception e) {
        if (e != null) {
            LogUtils.e(tag + "-" + e.getMessage());
        }
    }

    /**
     * 上传异常信息
     *
     * @param tag       异常标签
     * @param throwable 异常信息
     */
    public final static void postCaughtException(final String tag,
                                                 final Throwable throwable) {
        if (throwable != null) {
            LogUtils.e(tag + "-" + throwable.getMessage());
        }
    }

    /**
     * 上传错误信息到bugly
     *
     * @param throwable
     */
    public final static void postCaughtException(final Throwable throwable) {
        if (throwable != null) {
            LogUtils.e(throwable.getMessage());
            BuglyUtils.postCatchedException(throwable);
        }
    }

    private ExceptionServiceManager() {
    }
}
