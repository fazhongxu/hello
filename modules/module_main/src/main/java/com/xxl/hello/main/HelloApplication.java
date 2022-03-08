package com.xxl.hello.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.xxl.core.BaseApplication;
import com.xxl.core.data.router.AppRouterApi;
import com.xxl.core.image.selector.MediaSelector;
import com.xxl.core.image.selector.MediaSelectorApp;
import com.xxl.core.image.selector.PictureSelectorEngineImpl;
import com.xxl.core.listener.IApplication;
import com.xxl.core.utils.CacheUtils;
import com.xxl.core.utils.LogUtils;
import com.xxl.core.utils.ObjectUtils;
import com.xxl.core.utils.StringUtils;
import com.xxl.core.widget.swipebacklayout.SwipeBackActivityManager;
import com.xxl.hello.common.NetworkConfig;
import com.xxl.hello.common.ShortcutConfig;
import com.xxl.hello.main.di.component.DaggerAppComponent;
import com.xxl.hello.user.ui.setting.UserSettingActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2020/8/20.
 */
public class HelloApplication extends BaseApplication implements IApplication, MediaSelectorApp {

    //region: 成员变量

    /**
     * 网络环境是否是debug模式，上线必须改为false
     */
    private static final boolean sIsDebug = true;

    /**
     * Application 包装类
     */
    @Inject
    HelloApplicationWrapper mApplicationWrapper;

    //endregion

    //region: 页面生命周期

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    @Override
    public boolean isAgreePrivacyPolicy() {
        return mApplicationWrapper.isAgreePrivacyPolicy();
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    @Override
    public String getCurrentUserId() {
        return mApplicationWrapper.getCurrentUserId();
    }

    /**
     * 是否是debug模式
     *
     * @return
     */
    @Override
    public boolean isDebug() {
        return sIsDebug;
    }

    /**
     * 网络环境是否是debug模式
     *
     * @return
     */
    @Override
    public boolean isNetworkDebug() {
        return NetworkConfig.isNetworkDebug();
    }

    //endregion

    //region: 组件初始化操作

    /**
     * 初始化操作
     */
    private void init() {
        mApplicationWrapper.init(this);
    }

    /**
     * 初始化组件
     */
    @Override
    public void initPlugins() {
        super.initPlugins();
        CacheUtils.init(this, NetworkConfig.isDebug());
        LogUtils.init(NetworkConfig.isDebug());
        MediaSelector.init(this);
        SwipeBackActivityManager.init(this);
//        registerShortcuts(this);
        unRegisterShortcuts(this);
    }

    /**
     * 在用户统一"隐私政策"后初始化插件
     */
    @Override
    public void initPluginsAfterAgreePrivacyPolicy() {
        LogUtils.d("initPluginAfterAgreePrivacyPolicy");
    }

    //endregion

    //region: IApplication

    /**
     * 是否登录
     *
     * @return
     */
    @Override
    public boolean isLogin() {
        return !TextUtils.isEmpty(getCurrentUserId());
    }

    /**
     * 跳转到登录
     */
    @Override
    public void navigationToLogin() {
        AppRouterApi.navigationToLogin();
    }

    //endregion

    //region: MediaSelectorApp

    /**
     * Application
     *
     * @return
     */
    @Override
    public Context getAppContext() {
        return this;
    }

    /**
     * PictureSelectorEngine
     *
     * @return
     */
    @Override
    public PictureSelectorEngine getPictureSelectorEngine() {
        return new PictureSelectorEngineImpl();
    }

    //endregion

    //region: shortcut

    /**
     * 注册shortcuts
     *
     * @param context
     */
    private static void registerShortcuts(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            if (shortcutManager == null) {
                return;
            }
            final List<ShortcutInfo> targetShortcuts = new ArrayList<>();

            final Intent crmIntent = new Intent(context, UserSettingActivity.class);
            crmIntent.setAction(Intent.ACTION_VIEW);

            final ShortcutInfo shortcutInfoCrm = new ShortcutInfo.Builder(context, ShortcutConfig.CRM_SHORTCUT_ID)
                    .setShortLabel(StringUtils.getString(R.string.main_crm_shortcut_name))
                    .setIcon(Icon.createWithResource(context, R.drawable.main_ic_crm))
                    .setIntent(crmIntent)
                    .build();

            targetShortcuts.add(shortcutInfoCrm);
            shortcutManager.addDynamicShortcuts(targetShortcuts);
        }
    }


    private static void unRegisterShortcuts(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            if (shortcutManager == null) {
                return;
            }
            List<String> shortcutIds = new ArrayList<>();
            List<ShortcutInfo> pinnedShortcuts = shortcutManager.getPinnedShortcuts();
            for (ShortcutInfo pinnedShortcut : pinnedShortcuts) {
                if (ObjectUtils.equals(pinnedShortcut.getId(), ShortcutConfig.CRM_SHORTCUT_ID)) {
                    shortcutIds.add(pinnedShortcut.getId());
                }
            }
            shortcutIds.add(ShortcutConfig.CRM_SHORTCUT_ID);
            LogUtils.d("aaa" + shortcutIds.toString());
            shortcutManager.disableShortcuts(shortcutIds);
            shortcutManager.removeDynamicShortcuts(shortcutIds);
        }
    }


    //endregion
}
