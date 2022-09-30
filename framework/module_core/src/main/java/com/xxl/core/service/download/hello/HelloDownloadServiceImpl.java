package com.xxl.core.service.download.hello;

import android.Manifest;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.core.R;
import com.xxl.core.service.download.DownloadListener;
import com.xxl.core.service.download.DownloadOptions;
import com.xxl.core.service.download.DownloadService;
import com.xxl.core.service.download.DownloadServiceUtils;
import com.xxl.core.service.download.DownloadTaskInfo;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello 下载服务
 *
 * @author xxl.
 * @date 2022/7/5.
 */
public class HelloDownloadServiceImpl implements DownloadService, HelloDownloadWrapper.OnDownloadListener {

    //region: 成员变量

    /**
     * tag
     */
    private static final String TAG = "Hello ";

    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 下载监听
     */
    private List<DownloadListener> mDownloadListeners = new ArrayList<>();

    //endregion

    //region: 构造函数

    public HelloDownloadServiceImpl() {

    }

    //region: DownloadService

    /**
     * 注册下载服务
     *
     * @param application      上下文
     * @param downloadListener 下载监听
     */
    @Override
    public void register(@NonNull Application application,
                         @Nullable DownloadListener downloadListener) {
        if (DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "download register");
        }
        if (downloadListener != null) {
            mDownloadListeners.add(downloadListener);
        }
    }

    /**
     * 注销下载服务
     *
     * @param downloadListener 下载监听
     */
    @Override
    public void unRegister(@Nullable DownloadListener downloadListener) {

        if (downloadListener != null) {
            mDownloadListeners.remove(downloadListener);
        }
    }

    /**
     * 创建并启动下载任务
     *
     * @param object          Activity、Service、Application、DialogFragment、Fragment、PopupWindow、Dialog
     * @param downloadOptions 下载配置
     * @return
     */
    @Override
    public DownloadTaskInfo createDownloadTask(@NonNull Object object,
                                               @NonNull DownloadOptions downloadOptions) {
        final int taskId = HelloDownloadWrapper.getInstance()
                .create(downloadOptions.getUrl(), downloadOptions.getFilePath(), this);

        return DownloadTaskInfo.create(downloadOptions.getTargetDownloadTag())
                .setTaskId(String.valueOf(taskId))
                .setUrl(downloadOptions.getUrl());
    }

    /**
     * 创建并启动下载任务
     *
     * @param activity        activity
     * @param downloadOptions 下载配置
     * @param callBack        回调监听
     * @return
     */
    @Override
    public void createDownloadTask(@NonNull final FragmentActivity activity,
                                   @NonNull final DownloadOptions downloadOptions,
                                   @NonNull final OnRequestCallBack<DownloadTaskInfo> callBack) {
        if (activity == null) {
            callBack.onSuccess(createDownloadTask(activity, downloadOptions));
            return;
        }
        mHandler.post(() -> {
            final RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(isSuccess -> {
                        if (isSuccess) {
                            callBack.onSuccess(createDownloadTask(activity, downloadOptions));
                        } else {
                            callBack.onFailure(new Throwable(StringUtils.getString(R.string.core_permission_read_of_white_external_storage_failure_tips)));
                        }
                    }, throwable -> {
                        LogUtils.e("创建下载任务失败" + throwable);
                        callBack.onFailure(throwable);
                    });
        });
    }

    /**
     * 停止下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    @Override
    public void stopDownloadTask(@Nullable DownloadTaskInfo downloadTaskInfo) {

    }

    /**
     * 取消下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    @Override
    public void cancelDownloadTask(@Nullable DownloadTaskInfo downloadTaskInfo) {

    }

    /**
     * 恢复下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    @Override
    public void resumeDownloadTask(@Nullable DownloadTaskInfo downloadTaskInfo) {

    }

    //endregion

    //region: OnDownloadListener

    /**
     * 下载成功
     *
     * @param downloadTask
     */
    @Override
    public void onDownloadSuccess(@NonNull DownloadTask downloadTask) {
        if (DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onDownloadSuccess ");
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskComplete(HelloDownloadTaskEntity.obtain(downloadTask));
            }
        }
    }

    /**
     * 下载中
     *
     * @param downloadTask
     */
    @Override
    public void onDownloading(@NonNull DownloadTask downloadTask) {
        if (DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onDownloading ");
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskRunning(HelloDownloadTaskEntity.obtain(downloadTask));
            }
        }
    }

    /**
     * 下载异常信息
     *
     * @param downloadTask
     * @param throwable
     */
    @Override
    public void onDownloadFailed(@NonNull DownloadTask downloadTask,
                                 @Nullable Throwable throwable) {
        if (DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onDownloadFailed ");
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskFail(HelloDownloadTaskEntity.obtain(downloadTask), throwable);
            }
        }
    }

    //endregion

}