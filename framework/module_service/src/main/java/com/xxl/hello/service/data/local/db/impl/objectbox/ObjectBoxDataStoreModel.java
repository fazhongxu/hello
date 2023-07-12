package com.xxl.hello.service.data.local.db.impl.objectbox;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.DBServiceKit;
import com.xxl.hello.service.data.local.db.api.CacheDBDataService;
import com.xxl.hello.service.data.local.db.api.DBClientKit;
import com.xxl.hello.service.data.local.db.api.OrderDBDataService;
import com.xxl.hello.service.data.local.db.entity.CacheDBEntity;
import com.xxl.hello.service.data.local.db.entity.OrderDBEntity;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.hello.service.qunlifier.ForUserPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ObjectBox 数据库服务依赖注入
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:56 PM
 **/
@Module
public class ObjectBoxDataStoreModel {

    /**
     * 构建数据库服务
     *
     * @param application
     * @return
     */
    @Singleton
    @Provides
    public ObjectBoxDBClientKit provideObjectBoxDBClientKit(@ForApplication final Application application,
                                                            @ForUserPreference final UserPreferences userPreferences) {
        return new ObjectBoxDBClientKit(application,userPreferences);
    }

    /**
     * 构建数据库服务集合
     *
     * @param objectBoxDBClientKit
     * @return
     */
    @Singleton
    @Provides
    public DBClientKit provideDBClientKit(@NonNull final ObjectBoxDBClientKit objectBoxDBClientKit) {
        return objectBoxDBClientKit;
    }

    /**
     * 构建数据库服务集合
     *
     * @param objectBoxServiceKit
     * @return
     */
    @Singleton
    @Provides
    public DBServiceKit provideDBServiceKit(@NonNull final ObjectBoxServiceKit objectBoxServiceKit) {
        return objectBoxServiceKit;
    }

    /**
     * 构建对 {@link CacheDBEntity} 的数据操作服务
     *
     * @return 返回以 ObjectBox为基础的数据库操作的服务
     */
    @Provides
    @Singleton
    CacheDBDataService provideCacheDBDataService(@NonNull final ObjectBoxDBClientKit objectBoxClientKit) {
        return new CacheObjectBoxDataSource(objectBoxClientKit);
    }

    /**
     * 构建对 {@link OrderDBEntity} 的数据操作服务
     *
     * @return 返回以 ObjectBox为基础的数据库操作的服务
     */
    @Provides
    @Singleton
    OrderDBDataService provideOrderDBDataService(@NonNull final ObjectBoxDBClientKit objectBoxClientKit) {
        return new OrderObjectBoxDataSource(objectBoxClientKit);
    }
}
