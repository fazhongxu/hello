package com.xxl.hello.service.data.model.event;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 系统模块EventBus
 *
 * @author xxl.
 * @date 2022/05/30.
 */
public class SystemEventApi {

    //region: 构造函数

    private SystemEventApi() {

    }

    //endregion

    //region: 资源上传信息更新通知事件

    /**
     * 资源添加到队列更新通知事件
     */
    @Accessors(prefix = "m")
    public static final class OnPutResources2UploadQueueEvent {

        /**
         * 资源上传队列数据
         */
        @Getter
        private final List<ResourcesUploadQueueDBEntity> mTargetResourcesUploadQueueDBEntities;

        private OnPutResources2UploadQueueEvent(@NonNull final List<ResourcesUploadQueueDBEntity> targetResourcesUploadQueueDBEntities) {
            mTargetResourcesUploadQueueDBEntities = targetResourcesUploadQueueDBEntities;
        }

        public static final OnPutResources2UploadQueueEvent obtain(@NonNull final List<ResourcesUploadQueueDBEntity> targetResourcesUploadQueueDBEntities) {
            return new OnPutResources2UploadQueueEvent(targetResourcesUploadQueueDBEntities);
        }
    }

    //endregion

    //region: 素材提交到服务端通知事件

    /**
     * 素材提交到服务端通知
     */
    @Accessors(prefix = "m")
    public static final class OnMaterialSubmitToServiceEvent {

        /**
         * 资源上传队列数据
         */
        @Getter
        private final List<ResourcesUploadQueueDBEntity> mTargetResourcesUploadQueueDBEntities;

        private OnMaterialSubmitToServiceEvent(@NonNull final List<ResourcesUploadQueueDBEntity> targetResourcesUploadQueueDBEntities) {
            mTargetResourcesUploadQueueDBEntities = targetResourcesUploadQueueDBEntities;
        }

        public static final OnMaterialSubmitToServiceEvent obtain(@NonNull final List<ResourcesUploadQueueDBEntity> targetResourcesUploadQueueDBEntities) {
            return new OnMaterialSubmitToServiceEvent(targetResourcesUploadQueueDBEntities);
        }
    }

    //endregion

}