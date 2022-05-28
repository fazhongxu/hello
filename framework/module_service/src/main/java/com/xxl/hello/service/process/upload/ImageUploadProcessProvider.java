package com.xxl.hello.service.process.upload;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.utils.ImageUtils;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.MediaType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.process.BaseUploadProcessProvider;
import com.xxl.hello.service.process.OnResourcesCompressCallback;
import com.xxl.hello.service.upload.api.UploadService;

import java.io.File;

/**
 * @author xxl.
 * @date 2022/5/27.
 */
public class ImageUploadProcessProvider extends BaseUploadProcessProvider {

    //region: 构造函数

    public ImageUploadProcessProvider(@NonNull final Application application,
                                      @NonNull final DataRepositoryKit dataRepositoryKit,
                                      @NonNull final UploadService uploadService) {
        super(application, dataRepositoryKit, uploadService);
    }

    public static ImageUploadProcessProvider create(@NonNull final Application application,
                                                    @NonNull final DataRepositoryKit dataRepositoryKit,
                                                    @NonNull final UploadService uploadService) {
        return new ImageUploadProcessProvider(application, dataRepositoryKit, uploadService);
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
        return MediaType.IMAGE;
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
        ImageUtils.compress(waitCompressUrl, getCompressionDir(), new ImageUtils.OnSimpleCompressListener() {

            @Override
            public void onSuccess(File file) {
                callback.onComplete(file.getAbsolutePath(), 0, 0);
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    //endregion

}