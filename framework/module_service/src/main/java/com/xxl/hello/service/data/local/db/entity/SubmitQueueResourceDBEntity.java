package com.xxl.hello.service.data.local.db.entity;

import androidx.annotation.NonNull;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;

/**
 * 提交队列数据信息
 *
 * @author xxl.
 * @date 2022/5/27.
 */
@Entity
@NameInDb("h_t_submit_queue_resources")
public class SubmitQueueResourceDBEntity extends BaseDBEntity<SubmitQueueResourceDBEntity> {

    //region: 成员变量

    /**
     * 任务的I
     * 注意：该任务ID会关联 {@link UploadQueueResourceDBEntity#getSubmitTaskId()}  }
     */
    @Index
    @Unique
    @NameInDb("task_id")
    private String taskId;

    //endregion

    //region: 构造函数

    public SubmitQueueResourceDBEntity() {

    }

    public SubmitQueueResourceDBEntity obtain() {
        return new SubmitQueueResourceDBEntity();
    }

    //endregion

    //region: 提供方法

    public String getTaskId() {
        return taskId;
    }

    /**
     * 设置任务ID
     *
     * @param taskId
     * @return
     */
    public SubmitQueueResourceDBEntity setTaskId(@NonNull final String taskId) {
        this.taskId = taskId;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}