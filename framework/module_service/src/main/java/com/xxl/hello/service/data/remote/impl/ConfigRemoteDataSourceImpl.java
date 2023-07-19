package com.xxl.hello.service.data.remote.impl;

import androidx.annotation.NonNull;

import com.xxl.core.data.remote.ApiHeader;
import com.xxl.core.data.remote.BaseRemoteDataStoreSource;
import com.xxl.hello.service.data.model.api.config.QueryAppConfigResponse;
import com.xxl.hello.service.data.remote.api.ConfigRemoteDataSource;
import com.xxl.hello.service.data.remote.net.ConfigRemoteDataService;
import com.xxl.kit.TimeUtils;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;

/**
 * config远程数据服务
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public class ConfigRemoteDataSourceImpl extends BaseRemoteDataStoreSource implements ConfigRemoteDataSource {

    //region: 成员变量

    /**
     * config远程数据服务
     */
    private final ConfigRemoteDataService mConfigRemoteDataService;

    //endregion

    //region: 构造函数

    public ConfigRemoteDataSourceImpl(@NonNull ApiHeader apiHeader,
                                      @NonNull Retrofit retrofit) {
        super(apiHeader, retrofit);
        mConfigRemoteDataService = getRetrofit().create(ConfigRemoteDataService.class);
    }

    //endregion

    //region: 与config相关

    /**
     * 查询config
     *
     * @return
     */
    @Override
    public Observable<QueryAppConfigResponse> queryAppConfig(){
        return mConfigRemoteDataService.queryAppConfig(TimeUtils.currentServiceTimeMillis());
    }

    //endregion
}