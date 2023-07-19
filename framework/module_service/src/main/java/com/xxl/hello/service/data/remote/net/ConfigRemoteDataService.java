package com.xxl.hello.service.data.remote.net;

import com.xxl.hello.service.data.model.api.config.QueryAppConfigResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * config远程数据服务
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public interface ConfigRemoteDataService {

    /**
     * 查询config
     *
     * @param timestamp
     * @return
     */
    @GET("/xxx.json")
    Observable<QueryAppConfigResponse> queryAppConfig(@Query("timestamp") long timestamp);

}