package com.xxl.hello.service.upload.impl;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.upload.api.UploadService;

/**
 * 腾讯云上传服务实现类
 *
 * @author xxl.
 * @date 2022/5/28.
 */
public class TencentUploadServiceImpl implements UploadService {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public TencentUploadServiceImpl(@NonNull final Application application,
                                    @NonNull final DataRepositoryKit dataRepositoryKit) {

    }


    //endregion

    //region: 页面生命周期

    /**
     * 开始上传
     *
     * @param key
     */
    @Override
    public void onStart(@NonNull String key) {
        Log.e("aaa", "onStart: 我是腾讯云上传" + key);
    }

    /**
     * 上传完成
     *
     * @param domain
     * @param key
     */
    @Override
    public void onComplete(@NonNull String domain, @NonNull String key) {

    }

    //endregion

    //region: 内部辅助方法

    //endregion

}