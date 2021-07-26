package com.xxl.hello.common.utils;

import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2021/7/23.
 */
public class StringUtils {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private StringUtils() {

    }

    public final static StringUtils obtain() {
        return new StringUtils();
    }

    public static boolean isSpace(String filePath) {
        // TODO: 2021/7/23  
        return false;
    }

    /**
     * 判断文本是否为空
     *
     * @param charSequence
     * @return
     */
    public static boolean isEmpty(@Nullable final CharSequence charSequence) {
        return charSequence == null || charSequence.length() <= 0;
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

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}