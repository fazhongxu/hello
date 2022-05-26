package com.xxl.hello.common;

import android.text.Spannable;
import android.text.SpannableString;

import com.xxl.core.utils.StringUtils;

/**
 * @author xxl.
 * @date 2022/5/26.
 */
public class StringExpandUtils {

    private StringExpandUtils() {

    }

    /**
     * 构建高亮文本
     *
     * @param content 内容
     * @param keyword 关键字
     */
    public static Spannable buildHighlightPrimaryColor(final String content,
                                                       final String keyword) {
        final Spannable spannable = new SpannableString(content);
        StringUtils.setHighlightColorId(spannable, keyword, R.color.colorPrimary);
        return spannable;
    }

}