package com.xxl.hello.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.umeng.socialize.PlatformConfig;
import com.xxl.core.BaseApplication;
import com.xxl.core.image.selector.MediaSelector;
import com.xxl.core.image.selector.MediaSelectorApp;
import com.xxl.core.image.selector.PictureSelectorEngineImpl;
import com.xxl.core.listener.IApplication;
import com.xxl.core.service.download.DownloadServiceUtils;
import com.xxl.core.utils.CrashHandler;
import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.common.config.ShareConfig;
import com.xxl.hello.common.config.ShortcutConfig;
import com.xxl.hello.common.utils.TbsUtils;
import com.xxl.hello.main.di.component.DaggerAppComponent;
import com.xxl.hello.service.handle.api.AppSchemeService;
import com.xxl.hello.user.ui.setting.UserSettingActivity;
import com.xxl.kit.AppRouterApi;
import com.xxl.kit.AppUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.StringUtils;
import com.xxl.kit.TimeUtils;

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

    /**
     * scheme处理类
     */
    @Inject
    AppSchemeService mAppSchemeService;

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
        LogUtils.init(isDebug(), "HELLO");
        CrashHandler.getInstance().init(this, "Hello", isDebug());
        MediaSelector.init(this);
        registerShortcuts(this);
    }

    /**
     * 在用户统一"隐私政策"后初始化插件
     */
    @Override
    public void initPluginsAfterAgreePrivacyPolicy() {
        super.initPluginsAfterAgreePrivacyPolicy();
        LogUtils.d("initPluginAfterAgreePrivacyPolicy");
        try {
            initSharePlatform();
            TimeUtils.initialize();
            DownloadServiceUtils.init(this, isDebug());
            TbsUtils.initX5Environment(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        mApplicationWrapper.logout();
    }

    /**
     * 获取服务端的加密key
     *
     * @return
     */
    @Override
    public String getRemoteEncryptKeyString() {
        // TODO: 2023/2/20 此key需要服务端给 
        return null;
    }

    /**
     * 获取本地加密的key
     *
     * @return
     */
    @Override
    public String getLocalEncryptKeyString() {
        return "7INg1uUMJn+kD/0EfVV/gWa0jt/cknkKXSaq8bU8R8c=";
    }

    /**
     * 初始化分享平台
     */
    private void initSharePlatform() {
        // TODO: 2022/7/21 key
        // app id,app secret
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setWXFileProvider("com.xxl.hello.share.fileprovider");
        PlatformConfig.setQQZone("101830139", "5d63ae8858f1caab67715ccd6c18d7a5");
        PlatformConfig.setQQFileProvider("com.xxl.hello.share.fileprovider");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setSinaFileProvider("com.xxl.hello.share.fileprovider");
    }

    /**
     * 获取分享 appkey
     *
     * @return
     */
    @Override
    public String getShareAppKey() {
        return ShareConfig.APP_KEY;
    }

    /**
     * 获取分享Secret
     *
     * @return
     */
    @Override
    public String getShareSecret() {
        return ShareConfig.APP_SECRET;
    }

    /**
     * 获取分享渠道
     *
     * @return
     */
    @Override
    public String getChannel() {
        return ShareConfig.CHANNEL;
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
     *
     * @param requestCode
     */
    @Override
    public void navigationToLogin(int requestCode) {
        if (AppUtils.getTopActivity() == null) {
            LogUtils.e("AppUtils.getTopActivity() is null");
            return;
        }
        if (requestCode > 0) {
            AppRouterApi.Login.navigation(AppUtils.getTopActivity(), requestCode);
            return;
        }
        AppRouterApi.Login.navigation(AppUtils.getTopActivity());
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

    //region: shortcuts

    /**
     * 注册shortcuts
     *
     * @param context
     */
    private void registerShortcuts(@NonNull final Context context) {
        final Intent crmIntent = new Intent(context, UserSettingActivity.class);
        crmIntent.setAction(Intent.ACTION_VIEW);
        final ShortcutInfo crmShortcutInfo = ShortcutConfig.buildShortcutInfo(context, crmIntent, ShortcutConfig.CRM_SHORTCUT_ID, StringUtils.getString(R.string.main_crm_shortcut_name), R.drawable.main_ic_crm, StringUtils.getString(R.string.main_crm_shortcut_disable));
        ShortcutConfig.registerShortcut(context, crmShortcutInfo);
    }

    //endregion
}
