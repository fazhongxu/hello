package com.xxl.core.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * progress bar 包装类
 *
 * @author xxl.
 * @date 2023/4/3.
 */
public class ProgressBarWrapper {

    //region: 成员变量

    /**
     * progress
     */
    private KProgressHUD mKProgressHUD;

    private Handler mHandler;

    //endregion

    //region: 构造函数

    private ProgressBarWrapper(@NonNull final Context context) {
        mKProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        mHandler = new Handler(Looper.getMainLooper());
    }

    public final static ProgressBarWrapper create(@NonNull final Context context) {
        return new ProgressBarWrapper(context);
    }

    //endregion

    //region: 提供方法

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroyView() {
        try {
            dismiss();
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        if (mKProgressHUD != null && mKProgressHUD.isShowing()) {
            mKProgressHUD.dismiss();
        }
    }

    /**
     * 加载
     *
     * @param attributes
     */
    public void loading(@NonNull final Attributes attributes) {
        if (mKProgressHUD == null) {
            return;
        }
        mHandler.post(() -> {
            if (!attributes.isLoading()) {
                dismiss();
                return;
            }
            if (attributes.getTitle() != null) {
                mKProgressHUD.setLabel(attributes.getTitle().toString());
            }
            mKProgressHUD.show();
        });
    }

    //endregion

    //region: Builder

    public static class Attributes {

        /**
         * 是否处于加载中
         */
        private boolean mLoading;

        /**
         * 标题
         */
        private CharSequence mTitle;

        /**
         * 样式
         */
        private KProgressHUD.Style mStyle = KProgressHUD.Style.SPIN_INDETERMINATE;

        /**
         * 是否可取消
         */
        private boolean mCancellable = true;

        public boolean isLoading() {
            return mLoading;
        }

        public CharSequence getTitle() {
            return mTitle;
        }

        public KProgressHUD.Style getStyle() {
            return mStyle;
        }

        public boolean isCancellable() {
            return mCancellable;
        }

        /**
         * 设置是否处于加载中
         *
         * @param isLoading
         */
        public void setLoading(boolean isLoading) {
            mLoading = isLoading;
        }

        /**
         * 设置标题
         *
         * @param title
         */
        public void setTitle(CharSequence title) {
            mTitle = title;
        }

        /**
         * 设置样式
         *
         * @param style
         * @return
         */
        public void setStyle(KProgressHUD.Style style) {
            mStyle = style;
        }

        /**
         * 设置是否可取消
         *
         * @param isCancellable
         * @return
         */
        public void setCancellable(boolean isCancellable) {
            mCancellable = isCancellable;
        }


    }

    public static class Builder {

        private Attributes mAttributes;

        public Builder() {
            mAttributes = new Attributes();
        }

        public static Builder create() {
            return new Builder();
        }

        /**
         * 设置是否处于加载中
         *
         * @param isLoading
         */
        public Builder setLoading(boolean isLoading) {
            mAttributes.setLoading(isLoading);
            return this;
        }

        /**
         * 设置标题
         *
         * @param title
         */
        public Builder setTitle(CharSequence title) {
            mAttributes.setTitle(title);
            return this;
        }

        /**
         * 设置样式
         *
         * @param style
         * @return
         */
        public Builder setStyle(KProgressHUD.Style style) {
            mAttributes.setStyle(style);
            return this;
        }

        /**
         * 设置是否可取消
         *
         * @param isCancellable
         * @return
         */
        public Builder setCancellable(boolean isCancellable) {
            mAttributes.setCancellable(isCancellable);
            return this;
        }

        public Attributes build() {
            return mAttributes;
        }
    }

    //endregion

    // region: 内部辅助方法

    //endregion

}