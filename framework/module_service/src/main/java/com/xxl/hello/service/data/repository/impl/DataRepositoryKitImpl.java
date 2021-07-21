package com.xxl.hello.service.data.repository.impl;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.ConfigRepositoryApi;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * 数据服务接口集合
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public class DataRepositoryKitImpl implements DataRepositoryKit {

    //region: 构造函数

    @Inject
    public DataRepositoryKitImpl() {

    }

    //endregion

    //region: 服务接口相关

    @Inject
    Lazy<ConfigRepositoryApi> mConfigRepositoryApi;

    /**
     * 获取config数据源接口
     *
     * @return
     */
    @Override
    public ConfigRepositoryApi getConfigRepositoryApi() {
        return mConfigRepositoryApi.get();
    }

    @Inject
    Lazy<UserRepositoryApi> mUserRepositoryApi;

    /**
     * 获取用户模块数据源接口
     *
     * @return
     */
    @Override
    public UserRepositoryApi getUserRepositoryApi() {
        return mUserRepositoryApi.get();
    }

    //endregion

}
