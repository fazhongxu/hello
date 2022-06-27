package com.xxl.core.widget.web

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView

/**
 * 自定义滑动监听的WebView
 * 解决前端写的web 部分是滑动window（onScrollChanged正常执行）， 部分页面是滑动div(onScrollChanged 监听事件不回调问题)
 *
 * @author xxl.
 * @date 2022/6/26.
 */
class ScrollWebView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : WebView(context, attrs, defStyleAttr) {

    //region: 成员变量

    /**
     * 滑动监听
     */
    private var mListener: OnScrollChangeListener? = null

    //endregion

    //region: 页面生命周期

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mListener?.onWebScrollChanged(l, t, oldl, oldt)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> if (scrollY <= 0) {
                scrollTo(0, 1)
            }
            else -> {

            }
        }
        return super.onTouchEvent(event)
    }

    //endregion

    //region: 提供方法

    /**
     * 设置web滑动监听
     *
     * @param listener
     */
    fun setOnScrollChangeListener(listener: OnScrollChangeListener) {
        mListener = listener
    }

    //endregion

    //region: Inner Class OnScrollChangeListener

    interface OnScrollChangeListener {

        /**
         * 滑动监听
         */
        fun onWebScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)
    }

    //endregion
}