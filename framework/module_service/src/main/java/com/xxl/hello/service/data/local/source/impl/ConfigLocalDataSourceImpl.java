package com.xxl.hello.service.data.local.source.impl;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.DBServiceKit;
import com.xxl.hello.service.data.local.prefs.PreferencesKit;
import com.xxl.hello.service.data.local.source.api.ConfigLocalDataSource;
import com.xxl.hello.service.data.model.api.config.QueryAppConfigResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * config本地数据服务
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public class ConfigLocalDataSourceImpl implements ConfigLocalDataSource {

    //region: 构造函数

    public ConfigLocalDataSourceImpl(@NonNull final PreferencesKit preferencesKit,
                                     @NonNull final DBServiceKit dbServiceKit) {

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
        // TODO: 2023/7/19
        return null;
    }

    //endregion
}