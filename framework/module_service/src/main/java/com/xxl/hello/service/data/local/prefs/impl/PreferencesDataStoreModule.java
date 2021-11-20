package com.xxl.hello.service.data.local.prefs.impl;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.prefs.PreferencesKit;
import com.xxl.hello.service.data.local.prefs.api.UserLocalPreferences;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;
import com.xxl.hello.service.qunlifier.ForUserPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/21.
 */
@Module
public class PreferencesDataStoreModule {

    @Singleton
    @Provides
    PreferencesKit providePreferencesKit(@NonNull final PreferencesKitImpl preferencesKit) {
        return preferencesKit;
    }

    @ForUserPreference
    @Singleton
    @Provides
    UserPreferences provideUserPreferences() {
        return new UserPreferencesImpl();
    }

    @Singleton
    @Provides
    UserLocalPreferences provideUserLocalPreferences() {
        return new UserLocalPreferencesIml();
    }
}