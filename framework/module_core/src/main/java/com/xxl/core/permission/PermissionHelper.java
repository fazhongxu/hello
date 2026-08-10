package com.xxl.core.permission;

import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.kit.PermissionUtils;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 权限请求协调器：请求权限期间显示顶部说明弹窗，结果返回后关闭弹窗并回调。
 * <p>
 * 用于向用户解释权限用途，配合系统权限弹窗使用。结果统一回调 {@link Callback#onGranted()} /
 * {@link Callback#onDenied()}，可选 {@link #goToSettingsOnDenied(boolean)} 在被拒后引导去系统设置。
 *
 * @author xxl.
 * @date 2024/08/10.
 */
public class PermissionHelper {

    //region: 成员变量

    private final FragmentActivity mActivity;
    private final Fragment mFragment;
    private final RxPermissions mRxPermissions;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private CharSequence mInstructionTitle;
    private CharSequence mInstructionMessage;
    private boolean mShowInstruction = false;
    private boolean mGoToSettingsOnDenied = false;

    private PermissionInstructionPopup mPopup;

    //endregion

    //region: 构造函数

    private PermissionHelper(FragmentActivity activity, Fragment fragment) {
        mActivity = activity;
        mFragment = fragment;
        mRxPermissions = fragment != null ? new RxPermissions(fragment) : new RxPermissions(activity);
    }

    /**
     * 基于 Activity 创建
     *
     * @param activity
     * @return
     */
    public static PermissionHelper from(FragmentActivity activity) {
        return new PermissionHelper(activity, null);
    }

    /**
     * 基于 Fragment 创建
     *
     * @param fragment
     * @return
     */
    public static PermissionHelper from(Fragment fragment) {
        return new PermissionHelper(fragment.requireActivity(), fragment);
    }

    //endregion

    //region: 配置

    /**
     * 设置顶部说明弹窗的文案（请求权限期间显示）
     *
     * @param title
     * @param message
     * @return
     */
    public PermissionHelper instruction(CharSequence title, CharSequence message) {
        mInstructionTitle = title;
        mInstructionMessage = message;
        mShowInstruction = true;
        return this;
    }

    /**
     * 设置顶部说明弹窗的文案（请求权限期间显示）
     *
     * @param titleRes
     * @param messageRes
     * @return
     */
    public PermissionHelper instruction(int titleRes, int messageRes) {
        return instruction(mActivity.getString(titleRes), mActivity.getString(messageRes));
    }

    /**
     * 权限被拒后是否弹出「去系统设置」引导对话框，默认 false
     *
     * @param enable
     * @return
     */
    public PermissionHelper goToSettingsOnDenied(boolean enable) {
        mGoToSettingsOnDenied = enable;
        return this;
    }

    //endregion

    //region: 权限请求

    /**
     * 请求权限：发起前显示说明弹窗，结果返回后关闭弹窗并回调
     *
     * @param permissions 需要请求的权限
     * @param callback    授权/拒绝回调
     */
    public void request(String[] permissions, final Callback callback) {
        showInstruction();
        Disposable disposable = mRxPermissions.request(permissions)
                .subscribe(isGranted -> {
                    dismissInstruction();
                    if (isGranted) {
                        if (callback != null) {
                            callback.onGranted();
                        }
                    } else {
                        if (mGoToSettingsOnDenied) {
                            PermissionUtils.showGoToSettingsDialog(mActivity);
                        }
                        if (callback != null) {
                            callback.onDenied();
                        }
                    }
                }, throwable -> {
                    dismissInstruction();
                    if (callback != null) {
                        callback.onDenied();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    /**
     * 释放订阅与弹窗资源，建议在宿主销毁时调用
     */
    public void dispose() {
        dismissInstruction();
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    //endregion

    //region: 说明弹窗

    private void showInstruction() {
        if (!mShowInstruction || TextUtils.isEmpty(mInstructionTitle) || mPopup != null) {
            return;
        }
        mPopup = PermissionInstructionPopup.from(mActivity);
        mPopup.setTitle(mInstructionTitle);
        if (!TextUtils.isEmpty(mInstructionMessage)) {
            mPopup.setMessage(mInstructionMessage);
        }
        mPopup.showPopupWindow();
    }

    private void dismissInstruction() {
        if (mPopup != null) {
            mPopup.dismiss();
            mPopup = null;
        }
    }

    //endregion

    /**
     * 权限请求结果回调
     */
    public interface Callback {

        /**
         * 全部权限已授权
         */
        void onGranted();

        /**
         * 权限被拒（若开启 {@link #goToSettingsOnDenied(boolean)}，已先行弹出系统设置引导）
         */
        void onDenied();
    }
}
