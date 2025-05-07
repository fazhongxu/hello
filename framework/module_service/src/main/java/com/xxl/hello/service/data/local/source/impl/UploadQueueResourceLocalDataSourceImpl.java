package com.xxl.hello.service.data.local.source.impl;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.DBServiceKit;
import com.xxl.hello.service.data.local.db.entity.UploadQueueResourceDBEntity;
import com.xxl.hello.service.data.local.prefs.PreferencesKit;
import com.xxl.hello.service.data.local.source.api.UploadQueueResourceLocalDataSource;

import java.util.List;

/**
 * 上传队列资源地数据服务
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public class UploadQueueResourceLocalDataSourceImpl implements UploadQueueResourceLocalDataSource {

    //region: 成员变量

    private DBServiceKit mDBServiceKit;

    //endregion

    //region: 构造函数

    public UploadQueueResourceLocalDataSourceImpl(@NonNull final PreferencesKit preferencesKit,
                                                  @NonNull final DBServiceKit dbServiceKit) {
        mDBServiceKit = dbServiceKit;
    }

    //endregion

    //region: 与资源信息相关

    /**
     * 获取资源数据
     *
     * @param count
     * @return
     */
    public List<UploadQueueResourceDBEntity> getResourceDBEntities(int count) {
        return mDBServiceKit.getUploadQueueResourceDBDataService().getResourceDBEntities(count);
    }

    //endregion
}