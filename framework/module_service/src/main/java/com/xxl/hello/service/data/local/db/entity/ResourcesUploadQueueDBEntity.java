package com.xxl.hello.service.data.local.db.entity;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.enums.SystemEnumsApi.MediaType;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ResoucesUploadChannel;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;
import lombok.Getter;

/**
 * 资源上传队列数据信息
 *
 * @author xxl.
 * @date 2022/5/27.
 */
@Getter
@Entity
@NameInDb("h_t_resources_upload_queue")
public class ResourcesUploadQueueDBEntity extends BaseDBEntity<ResourcesUploadQueueDBEntity> {

    //region: 成员变量

    /**
     * 资源ID，唯一标致，通常为媒体ID
     */
    @Unique
    @Index
    @NameInDb("resources_upload_id")
    private String resourcesUploadId;

    /**
     * 任务的ID
     */
    @NameInDb("submit_task_id")
    private String submitTaskId;

    /**
     * 资源已经上传的网络路径
     */
    @NameInDb("upload_url")
    private String uploadUrl;

    /**
     * 资源本地待上传的文件路径
     */
    @NameInDb("wait_upload_path")
    private String waitUploadPath;
    /**
     * 资源类型
     */
    @MediaType
    @NameInDb("media_type")
    private String mediaType;

    /**
     * 上传渠道
     */
    @ResoucesUploadChannel
    @NameInDb("uploadChannel")
    private int uploadChannel;

    //endregion

    //region: 构造函数

    public ResourcesUploadQueueDBEntity() {

    }

    public ResourcesUploadQueueDBEntity obtain() {
        return new ResourcesUploadQueueDBEntity();
    }

    //endregion

    //region: 提供方法

    /**
     * 设置任务ID
     *
     * @param submitTaskId
     * @return
     */
    public ResourcesUploadQueueDBEntity setSubmitTaskId(@NonNull final String submitTaskId) {
        this.submitTaskId = submitTaskId;
        return this;
    }

    /**
     * 设置资源类型
     *
     * @param mediaType
     * @return
     */
    public ResourcesUploadQueueDBEntity setMediaType(@MediaType final String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    /**
     * 设置上传的资源路径
     *
     * @param uploadUrl
     * @return
     */
    public ResourcesUploadQueueDBEntity setUploadUrl(@NonNull final String uploadUrl) {
        this.uploadUrl = uploadUrl;
        return this;
    }

    /**
     * 设置待上传的资源路径
     *
     * @param waitUploadPath
     * @return
     */
    public ResourcesUploadQueueDBEntity setWaitUploadUrl(@NonNull final String waitUploadPath) {
        this.waitUploadPath = waitUploadPath;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}