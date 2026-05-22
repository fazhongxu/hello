package com.xxl.hello.widget.ui.video;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.xxl.core.service.download.DownloadListener;
import com.xxl.core.service.download.DownloadOptions;
import com.xxl.core.service.download.DownloadServiceWrapper;
import com.xxl.core.service.download.DownloadTaskEntity;
import com.xxl.core.service.download.DownloadTaskInfo;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * 视频下载页面
 *
 * @author xxl.
 * @date 2026/05/21.
 */
@HiltViewModel
public class VideoDownloadViewModel extends BaseViewModel<VideoDownloadNavigator> {

    //region: 成员变量

    private final DataRepositoryKit mDataRepositoryKit;

    private DownloadServiceWrapper mDownloadServiceWrapper;

    /**
     * 视频下载链接
     */
    public ObservableField<String> videoUrl = new ObservableField<>("");

    /**
     * 下载进度
     */
    public ObservableField<Integer> downloadProgress = new ObservableField<>(0);

    /**
     * 下载状态文本
     */
    public ObservableField<String> downloadStatusText = new ObservableField<>("");

    /**
     * 是否正在下载
     */
    public ObservableField<Boolean> isDownloading = new ObservableField<>(false);

    /**
     * 视频标题
     */
    public ObservableField<String> videoTitle = new ObservableField<>("");

    /**
     * 视频文件大小
     */
    public ObservableField<String> videoFileSize = new ObservableField<>("");

    /**
     * 下载任务信息
     */
    private DownloadTaskInfo mCurrentTaskInfo;

    //endregion

    //region: 构造函数

    @Inject
    public VideoDownloadViewModel(@NonNull final Application application,
                                  @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
    }

    //endregion

    //region: 提供方法

    /**
     * 设置下载服务包装类
     *
     * @param downloadServiceWrapper
     */
    public void setDownloadServiceWrapper(@NonNull final DownloadServiceWrapper downloadServiceWrapper) {
        mDownloadServiceWrapper = downloadServiceWrapper;
    }

    /**
     * 设置视频下载链接
     *
     * @param targetVideoUrl
     */
    public void setVideoUrl(@NonNull final String targetVideoUrl) {
        videoUrl.set(targetVideoUrl);
    }

    /**
     * 开始下载视频
     */
    public void startDownload() {
        final String targetVideoUrl = videoUrl.get();
        if (targetVideoUrl == null || targetVideoUrl.isEmpty()) {
            return;
        }
        if (mDownloadServiceWrapper == null) {
            return;
        }

        isDownloading.set(true);
        downloadStatusText.set("准备下载...");

        final DownloadOptions downloadOptions = DownloadOptions.create(targetVideoUrl)
                .setFileExtension("mp4");

        mDownloadServiceWrapper.createDownloadTask(null, downloadOptions, new DownloadListener() {

            @Override
            public void onTaskCreated(@NonNull DownloadTaskInfo targetDownloadTaskInfo) {
                mCurrentTaskInfo = targetDownloadTaskInfo;
                downloadStatusText.set("等待下载...");
            }

            @Override
            public void onTaskStart(@NonNull DownloadTaskEntity taskEntity) {
                downloadStatusText.set("开始下载...");
            }

            @Override
            public void onTaskRunning(@NonNull DownloadTaskEntity taskEntity) {
                long fileSize = taskEntity.getFileSize();
                long currentProgress = taskEntity.getCurrentProgress();
                int progress = fileSize > 0 ? (int) (currentProgress * 100 / fileSize) : 0;
                downloadProgress.set(progress);
                downloadStatusText.set("下载中 " + progress + "%");
                getNavigator().onDownloadProgress(progress);
            }

            @Override
            public void onTaskComplete(@NonNull DownloadTaskEntity taskEntity) {
                downloadProgress.set(100);
                downloadStatusText.set("下载完成");
                isDownloading.set(false);
                getNavigator().onDownloadComplete(taskEntity.getSavePath());
            }

            @Override
            public void onTaskFail(@NonNull DownloadTaskEntity taskEntity,
                                   Throwable throwable) {
                downloadStatusText.set("下载失败");
                isDownloading.set(false);
                getNavigator().onDownloadFail(throwable != null ? throwable.getMessage() : "未知错误");
            }
        });
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mDownloadServiceWrapper != null) {
            mDownloadServiceWrapper.onCleared();
        }
    }

    //endregion

}
