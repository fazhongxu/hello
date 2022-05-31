package com.xxl.hello.widget.ui.model.resource;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.ui.BaseViewModel;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 资源处理队列ViewModel
 *
 * @author xxl.
 * @date 2022/5/28.
 */
@Accessors(prefix = "m")
public abstract class BaseResourceQueueViewModel<N> extends BaseViewModel<N> {

    //region: 成员变量

    /**
     * 数据服务接口集合
     */
    @Getter
    private DataRepositoryKit mDataRepositoryKit;

    /**
     * 任务ID
     */
    @Getter
    private String mTaskId = UUID.randomUUID().toString();

    //endregion

    //region: 构造函数

    public BaseResourceQueueViewModel(@NonNull final Application application,
                                      @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
    }

    //endregion

    //region: 资源上传相关

    // TODO: 2022/5/28 把资源添加到数据库，然后运行队列服务，开始压缩，上传，最后提交服务端


    //endregion


}