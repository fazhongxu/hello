package com.xxl.hello.service.data.model.event;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.entity.UploadQueueResourceDBEntity;

import java.util.List;

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
    public static final class OnPutResources2UploadQueueEvent {

        /**
         * 资源上传队列数据
         */
        private final List<UploadQueueResourceDBEntity> mTargetResourcesUploadQueueDBEntities;

        public List<UploadQueueResourceDBEntity> getTargetResourcesUploadQueueDBEntities() {
            return mTargetResourcesUploadQueueDBEntities;
        }

        private OnPutResources2UploadQueueEvent(@NonNull final List<UploadQueueResourceDBEntity> targetResourcesUploadQueueDBEntities) {
            mTargetResourcesUploadQueueDBEntities = targetResourcesUploadQueueDBEntities;
        }

        public static final OnPutResources2UploadQueueEvent obtain(@NonNull final List<UploadQueueResourceDBEntity> targetResourcesUploadQueueDBEntities) {
            return new OnPutResources2UploadQueueEvent(targetResourcesUploadQueueDBEntities);
        }
    }

    //endregion

    //region: 素材提交到服务端通知事件

    /**
     * 素材提交到服务端通知
     */
    public static final class OnMaterialSubmitToServiceEvent {

        /**
         * 资源上传队列数据
         */
        private final List<UploadQueueResourceDBEntity> mTargetResourcesUploadQueueDBEntities;

        public List<UploadQueueResourceDBEntity> getTargetResourcesUploadQueueDBEntities() {
            return mTargetResourcesUploadQueueDBEntities;
        }

        private OnMaterialSubmitToServiceEvent(@NonNull final List<UploadQueueResourceDBEntity> targetResourcesUploadQueueDBEntities) {
            mTargetResourcesUploadQueueDBEntities = targetResourcesUploadQueueDBEntities;
        }

        public static final OnMaterialSubmitToServiceEvent obtain(@NonNull final List<UploadQueueResourceDBEntity> targetResourcesUploadQueueDBEntities) {
            return new OnMaterialSubmitToServiceEvent(targetResourcesUploadQueueDBEntities);
        }
    }

    //endregion

    //region: 录音通知事件

    /**
     * 开始录音通知事件
     */
    public static final class OnStartRecordingEvent {
        private OnStartRecordingEvent() {

        }

        public static final OnStartRecordingEvent obtain() {
            return new OnStartRecordingEvent();
        }
    }

    /**
     * 停止录音通知事件
     */
    public static final class OnStopRecordingEvent {
        private OnStopRecordingEvent() {

        }

        public static final OnStopRecordingEvent obtain() {
            return new OnStopRecordingEvent();
        }
    }

    //endregion

}