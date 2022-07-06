package com.xxl.core.service.download.hello;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2022/7/6.
 */
public class HelloDownloadWrapper {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public HelloDownloadWrapper(@NonNull final Application application) {

    }

    //endregion

    //region: 提供方法

    public void download() {
        DownloadTask downloadTask = DownloadTask.create();
    }


    //endregion

    //region: Inner Class OnDownloadListener

    public interface OnDownloadListener {

        /**
         * 下载成功
         *
         * @param downloadTask
         */
        void onDownloadSuccess(@NonNull final DownloadTask downloadTask);

        /**
         * 下载中
         *
         * @param downloadTask
         */
        default void onDownloading(@NonNull final DownloadTask downloadTask) {

        }


        /**
         * 下载异常信息
         *
         * @param downloadTask
         * @param throwable
         */
        void onDownloadFailed(@NonNull final DownloadTask downloadTask,
                              @Nullable final Throwable throwable);
    }

    //endregion

}