package com.xxl.hello.service.data.repository.api;

import com.xxl.hello.service.data.model.api.config.QueryAppConfigResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * config 对外开放的API接口
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public interface ConfigRepositoryApi {

    //region: 与config相关

    /**
     * 查询config
     *
     * @param isForce 是否强制请求网络
     * @return
     */
    Observable<QueryAppConfigResponse> queryAppConfig(final boolean isForce);

    //endregion
}