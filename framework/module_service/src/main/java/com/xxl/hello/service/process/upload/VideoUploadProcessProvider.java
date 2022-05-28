package com.xxl.hello.service.process.upload;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.utils.VideoUtils;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.MediaType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.process.BaseUploadProcessProvider;
import com.xxl.hello.service.process.OnResourcesCompressCallback;
import com.xxl.hello.service.upload.api.UploadService;

/**
 * @author xxl.
 * @date 2022/5/27.
 */
public class VideoUploadProcessProvider extends BaseUploadProcessProvider {

    //region: 构造函数

    public VideoUploadProcessProvider(@NonNull final Application application,
                                      @NonNull final DataRepositoryKit dataRepositoryKit,
                                      @NonNull final UploadService uploadService) {
        super(application, dataRepositoryKit, uploadService);
    }

    public static VideoUploadProcessProvider create(@NonNull final Application application,
                                                    @NonNull final DataRepositoryKit dataRepositoryKit,
                                                    @NonNull final UploadService uploadService) {
        return new VideoUploadProcessProvider(application, dataRepositoryKit, uploadService);
    }

    //endregion

    //region: 生命周期相关

    /**
     * 获取媒体资源类型
     *
     * @return
     */
    @Override
    public String getMediaType() {
        return MediaType.VIDEO;
    }

    /**
     * 处理资源压缩
     *
     * @param waitCompressUrl 待压缩的资源路径
     * @param callback        回调
     */
    @Override
    public void handleCompress(@NonNull final String waitCompressUrl,
                               @NonNull final OnResourcesCompressCallback callback) {
        VideoUtils.compress(waitCompressUrl, getCompressionDir(), new VideoUtils.OnVideoProgressListener() {

            @Override
            public void onComplete(String videoPath,
                                   long videoWidth,
                                   long videoHeight) {
                callback.onComplete(videoPath, videoWidth, videoHeight);
            }

            @Override
            public void onComplete(String videoPath) {
                callback.onComplete(videoPath, 0, 0);
            }

            @Override
            public void onProgress(float progress) {
                callback.onProgress(progress);
            }
        });
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}