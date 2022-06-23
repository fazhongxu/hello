package com.xxl.kit;

import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.IllegalFormatException;

/**
 * @author xxl.
 * @date 2021/7/23.
 */
public class StringUtils {

    private StringUtils() {

    }

    public final static StringUtils obtain() {
        return new StringUtils();
    }

    /**
     * Return whether the string is null or 0-length.
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * Return whether the string is null or whitespace.
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * Return whether the string is null or white space.
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether string1 is equals to string2.
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) {
            return true;
        }
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether string1 is equals to string2, ignoring case considerations..
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * Return {@code ""} if string equals null.
     *
     * @param s The string.
     * @return {@code ""} if string equals null
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * Return the length of string.
     *
     * @param s The string.
     * @return the length of string
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * Set the first letter of string upper.
     *
     * @param s The string.
     * @return the string with first letter upper.
     */
    public static String upperFirstLetter(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (!Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) - 32) + s.substring(1);
    }

    /**
     * Set the first letter of string lower.
     *
     * @param s The string.
     * @return the string with first letter lower.
     */
    public static String lowerFirstLetter(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (!Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 将文本转换为long 类型
     *
     * @param text
     * @param defaultValue
     * @return
     */
    public static long toLong(@Nullable final String text,
                              final int defaultValue) {
        if (!isEmpty(text)) {
            try {
                return Long.parseLong(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * Return the string value associated with a particular resource ID.
     *
     * @param id The desired resource identifier.
     * @return the string value associated with a particular resource ID.
     */
    public static String getString(@StringRes int id) {
        return getString(id, (Object[]) null);
    }

    /**
     * Return the string value associated with a particular resource ID.
     *
     * @param id         The desired resource identifier.
     * @param formatArgs The format arguments that will be used for substitution.
     * @return the string value associated with a particular resource ID.
     */
    public static String getString(@StringRes int id, Object... formatArgs) {
        try {
            return format(AppUtils.getApplication().getString(id), formatArgs);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return String.valueOf(id);
        }
    }

    /**
     * Return the string array associated with a particular resource ID.
     *
     * @param id The desired resource identifier.
     * @return The string array associated with the resource.
     */
    public static String[] getStringArray(@ArrayRes int id) {
        try {
            return AppUtils.getApplication().getResources().getStringArray(id);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return new String[]{String.valueOf(id)};
        }
    }

    /**
     * Format the string.
     *
     * @param str  The string.
     * @param args The args.
     * @return a formatted string.
     */
    public static String format(@Nullable String str, Object... args) {
        String text = str;
        if (text != null) {
            if (args != null && args.length > 0) {
                try {
                    text = String.format(str, args);
                } catch (IllegalFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }

    /**
     * 设置高亮
     *
     * @param content 内容
     * @param keyword 关键字
     * @param colorId 高亮颜色
     */
    public static Spannable setHighlightColorId(final String content,
                                                final String keyword,
                                                @ColorRes final int colorId) {
        Spannable spannable = new SpannableString(content);
        setHighlightColorId(spannable, keyword, colorId);
        return spannable;
    }

    /**
     * 设置高亮
     *
     * @param content      内容
     * @param keyword      关键字
     * @param primaryColor 高亮颜色
     */
    public static Spannable setHighlightColor(final String content,
                                              final String keyword,
                                              final int primaryColor) {
        Spannable spannable = new SpannableString(content);
        setHighlightColor(spannable, keyword, primaryColor);
        return spannable;
    }

    /**
     * 设置高亮
     *
     * @param spannable 内容
     * @param keyword   关键字
     * @param colorId   高亮颜色
     */
    public static void setHighlightColorId(final Spannable spannable,
                                           final String keyword,
                                           @ColorRes final int colorId) {
        final int primaryColor = ContextCompat.getColor(AppUtils.getApplication().getApplicationContext(), colorId);
        setHighlightColor(spannable, keyword, primaryColor);
    }

    /**
     * 设置高亮
     *
     * @param spannable      内容
     * @param keyword        关键字
     * @param highLightColor 高亮颜色
     */
    public static void setHighlightColor(@NonNull Spannable spannable,
                                         @NonNull String keyword,
                                         @ColorInt int highLightColor) {
        setHighlightColor(spannable, keyword, highLightColor, true);
    }

    /**
     * 设置高亮
     *
     * @param spannable      内容
     * @param keyword        关键字
     * @param highLightColor 高亮颜色
     * @param ignoreCase     是否忽略大小写
     */
    public static void setHighlightColor(@NonNull Spannable spannable,
                                         @NonNull String keyword,
                                         @ColorInt int highLightColor,
                                         final boolean ignoreCase) {
        if (TextUtils.isEmpty(spannable) || TextUtils.isEmpty(keyword)) {
            return;
        }

        final String content = ignoreCase ? spannable.toString().toLowerCase() : spannable.toString();
        keyword = ignoreCase ? keyword.toLowerCase() : keyword;
        if (!content.contains(keyword)) {
            return;
        }
        String substring = content;
        int start = 0;
        try {
            while (!TextUtils.isEmpty(substring) && substring.contains(keyword)) {
                start = start + substring.indexOf(keyword);
                int end = start + keyword.length();
                final ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(highLightColor);
                spannable.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                substring = content.substring(end);
                start = end;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断字符串是否是网络地址
     *
     * @param s
     * @return
     */
    public static boolean isHttp(@Nullable final String s) {
        if (isEmpty(s)) {
            return false;
        }
        return s.toLowerCase().startsWith("http");
    }
}