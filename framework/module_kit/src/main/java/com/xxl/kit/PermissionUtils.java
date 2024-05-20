package com.xxl.kit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * @author xxl.
 * @date 2024/4/19.
 */
public class PermissionUtils {

    private PermissionUtils() {

    }

    /**
     * 权限处理，系统返回拒绝时弹窗提示去设置页开启权限
     *
     * @return
     */
    public static ObservableTransformer<Boolean, Boolean> applyPermissionSetting() {
        return upstream -> upstream.filter((Predicate<Boolean>) aBoolean -> {
            if (aBoolean) {
                return true;
            }
            showGoToSettingsDialog(AppUtils.getTopActivity());
            return false;
        });
    }

    /**
     * 展示app系统设置页面
     *
     * @param context
     */
    public static void showGoToSettingsDialog(final Activity context) {
        if (context == null || context.isFinishing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("需要权限");
        builder.setMessage("我们需要您手动在设置中授权所需权限。");
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 引导用户到应用设置页面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

}