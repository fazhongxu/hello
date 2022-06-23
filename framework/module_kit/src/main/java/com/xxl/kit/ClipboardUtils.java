package com.xxl.kit;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

/**
 * @author xxl.
 * @date 2022/06/23.
 */
public final class ClipboardUtils {

    private ClipboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Copy the text to clipboard.
     * <p>The label equals name of package.</p>
     *
     * @param text The text.
     */
    public static void copyText(final CharSequence text) {
        ClipboardManager cm = (ClipboardManager) AppUtils.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        //noinspection ConstantConditions
        cm.setPrimaryClip(ClipData.newPlainText(AppUtils.getApplication().getPackageName(), text));
    }

    /**
     * Copy the text to clipboard.
     *
     * @param label The label.
     * @param text  The text.
     */
    public static void copyText(final CharSequence label, final CharSequence text) {
        ClipboardManager cm = (ClipboardManager) AppUtils.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        //noinspection ConstantConditions
        cm.setPrimaryClip(ClipData.newPlainText(label, text));
    }

    /**
     * Clear the clipboard.
     */
    public static void clear() {
        ClipboardManager cm = (ClipboardManager) AppUtils.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        //noinspection ConstantConditions
        cm.setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    /**
     * Return the label for clipboard.
     *
     * @return the label for clipboard
     */
    public static CharSequence getLabel() {
        ClipboardManager cm = (ClipboardManager) AppUtils.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        //noinspection ConstantConditions
        ClipDescription des = cm.getPrimaryClipDescription();
        if (des == null) {
            return "";
        }
        CharSequence label = des.getLabel();
        if (label == null) {
            return "";
        }
        return label;
    }

    /**
     * Return the text for clipboard.
     *
     * @return the text for clipboard
     */
    public static CharSequence getText() {
        ClipboardManager cm = (ClipboardManager) AppUtils.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        //noinspection ConstantConditions
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            CharSequence text = clip.getItemAt(0).coerceToText(AppUtils.getApplication());
            if (text != null) {
                return text;
            }
        }
        return "";
    }

    /**
     * 获取剪切板内容
     *
     * @param callback
     */
    public static void getText(@NonNull final OnClipboardCallback<CharSequence> callback) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                AppUtils.getTopActivity().getWindow().getDecorView().post(() -> callback.invoke(getText()));
                return;
            }
            callback.invoke(getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the clipboard changed listener.
     */
    public static void addChangedListener(final ClipboardManager.OnPrimaryClipChangedListener listener) {
        ClipboardManager cm = (ClipboardManager) AppUtils.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        //noinspection ConstantConditions
        cm.addPrimaryClipChangedListener(listener);
    }

    /**
     * Remove the clipboard changed listener.
     */
    public static void removeChangedListener(final ClipboardManager.OnPrimaryClipChangedListener listener) {
        ClipboardManager cm = (ClipboardManager) AppUtils.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        //noinspection ConstantConditions
        cm.removePrimaryClipChangedListener(listener);
    }

    /**
     * 剪切板回调
     *
     * @param <T>
     */
    public interface OnClipboardCallback<T> {

        /**
         * 返回数据执行的方法
         *
         * @param value
         */
        void invoke(T value);
    }
}