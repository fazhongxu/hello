package com.xxl.kit;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.UMShareAPI;

/**
 * 分享工具类
 *
 * @author xxl.
 * @date 2022/7/20.
 */
public class ShareUtils {

    /**
     * 预初始化
     *
     * @param context
     * @param appkey
     * @param channel
     * @param isDebug
     */
    public static void preInit(@NonNull final Context context,
                               @NonNull final String appkey,
                               @NonNull final String channel,
                               final boolean isDebug) {
        UMConfigure.setLogEnabled(isDebug);
        UMConfigure.preInit(context, appkey, channel);
    }

    /**
     * 初始化
     *
     * @param context
     * @param appkey
     * @param channel
     * @param pushSecret
     * @param isDebug
     */
    public static void init(@NonNull final Context context,
                            @NonNull final String appkey,
                            @NonNull final String pushSecret,
                            @NonNull final String channel,
                            final boolean isDebug) {
        UMConfigure.setLogEnabled(isDebug);
        UMConfigure.init(context, appkey, channel, UMConfigure.DEVICE_TYPE_PHONE, pushSecret);
    }

    /**
     * onActivityResult
     *
     * @param context
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(@NonNull final Context context,
                                        final int requestCode,
                                        final int resultCode,
                                        @Nullable final Intent data) {
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    private ShareUtils() {

    }

}