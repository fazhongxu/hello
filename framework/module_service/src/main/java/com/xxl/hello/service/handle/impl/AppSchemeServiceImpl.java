package com.xxl.hello.service.handle.impl;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xxl.core.utils.AppExpandUtils;
import com.xxl.hello.common.config.AppConfig;
import com.xxl.hello.router.api.UserRouterApi;
import com.xxl.hello.service.R;
import com.xxl.hello.service.handle.api.AppSchemeService;
import com.xxl.kit.AppRouterApi;
import com.xxl.kit.LogUtils;
import com.xxl.kit.RouterUtils;
import com.xxl.kit.StringUtils;
import com.xxl.kit.ToastUtils;

import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * @author xxl.
 * @date 2023/1/6.
 */
public class AppSchemeServiceImpl implements AppSchemeService {

    /**
     * scheme 标识
     */
    private static final String APP_SCHEME_TAG = AppConfig.APP_SCHEME_TAG;

    private static final LinkedHashMap<String, SchemeHandle> SCHEME_HANDLES = new LinkedHashMap<>();

    static {
        SCHEME_HANDLES.put("/user_setting", new UserSettingSchemeHandle());
    }

    /**
     * 导航
     *
     * @param context
     * @param payload
     * @param isToast
     */
    @Override
    public boolean navigationToScheme(@NonNull Context context,
                                      @NonNull String payload,
                                      final boolean isToast) {
        if (!TextUtils.isEmpty(payload)) {
            try {
                JSONObject jsonObject = new JSONObject(payload);
                String scheme = jsonObject.getString(AppConfig.APP_DEFAULT_SCHEME);
                if (!TextUtils.isEmpty(scheme)) {
                    boolean isLogged = !TextUtils.isEmpty(AppExpandUtils.getCurrentUserId()) && RouterUtils.hasActivity(AppRouterApi.MAIN_PATH);
                    if (isLogged) {
                        return navigation(context, scheme, true);
                    } else {
                        AppRouterApi.Splash.newBuilder()
                                .setNextPath(scheme)
                                .setExtraData(scheme)
                                .navigationAndClearTop();
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 导航
     *
     * @param context
     * @param path
     * @param isToast
     */
    @Override
    public boolean navigation(@NonNull Context context,
                              @NonNull String path,
                              final boolean isToast) {
        LogUtils.d("navigation path " + path);
        if (StringUtils.containsIgnoreCase(path, APP_SCHEME_TAG)) {
            final Uri targetUri = Uri.parse(path);
            final String targetPath = targetUri.getPath();
            final SchemeHandle schemeHandle = SCHEME_HANDLES.get(targetPath);
            if (schemeHandle != null) {
                return schemeHandle.handle(context, targetUri, isToast);
            } else {
                if (isToast) {
                    ToastUtils.warning(R.string.resources_can_not_parse_link_tips).show();
                }
            }
        }
        return false;
    }

    /**
     * 用户设置页面scheme处理
     */
    public static class UserSettingSchemeHandle implements SchemeHandle {

        /**
         * 事件处理
         *
         * @param context
         * @param targetUri
         * @param isToast
         */
        @Override
        public boolean handle(@NonNull Context context,
                              @NonNull Uri targetUri,
                              boolean isToast) {
            UserRouterApi.UserSetting.navigation();
            return true;
        }
    }

    public interface SchemeHandle {

        /**
         * 事件处理
         *
         * @param context
         * @param targetUri
         * @param isToast
         * @return
         */
        boolean handle(@NonNull final Context context,
                       @NonNull final Uri targetUri,
                       final boolean isToast);
    }
}