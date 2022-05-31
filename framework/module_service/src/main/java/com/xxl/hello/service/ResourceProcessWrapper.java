package com.xxl.hello.service;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xxl.core.exception.ResponseCode;
import com.xxl.core.exception.ResponseException;
import com.xxl.core.utils.StringUtils;
import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.MediaType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.process.BaseUploadProcessProvider;
import com.xxl.hello.service.process.OnResourcesUploadCallback;
import com.xxl.hello.service.process.upload.ImageUploadProcessProvider;
import com.xxl.hello.service.process.upload.VideoUploadProcessProvider;
import com.xxl.hello.service.upload.api.UploadService;

import java.util.LinkedHashMap;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 资源处理包装类
 *
 * @author xxl.
 * @date 2022/5/28.
 */
@Accessors(prefix = "m")
public class ResourceProcessWrapper {

    //region: 成员变量

    /**
     * 上下文
     */
    @Getter
    private Application mApplication;

    /**
     * 数据服务接口集合
     */
    @Getter
    private DataRepositoryKit mDataRepositoryKit;

    /**
     * 上传服务
     */
    @Getter
    private final UploadService mUploadService;

    /**
     * 资源上传处理模板集合
     */
    private LinkedHashMap<String, BaseUploadProcessProvider> mUploadProcessProviderMap = new LinkedHashMap<>();

    //endregion

    //region: 构造函数

    public ResourceProcessWrapper(@NonNull final Application application,
                                  @NonNull final DataRepositoryKit dataRepositoryKit,
                                  @NonNull final UploadService uploadService,
                                  @NonNull final UploadService tencentUploadService) {
        mApplication = application;
        mDataRepositoryKit = dataRepositoryKit;
        mUploadService = uploadService;
        registerUploadProcessProvider();
    }

    //endregion

    //region: 页面生命周期

    /**
     * clear
     */
    void onCleared() {
        try {
            unregisterUploadProcessProvider();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region: 资源上传相关

    /**
     * 注册资源上传处理模板
     */
    private void registerUploadProcessProvider() {
        registerUploadProcessProvider(ImageUploadProcessProvider.create(getApplication(), getDataRepositoryKit(), getUploadService()));
        registerUploadProcessProvider(VideoUploadProcessProvider.create(getApplication(), getDataRepositoryKit(), getUploadService()));
    }

    /**
     * 取消注册资源上传处理模板
     */
    private void unregisterUploadProcessProvider() {
        if (!mUploadProcessProviderMap.isEmpty()) {
            mUploadProcessProviderMap.clear();
        }
    }

    /**
     * 注册资源上传处理模板
     *
     * @param targetUploadProcessProvider
     */
    private void registerUploadProcessProvider(@NonNull final BaseUploadProcessProvider targetUploadProcessProvider) {
        final String mediaType = targetUploadProcessProvider.getMediaType();
        if (TextUtils.isEmpty(mediaType)) {
            throw new RuntimeException("未找到资源处理模板");
        }
        mUploadProcessProviderMap.put(mediaType, targetUploadProcessProvider);
    }

    /**
     * 获取资源上传处理
     *
     * @param mediaType 媒体类型
     * @return
     */
    private BaseUploadProcessProvider getUploadProcessProvider(@MediaType final String mediaType) {
        return mUploadProcessProviderMap.get(mediaType);
    }

    /**
     * 资源上传
     *
     * @param targetResourcesUploadQueueDBEntity 资源信息
     * @param callBack                           回调
     */
    public void onUpload(@NonNull final ResourcesUploadQueueDBEntity targetResourcesUploadQueueDBEntity,
                         @NonNull final OnResourcesUploadCallback callBack) {
        onUpload(targetResourcesUploadQueueDBEntity, true, callBack);
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
        final BaseUploadProcessProvider uploadProcessProvider = getUploadProcessProvider(targetResourcesUploadQueueDBEntity.getMediaType());
        if (uploadProcessProvider != null) {
            uploadProcessProvider.onUpload(targetResourcesUploadQueueDBEntity, isForever, callBack);
        } else {
            callBack.onFailure(ResponseException.create(ResponseCode.RESPONSE_CODE_UN_KNOW, StringUtils.getString(R.string.resources_file_upload_failure_can_not_found_res)));
        }
    }

    //endregion

}