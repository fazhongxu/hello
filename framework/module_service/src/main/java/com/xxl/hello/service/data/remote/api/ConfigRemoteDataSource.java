package com.xxl.hello.service.data.remote.api;

import com.xxl.hello.service.data.model.api.config.QueryAppConfigResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * config远程数据服务
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public interface ConfigRemoteDataSource {

    //region: 与config相关

    /**
     * 查询config
     *
     * @return
     */
    Observable<QueryAppConfigResponse> queryAppConfig();

    //endregion
}