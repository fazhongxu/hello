package com.xxl.hello;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.didichuxing.doraemonkit.DoKit;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorManager;
import com.xxl.core.data.router.SystemRouterApi;

/**
 * DoKit 辅助类
 *
 * @author xxl.
 * @date 2023/4/14.
 */
public class DoKitHelper {

    private DoKitHelper() {

    }

    /**
     * 初始化
     *
     * @param application
     */
    public static void init(@NonNull final Application application) {
        new DoKit.Builder(application)
                .webDoorCallback(new WebDoorManager.WebDoorCallback() {
                    @Override
                    public void overrideUrlLoading(Context context, String url) {
                        SystemRouterApi.WebView.newBuilder(url)
                                .navigation();
                    }
                })
                .build();
    }

}