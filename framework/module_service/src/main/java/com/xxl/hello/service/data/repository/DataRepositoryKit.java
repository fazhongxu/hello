package com.xxl.hello.service.data.repository;

import com.xxl.hello.service.data.repository.api.ConfigRepositoryApi;
import com.xxl.hello.service.data.repository.api.ResourceRepositoryApi;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;

/**
 * 数据服务接口集合
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public interface DataRepositoryKit {

    /**
     * 获取config数据源接口
     *
     * @return
     */
    ConfigRepositoryApi getConfigRepositoryApi();

    /**
     * 获取资源数据源接口
     *
     * @return
     */
    ResourceRepositoryApi getResourceRepositoryApi();

    /**
     * 获取用户模块数据源接口
     *
     * @return
     */
    UserRepositoryApi getUserRepositoryApi();
}
