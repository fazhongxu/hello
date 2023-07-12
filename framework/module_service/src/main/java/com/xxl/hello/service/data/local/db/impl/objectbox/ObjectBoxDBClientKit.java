package com.xxl.hello.service.data.local.db.impl.objectbox;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.service.BuildConfig;
import com.xxl.hello.service.data.local.db.api.DBClientKit;
import com.xxl.hello.service.data.local.db.entity.MyObjectBox;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;
import com.xxl.kit.LogUtils;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * 数据库服务
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:48 PM
 **/
public class ObjectBoxDBClientKit implements DBClientKit {

    //region: 静态常量

    /**
     * 数据库前缀
     */
    private static final String DB_NAME_PREFIX = "hello_db_";

    //endregion

    //region: 成员变量

    /**
     * 用户信息存储
     */
    private final UserPreferences mUserPreferences;

    /**
     * ObjectBox 数据库
     */
    private BoxStore mOoxStore;

    /**
     * 应用上下文
     */
    private final Application mApplication;

    /**
     * 当前打开的数据库名称
     */
    private String mDatabaseName;

    //endregion

    //region: 构造函数

    public ObjectBoxDBClientKit(@NonNull final Application application,
                                @NonNull final UserPreferences userPreferences) {
        mApplication = application;
        mUserPreferences = userPreferences;
    }

    //endregion

    //region: 提供方法

    /**
     * 切换数据库
     *
     * @param targetUserId
     */
    @Override
    public boolean switchDatabase(@NonNull String targetUserId) {
        synchronized (this) {
            if (TextUtils.isEmpty(targetUserId)) {
                return false;
            }

            final String dataBaseName = buildDataBaseName(targetUserId);
            if (TextUtils.equals(dataBaseName,mDatabaseName) && mOoxStore != null && !mOoxStore.isClosed()) {
                return true;
            }
            mDatabaseName = dataBaseName;

            closeDatabase();
            mOoxStore = MyObjectBox.builder()
                    .androidContext(mApplication)
                    .name(dataBaseName)
                    .build();

            if (BuildConfig.DEBUG) {
                final boolean start = new AndroidObjectBrowser(mOoxStore)
                        .start(mApplication);
                LogUtils.d("数据库网页浏览服务开启状态" + start);
            }
            return true;
        }
    }

    /**
     * 关闭数据库
     */
    @Override
    public void closeDatabase() {
        synchronized (this) {
            if (mOoxStore == null || mOoxStore.isClosed()) {
                return;
            }
            mOoxStore.close();
            mOoxStore = null;
        }
    }

    /**
     * 判断数据库是否已经关闭
     *
     * @return
     */
    @Override
    public boolean isClosed() {
        synchronized (this) {
            return mOoxStore != null && mOoxStore.isClosed();
        }
    }

    /**
     * 获取ObjectBox数据库
     *
     * @return
     */
    public BoxStore getOoxStore() {
        synchronized (this) {
            if (mOoxStore == null || mOoxStore.isClosed()) {
                switchDatabase(mUserPreferences.getUserId());
            }
            return mOoxStore;
        }
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 构建数据库名称
     *
     * @param targetUserId
     * @return
     */
    private String buildDataBaseName(@NonNull final String targetUserId) {
        if (NetworkConfig.Companion.isNetworkDebug()) {
            return String.format("%s%s%s", DB_NAME_PREFIX, targetUserId, "_DEBUG");
        }
        return String.format("%s%s", DB_NAME_PREFIX, targetUserId);
    }

    //endregion
}
