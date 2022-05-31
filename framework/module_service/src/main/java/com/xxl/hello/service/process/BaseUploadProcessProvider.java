package com.xxl.hello.service.process;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.exception.ResponseCode;
import com.xxl.core.utils.FileUtils;
import com.xxl.core.utils.StringUtils;
import com.xxl.hello.common.CacheDirConfig;
import com.xxl.hello.service.R;
import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.MediaType;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ResoucesUploadChannel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.upload.api.UploadService;

/**
 * 资源上传处理
 *
 * @author xxl.
 * @date 2022/5/28.
 */
public abstract class BaseUploadProcessProvider extends BaseProcessProvider {

    //region: 构造函数

    public BaseUploadProcessProvider(@NonNull final Application application,
                                     @NonNull final DataRepositoryKit dataRepositoryKit,
                                     @NonNull final UploadService uploadService) {
        super(application, dataRepositoryKit, uploadService);
    }

    //endregion

    //region: 抽象方法相关

    /**
     * 获取媒体资源类型
     *
     * @return
     */
    @MediaType
    public abstract String getMediaType();

    /**
     * 处理资源压缩
     *
     * @param waitCompressUrl
     * @param callback
     */
    public abstract void handleCompress(@NonNull final String waitCompressUrl,
                                        @NonNull final OnResourcesCompressCallback callback);

    //endregion

    //region: 资源上传相关

    /**
     * 获取压缩文件夹
     *
     * @return
     */
    protected String getCompressionDir() {
        return CacheDirConfig.COMPRESSION_FILE_DIR;
    }

    /**
     * 上传的资源是否永久有效
     *
     * @return
     */
    protected boolean isForever() {
        return true;
    }

    /**
     * 资源上传
     *
     * @param targetResourcesUploadQueueDBEntity
     * @param callBack
     */
    public void onUpload(@NonNull final ResourcesUploadQueueDBEntity targetResourcesUploadQueueDBEntity,
                         @NonNull final OnResourcesUploadCallback callBack) {
        onUpload(targetResourcesUploadQueueDBEntity, isForever(), callBack);
    }

    /**
     * 资源上传
     *
     * @param targetResourcesUploadQueueDBEntity 资源信息
     * @param isForever                          资源是否永久有效
     * @param callBack                           回调
     */
    public void onUpload(@NonNull final ResourcesUploadQueueDBEntity targetResourcesUploadQueueDBEntity,
                         final boolean isForever,
                         @NonNull final OnResourcesUploadCallback callBack) {
        onUpload(targetResourcesUploadQueueDBEntity.getWaitUploadPath(), isForever, targetResourcesUploadQueueDBEntity.getUploadChannel(), callBack);
    }

    /**
     * 资源上传
     *
     * @param waitUploadPath 待上传的资源路径
     * @param isForever      资源是否永久有效
     * @param uploadChannel  上传渠道
     * @param callback       回调
     */
    public void onUpload(@NonNull final String waitUploadPath,
                         final boolean isForever,
                         @ResoucesUploadChannel final int uploadChannel,
                         @NonNull final OnResourcesUploadCallback callback) {
        if (FileUtils.isFile(waitUploadPath)) {
            handleUpload(waitUploadPath, isForever, uploadChannel, callback);
        } else if (StringUtils.isHttp(waitUploadPath)) {
            callback.onComplete(waitUploadPath);
        } else {
            callback.onFailure(createResponseException(ResponseCode.RESPONSE_CODE_UN_KNOW, StringUtils.getString(R.string.resources_file_upload_failure)));
        }
    }

    /**
     * 资源上传
     *
     * @param waitUploadPath 待上传的资源路径
     * @param isForever      资源是否永久有效
     * @param uploadChannel  上传渠道
     * @param callback       回调
     */
    protected void handleUpload(@NonNull final String waitUploadPath,
                                final boolean isForever,
                                @ResoucesUploadChannel final int uploadChannel,
                                @NonNull final OnResourcesUploadCallback callback) {
        final OnResourcesCompressCallback onResourcesCompressCallback = new OnResourcesCompressCallback() {

            @Override
            public void onComplete(String filePath,
                                   long width,
                                   long height) {
                upload(waitUploadPath, isForever, uploadChannel, callback);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        };
        handleCompress(waitUploadPath, onResourcesCompressCallback);
    }

    /**
     * 资源上传
     *
     * @param waitUploadPath 待上传的资源路径
     * @param isForever      资源是否永久有效
     * @param uploadChannel  上传渠道
     * @param callback       回调
     */
    protected void upload(@NonNull final String waitUploadPath,
                          final boolean isForever,
                          @ResoucesUploadChannel final int uploadChannel,
                          @NonNull final OnResourcesUploadCallback callback) {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onComplete("https://"+waitUploadPath);
            }
        }, 2000);

        // TODO: 2022/5/28 upload  模拟资源上传
    }

    //endregion

}