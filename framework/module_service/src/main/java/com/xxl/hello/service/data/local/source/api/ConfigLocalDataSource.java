package com.xxl.hello.service.data.local.source.api;

import com.xxl.hello.service.data.model.api.config.QueryAppConfigResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * config本地数据服务
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public interface ConfigLocalDataSource {

    //region: 与config相关

    /**
     * 查询config
     *
     * @return
     */
    Observable<QueryAppConfigResponse> queryAppConfig();

    //endregion
}