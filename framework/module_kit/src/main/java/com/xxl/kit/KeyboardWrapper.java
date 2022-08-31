package com.xxl.kit;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 键盘处理包装类
 *
 * @author xxl.
 * @date 2022/8/31.
 */
public class KeyboardWrapper implements KeyboardUtils.OnSoftInputChangedListener {

    //region: 成员变量

    /**
     * 上下文
     */
    private Activity mActivity;

    /**
     * 内容布局，不被输入法遮挡的布局
     */
    private View mContentView;

    /**
     * 内容底部的布局,可以给这个布局设置一个键盘高度的margin，来让内容布局被顶起来不被挡住
     */
    private View mBottomView;

    /**
     * 键盘状态监听
     */
    private OnKeyboardStateChangeListener mKeyboardListener;

    //endregion

    //region: 构造函数

    public KeyboardWrapper(@NonNull final Activity activity,
                           @Nullable final View contentView,
                           @Nullable final View bottomView) {
        mActivity = activity;
        mContentView = contentView;
        mBottomView = bottomView;
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public static KeyboardWrapper create(@NonNull final Activity activity,
                                         @Nullable final View contentView,
                                         @Nullable final View bottomView) {
        KeyboardWrapper keyboardWrapper = new KeyboardWrapper(activity, contentView, bottomView);
        keyboardWrapper.register();
        return keyboardWrapper;
    }

    //endregion

    //region: 提供方法

    /**
     * 注册监听
     */
    public void register() {
        if (mActivity == null) {
            return;
        }
        KeyboardUtils.registerSoftInputChangedListener(mActivity, this);
    }

    /**
     * pause
     */
    public void onPause() {
        if (mActivity == null) {
            return;
        }
        KeyboardUtils.hideSoftInput(mActivity);
    }

    /**
     * 注销监听
     */
    public void onDestory() {
        if (mActivity == null) {
            return;
        }
        KeyboardUtils.unregisterSoftInputChangedListener(mActivity.getWindow());
    }

    /**
     * 设置键盘监听
     *
     * @param keyboardListener
     */
    public void setKeyboardStateChangeListener(@NonNull final OnKeyboardStateChangeListener keyboardListener) {
        mKeyboardListener = keyboardListener;
    }

    //endregion

    //endregion

    //region: KeyboardUtils.OnSoftInputChangedListener

    @Override
    public void onSoftInputChanged(int height) {
        if (height > 0) {
            mKeyboardListener.onOpenKeyboard(height);
        } else {
            mKeyboardListener.onCloseKeyboard(height);
        }
        LogUtils.d("键盘高度改变" + height);
    }

    //endregion

    //region: OnKeyboardStateChangeListener

    /**
     * 键盘改变监听
     */
    public interface OnKeyboardStateChangeListener {

        /**
         * 键盘打开
         *
         * @param keyboardHeight
         */
        void onOpenKeyboard(final int keyboardHeight);

        /**
         * 键盘关闭
         *
         * @param keyboardHeight
         */
        void onCloseKeyboard(final int keyboardHeight);
    }

    //endregion
}