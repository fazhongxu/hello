package com.xxl.hello.service.data.repository.impl;

import androidx.annotation.NonNull;

import com.xxl.core.rx.SchedulersProvider;
import com.xxl.hello.service.data.local.source.api.ConfigLocalDataSource;
import com.xxl.hello.service.data.model.api.config.QueryAppConfigResponse;
import com.xxl.hello.service.data.remote.api.ConfigRemoteDataSource;
import com.xxl.hello.service.data.repository.api.ConfigRepositoryApi;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

/**
 * config数据服务
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public class ConfigRepositoryImpl implements ConfigRepositoryApi {

    //region: 成员变量

    /**
     * config本地数据服务
     */
    private final ConfigLocalDataSource mConfigLocalDataSource;

    /**
     * config远程数据服务
     */
    private final ConfigRemoteDataSource mConfigRemoteDataSource;

    //endregion

    //region: 构造函数

    public ConfigRepositoryImpl(@NonNull final ConfigLocalDataSource configLocalDataSource,
                                @NonNull final ConfigRemoteDataSource configRemoteDataSource) {
        mConfigLocalDataSource = configLocalDataSource;
        mConfigRemoteDataSource = configRemoteDataSource;
    }

    //endregion

    //region: 与config相关

    /**
     * 查询config
     *
     * @param isForce 是否强制请求网络
     * @return
     */
    @Override
    public Observable<QueryAppConfigResponse> queryAppConfig(final boolean isForce) {

        if (isForce) {
            return queryRemoteAppConfig();
        }
        return mConfigLocalDataSource.queryAppConfig()
                .compose(SchedulersProvider.applyIOSchedulers())
                .flatMap((Function<QueryAppConfigResponse, ObservableSource<QueryAppConfigResponse>>) response -> {
                    if (response == null) {
                        return queryRemoteAppConfig();
                    }
                    return Observable.just(response);
                });
    }

    /**
     * 请求查询远程config
     *
     * @return
     */
    private Observable<QueryAppConfigResponse> queryRemoteAppConfig() {
        return mConfigRemoteDataSource.queryAppConfig()
                .compose(SchedulersProvider.applyIOSchedulers())
                .doOnNext(response -> {
                    //if success mConfigLocalDataSource.put¬
                });
    }

    //endregion

}