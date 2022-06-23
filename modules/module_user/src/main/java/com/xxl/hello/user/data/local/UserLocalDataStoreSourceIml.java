package com.xxl.hello.user.data.local;

import androidx.annotation.NonNull;

import com.xxl.kit.GsonUtils;
import com.xxl.hello.service.data.local.db.DBServiceKit;
import com.xxl.hello.service.data.local.db.api.CacheDBDataService;
import com.xxl.hello.service.data.local.prefs.PreferencesKit;
import com.xxl.hello.service.data.local.prefs.api.UserLocalPreferences;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;

/**
 * 用户模块本地数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public class UserLocalDataStoreSourceIml implements UserLocalDataStoreSource {

    //region: 成员变量

    /**
     * 本地数据存储集合
     */
    private final PreferencesKit mPreferencesKit;

    /**
     * 数据库服务集合
     */
    private final DBServiceKit mDBServiceKit;

    //endregion

    //region: 构造函数

    public UserLocalDataStoreSourceIml(@NonNull final PreferencesKit preferencesKit,
                                       @NonNull final DBServiceKit dbServiceKit) {
        mPreferencesKit = preferencesKit;
        mDBServiceKit = dbServiceKit;
    }

    //endregion

    //region: 用户"隐私政策"相关

    /**
     * 设置用户同意"隐私协议"的状态
     *
     * @return
     */
    @Override
    public boolean setAgreePrivacyPolicyStatus(final boolean isAgree) {
        final UserLocalPreferences userLocalPreferences = mPreferencesKit.getUserLocalPreferences();
        return userLocalPreferences.setAgreePrivacyPolicyStatus(isAgree);
    }

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    @Override
    public boolean isAgreePrivacyPolicy() {
        final UserLocalPreferences userLocalPreferences = mPreferencesKit.getUserLocalPreferences();
        return userLocalPreferences.isAgreePrivacyPolicy();
    }

    //endregion

    //region: 与登录用户信息相关
    /**
     * 设置当前登录的用户信息
     *
     * @param loginUserEntity
     * @return
     */
    @Override
    public boolean setCurrentLoginUserEntity(@NonNull final LoginUserEntity loginUserEntity) {
        final UserPreferences<LoginUserEntity> userPreferences = mPreferencesKit.getUserPreferences();

        // FIXME: 2021/11/21 数据库测试代码，
        try {
            final CacheDBDataService cacheDBDataService = mDBServiceKit.getCacheDBDataService();
            cacheDBDataService.putCacheData("123", GsonUtils.toJson(loginUserEntity));
        }catch (Exception e) {
            e.printStackTrace();
        }


        return userPreferences.setCurrentLoginUserEntity(loginUserEntity);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @Override
    public LoginUserEntity getCurrentLoginUserEntity() {
        final UserPreferences<LoginUserEntity> userPreferences = mPreferencesKit.getUserPreferences();

        // FIXME: 2021/11/21 数据库测试代码，
        try {
            final CacheDBDataService cacheDBDataService = mDBServiceKit.getCacheDBDataService();
            LoginUserEntity loginUserEntity = cacheDBDataService.getCacheData("123", LoginUserEntity.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return userPreferences.getCurrentLoginUserEntity();
    }

    /**
     * 获取用户token
     *
     * @return
     */
    @Override
    public String getUserToken() {
        final UserPreferences userPreferences = mPreferencesKit.getUserPreferences();
        return userPreferences.getToken();
    }

    //endregion
}