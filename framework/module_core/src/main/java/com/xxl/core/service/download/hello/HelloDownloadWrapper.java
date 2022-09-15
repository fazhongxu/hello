package com.xxl.core.service.download.hello;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.core.service.download.DownloadState;
import com.xxl.kit.DownloadUtil;

import java.io.File;
import java.util.UUID;

/**
 * @author xxl.
 * @date 2022/7/6.
 */
public class HelloDownloadWrapper {

    //region: 成员变量

    private static HelloDownloadWrapper sInstance;

    //endregion

    //region: 构造函数

    private HelloDownloadWrapper() {

    }

    public static HelloDownloadWrapper getInstance() {
        if (sInstance == null) {
            synchronized (HelloDownloadWrapper.class) {
                if (sInstance == null) {
                    sInstance = new HelloDownloadWrapper();
                }
            }
        }
        return sInstance;
    }

    //endregion

    //region: 提供方法

    /**
     * 创建下载任务
     *
     * @return
     */
    public int create(String url, String fileSavePath, OnDownloadListener listener) {
        if (null == url) {
            throw new NullPointerException("下载地址不能为空!");
        }
        if (null == fileSavePath) {
            throw new NullPointerException("文件保存路径不能为空!");
        }

        final DownloadTask downloadTask = DownloadTask.create()
                .setDownloadKey(url)
                .setFileSavePath(fileSavePath)
                .setTaskState(DownloadState.STATE_POST_PRE);

        File file = new File(fileSavePath);
        DownloadUtil.get()
                .download(url, file.getParent(), file.getName(), new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(File file) {
                        if (listener != null) {
                            downloadTask.setTaskState(DownloadState.STATE_COMPLETE);
                            listener.onDownloadSuccess(downloadTask);
                        }
                    }

                    @Override
                    public void onDownloading(long totalSize,
                                              long currentSize) {
                        if (listener != null) {
                            downloadTask.setFileSize(totalSize);
                            downloadTask.setCurrentProgress(currentSize);
                            downloadTask.setTaskState(DownloadState.STATE_RUNNING);
                            listener.onDownloading(downloadTask);
                        }
                    }

                    @Override
                    public void onDownloadFailed(Throwable e) {
                        if (listener != null) {
                            downloadTask.setTaskState(DownloadState.STATE_FAIL);
                            listener.onDownloadFailed(downloadTask, e);
                        }
                    }
                });

        return UUID.randomUUID().hashCode();
    }

    /**
     * 停止下载
     *
     * @param taskId 任务ID
     */
    public void stop(final long taskId) {
        // TODO: 2022/7/6  
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