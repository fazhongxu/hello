package com.xxl.hello.service.data.local.db.entity;

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
    @NameInDb("resource_type")
    private String resourceType;

    /**
     * 上传渠道
     */
    @ResoucesUploadChannel
    @NameInDb("uploadChannel")
    private int uploadChannel;

    //endregion

    //region: 构造函数

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}