package com.xxl.hello.common.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.xxl.hello.common.R;
import com.xxl.kit.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

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

    /**
     * 构建高亮文本
     *
     * @param content 内容
     * @param keyword 关键字
     */
    public static Spannable buildHighlightPrimaryColor(final Spannable content,
                                                       final String keyword) {
        final Spannable spannable = new SpannableString(content);
        StringUtils.setHighlightColorId(spannable, keyword, R.color.colorPrimary);
        return spannable;
    }

    /**
     * 解析url 中的key value
     * https://www.baidu.com/s?uid=1212&type=3&audio_id=1200
     * 返回 uid,1212,type,3 的key value 组成的map
     *
     * @param targetUrl
     * @return
     */
    private Map<String, String> parseUrlKey(@Nullable final String targetUrl) {
        final Map<String, String> map = new LinkedHashMap<>();
        if (TextUtils.isEmpty(targetUrl)) {
            return map;
        }
        try {
            int index = targetUrl.indexOf("?");
            if (index <= 0) {
                return map;
            }
            final String url = targetUrl.substring(index + 1);
            final String[] splits = url.split("&");
            if (splits.length > 0) {
                for (String keyValue : splits) {
                    String[] s = keyValue.split("=");
                    if (s.length > 1) {
                        map.put(s[0], s[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}