package com.xxl.hello.user.data.local;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.DBServiceKit;
import com.xxl.hello.service.data.local.db.api.DBClientKit;
import com.xxl.hello.service.data.local.prefs.PreferencesKit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 用户模块本地数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public class UserLocalDataStoreModule {

    @Singleton
    @Provides
    UserLocalDataStoreSource provideUserLocalDataStoreModule(@NonNull final PreferencesKit preferencesKit,
                                                             @NonNull final DBServiceKit dbServiceKit) {
        return new UserLocalDataStoreSourceIml(preferencesKit,dbServiceKit);
    }
}