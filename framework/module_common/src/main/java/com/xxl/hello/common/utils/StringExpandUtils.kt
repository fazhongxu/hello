package com.xxl.hello.common.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import androidx.annotation.ColorInt
import com.xxl.core.widget.text.TouchableSpan
import com.xxl.hello.common.R
import com.xxl.kit.AppUtils
import com.xxl.kit.ResourceUtils
import com.xxl.kit.StringUtils

/**
 * 字符串处理扩展工具类
 *
 * @author xxl.
 * @date 2023/3/3.
 */
class StringExpandUtils private constructor() {

    companion object {

        /**
         * 构建高亮文本
         *
         * @param content 内容
         * @param keyword 关键字
         */
        fun buildHighlightPrimaryColor(content: String,
                                       keyword: String): Spannable {
            val spannable: Spannable = SpannableString(content)
            val highlightColor = ResourceUtils.getAttrColor(AppUtils.getTopActivity(), R.attr.h_common_primary_color)
            StringUtils.setHighlightColor(spannable, keyword, highlightColor)
            return spannable
        }

        /**
         * 构建高亮文本
         *
         * @param content 内容
         * @param keyword 关键字
         */
        fun buildHighlightPrimaryColor(content: Spannable?,
                                       keyword: String?): Spannable? {
            val spannable: Spannable = SpannableString(content)
            val highlightColor = ResourceUtils.getAttrColor(AppUtils.getTopActivity(), R.attr.h_common_primary_color)
            StringUtils.setHighlightColorId(spannable, keyword, highlightColor)
            return spannable
        }

        /**
         * 构建可以点击的 span
         *
         * @param normalTextColor
         * @param pressedTextColor
         * @param listener
         * @return
         */
        fun buildTouchableSpan(content: String,
                               keyword: String,
                               @ColorInt normalTextColor: Int,
                               @ColorInt pressedTextColor: Int,
                               listener: View.OnClickListener): Spannable? {
            val start: Int = content.indexOf(keyword)
            val end = start + keyword.length
            val spannableString = SpannableString(content)
            val touchableSpan = buildTouchableSpan(normalTextColor, pressedTextColor, Color.TRANSPARENT, listener)
            spannableString.setSpan(touchableSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            return spannableString
        }

        /**
         * 构建可以点击的 span
         *
         * @param normalTextColor
         * @param pressedTextColor
         * @param listener
         * @return
         */
        fun buildTouchableSpan(@ColorInt normalTextColor: Int,
                               @ColorInt pressedTextColor: Int,
                               listener: View.OnClickListener): TouchableSpan? {
            return buildTouchableSpan(normalTextColor, pressedTextColor, Color.TRANSPARENT, listener)
        }

        /**
         * 构建可以点击的 span
         *
         * @param normalTextColor
         * @param pressedTextColor
         * @param pressedBackgroundColor
         * @param listener
         * @return
         */
        fun buildTouchableSpan(@ColorInt normalTextColor: Int,
                               @ColorInt pressedTextColor: Int,
                               @ColorInt pressedBackgroundColor: Int,
                               listener: View.OnClickListener): TouchableSpan {
            return buildTouchableSpan(normalTextColor, pressedTextColor, Color.TRANSPARENT, pressedBackgroundColor, listener)
        }

        /**
         * 构建可以点击的 span
         *
         * @param normalTextColor
         * @param pressedTextColor
         * @param normalBackgroundColor
         * @param pressedBackgroundColor
         * @param listener
         * @return
         */
        fun buildTouchableSpan(@ColorInt normalTextColor: Int,
                               @ColorInt pressedTextColor: Int,
                               @ColorInt normalBackgroundColor: Int,
                               @ColorInt pressedBackgroundColor: Int,
                               listener: View.OnClickListener): TouchableSpan {
            return object : TouchableSpan(normalTextColor, pressedTextColor, normalBackgroundColor, pressedBackgroundColor) {
                override fun onSpanClick(view: View) {
                    listener.onClick(view)
                }
            }
        }

        /**
         * 解析url 中的key value
         * https://www.baidu.com/s?uid=1212&type=3&audio_id=1200
         * 返回 uid,1212,type,3 的key value 组成的map
         *
         * @param targetUrl
         * @return
         */
        fun parseUrlKey(targetUrl: String?): Map<String, String>? {
            val map: MutableMap<String, String> = LinkedHashMap()
            if (TextUtils.isEmpty(targetUrl)) {
                return map
            }
            try {
                val index = targetUrl?.indexOf("?")
                if (index!! <= 0) {
                    return map
                }
                val url = targetUrl?.substring(index + 1)
                val splits: Array<String> = url.split("&").toTypedArray()
                if (splits != null && splits.size > 0) {
                    for (keyValue in splits) {
                        val s: Array<String> = keyValue.split("=").toTypedArray()
                        if (s.size > 1) {
                            map[s[0]] = s[1]
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return map
        }

    }
}