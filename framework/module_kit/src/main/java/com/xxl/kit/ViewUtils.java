package com.xxl.kit;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

import io.reactivex.SingleTransformer;

/**
 * View 相关工具类 findViewById,设置padding,margin,背景色，文本，获取layoutParams等
 *
 * @author xxl.
 * @date 2021/12/14.
 */
public final class ViewUtils {

    /**
     * findView
     *
     * @param parent
     * @param viewId
     * @param <V>
     * @return
     */
    public static <V extends View> V findView(View parent, int viewId) {
        if (parent == null) {
            return null;
        }
        return parent.findViewById(viewId);
    }

    /**
     * findView
     *
     * @param activity
     * @param viewId
     * @param <V>
     * @return
     */
    public static <V extends View> V findView(Activity activity, int viewId) {
        if (activity == null) {
            return null;
        }
        return activity.findViewById(viewId);
    }

    /**
     * set view visibility
     *
     * @param targetView
     * @param visibility
     */
    public static void setVisibility(View targetView, boolean visibility) {
        if (targetView == null) {
            return;
        }
        targetView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    /**
     * set view visibility
     *
     * @param targetView
     * @param visibility
     */
    public static void setVisibility(View targetView, int visibility) {
        if (targetView == null) {
            return;
        }
        targetView.setVisibility(visibility);
    }

    /**
     * set view setOnClickListener
     *
     * @param targetView
     * @param listener
     */
    public static void setOnClickListener(View targetView,
                                          View.OnClickListener listener) {
        if (targetView == null) {
            return;
        }
        targetView.setOnClickListener(listener);
    }


    /**
     * set text
     *
     * @param targetTextView
     * @param charSequence
     */
    public static void setText(TextView targetTextView, CharSequence charSequence) {
        if (targetTextView == null || charSequence == null) {
            return;
        }
        targetTextView.setText(charSequence);
    }

    /**
     * set text
     *
     * @param targetTextView
     * @param stringRes
     */
    public static void setText(TextView targetTextView, @StringRes int stringRes) {
        if (targetTextView == null) {
            return;
        }
        targetTextView.setText(stringRes);
    }

    /**
     * 判断是否是快速点击（重复点击）
     *
     * @return
     */
    public static boolean isFastClick() {
        return isFastClick(DEFAULT_CLICK_MILLIS);
    }

    /**
     * 判断是否是快速点击（重复点击）
     *
     * @return
     */
    public static boolean isFastClick(final long clickTimeMillis) {
        final long currentTimeMillis = System.currentTimeMillis();
        final boolean isFastClick = currentTimeMillis - sLastClickTimeMillis < (clickTimeMillis > 0 ? clickTimeMillis : DEFAULT_CLICK_MILLIS);
        sLastClickTimeMillis = currentTimeMillis;
        return isFastClick;
    }

    /**
     * 最近一次点击的时间戳（毫秒）
     */
    private static long sLastClickTimeMillis = 0;

    /**
     * 防止重复点击的间隔时间（毫秒）
     */
    private static final long DEFAULT_CLICK_MILLIS = 500L;

    private ViewUtils() {

    }

}