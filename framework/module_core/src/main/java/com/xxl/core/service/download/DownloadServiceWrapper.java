package com.xxl.core.service.download;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.LinkedHashMap;

/**
 * 下载服务包装类
 *
 * @author xxl.
 * @date 2022/9/22.
 */
public class DownloadServiceWrapper implements DownloadListener {

    //region: 成员变量

    /**
     * 上下文
     */
    private Application mApplication;

    /**
     * 下载服务
     */
    private final DownloadService mDownloadService;

    /**
     * 下载处理集合
     */
    private final LinkedHashMap<String, DownloadHandle> mDownloadHandleMap = new LinkedHashMap<>();

    //endregion

    //region: 构造函数

    private DownloadServiceWrapper(@NonNull Application application,
                                   @NonNull final DownloadService downloadService) {
        mApplication = application;
        mDownloadService = downloadService;
        mDownloadService.register(application, this);
    }

    public static DownloadServiceWrapper create(@NonNull Application application,
                                                @NonNull final DownloadService downloadService) {
        return new DownloadServiceWrapper(application, downloadService);
    }

    //endregion

    //region: DownloadListener

    /**
     * 下载开始
     *
     * @param taskEntity
     */
    @Override
    public void onTaskStart(@NonNull DownloadTaskEntity taskEntity) {
        final DownloadHandle downloadHandle = getDownloadHandle(taskEntity.getKey());
        if (downloadHandle != null) {
            final DownloadListener downloadListener = downloadHandle.getDownloadListener();
            if (downloadListener != null) {
                downloadListener.onTaskStart(taskEntity);
            }
        }
    }

    /**
     * 下载完成
     *
     * @param taskEntity
     */
    @Override
    public void onTaskComplete(@NonNull DownloadTaskEntity taskEntity) {
        final DownloadHandle downloadHandle = getDownloadHandle(taskEntity.getKey());
        if (downloadHandle != null) {
            final DownloadListener downloadListener = downloadHandle.getDownloadListener();
            if (downloadListener != null) {
                downloadListener.onTaskComplete(taskEntity);
            }
        }
        removeDownloadHandle(taskEntity.getKey());
    }

    /**
     * 下载失败
     *
     * @param taskEntity
     * @param throwable
     */
    @Override
    public void onTaskFail(@NonNull DownloadTaskEntity taskEntity,
                           @Nullable Throwable throwable) {
        final DownloadHandle downloadHandle = getDownloadHandle(taskEntity.getKey());
        if (downloadHandle != null) {
            final DownloadListener downloadListener = downloadHandle.getDownloadListener();
            if (downloadListener != null) {
                downloadListener.onTaskFail(taskEntity,throwable);
            }
        }
        removeDownloadHandle(taskEntity.getKey());
    }

    //endregion

    //region: 提供方法

    /**
     * 创建下载任务
     *
     * @param activity         上下文
     * @param downloadOption   下载配置
     * @param downloadListener 下载监听
     */
    public void createDownloadTask(@NonNull final FragmentActivity activity,
                                   @NonNull final DownloadOptions downloadOption,
                                   @NonNull final DownloadListener downloadListener) {
        putDownloadHandle(downloadOption.getTargetDownloadTag(), DownloadHandle.create(downloadOption, downloadListener));
        mDownloadService.createDownloadTask(activity, downloadOption);
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 添加下载任务处理
     *
     * @param taskKey
     * @param downloadHandle
     */
    protected void putDownloadHandle(@NonNull final String taskKey,
                                     @NonNull final DownloadHandle downloadHandle) {
        mDownloadHandleMap.put(taskKey, downloadHandle);
    }

    /**
     * 获取下载任务处理
     *
     * @param taskKey
     * @return
     */
    public DownloadHandle getDownloadHandle(@NonNull final String taskKey) {
        return mDownloadHandleMap.get(taskKey);
    }

    /**
     * 移除下载任务处理
     *
     * @param taskKey
     */
    protected void removeDownloadHandle(@NonNull final String taskKey) {
        mDownloadHandleMap.remove(taskKey);
    }

    //endregion

}