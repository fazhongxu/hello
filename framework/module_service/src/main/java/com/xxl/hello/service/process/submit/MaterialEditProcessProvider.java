package com.xxl.hello.service.process.submit;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.entity.UploadQueueResourcesDBEntity;
import com.xxl.hello.service.data.model.api.material.MaterialEditRequest;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ResourcesSubmitType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.process.BaseSubmitProcessProvider;
import com.xxl.hello.service.upload.api.UploadService;
import com.xxl.kit.OnRequestCallBack;

/**
 * 素材编辑
 *
 * @author xxl.
 * @date 2022/5/27.
 */
public class MaterialEditProcessProvider extends BaseSubmitProcessProvider<MaterialEditRequest> {

    //region: 构造函数

    public MaterialEditProcessProvider(@NonNull final Application application,
                                       @NonNull final DataRepositoryKit dataRepositoryKit,
                                       @NonNull final UploadService uploadService,
                                       @NonNull final UploadService qiNiuUploadService) {
        super(application, dataRepositoryKit, uploadService,qiNiuUploadService);
    }

    //endregion

    //region: 素材发布相关

    /**
     * 获取提交资源类型
     *
     * @return
     */
    @Override
    public String getSubmitType() {
        return ResourcesSubmitType.MATERIAL_EDIT;
    }

    /**
     * 处理资源提交
     *
     * @param targetUploadQueueResourcesDBEntity 资源上传队列数据
     * @param targetRequest                      请求参数
     * @param callback                           回调
     */
    @Override
    public void handleSubmit(@NonNull final UploadQueueResourcesDBEntity targetUploadQueueResourcesDBEntity,
                             @NonNull final MaterialEditRequest targetRequest,
                             @NonNull final OnRequestCallBack<Boolean> callback) {
        // TODO: 2022/5/28

    }

    //endregion

    //region: 内部辅助方法

    //endregion

}