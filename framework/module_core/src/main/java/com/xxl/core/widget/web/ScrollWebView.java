package com.xxl.core.widget.web;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * 自定义滑动监听的WebView
 * 解决前端写的web 部分是滑动window（onScrollChanged正常执行）， 部分页面是滑动div(onScrollChanged 监听事件不回调问题)
 *
 * @author xxl.
 * @date 2022/6/25.
 */
public class ScrollWebView extends WebView {

    //region: 成员变量

    /**
     * 滑动监听
     */
    private OnScrollChangeListener mListener;

    //endregion

    //region: 构造函数

    public ScrollWebView(Context context) {
        this(context, null);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.onWebScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getScrollY() <= 0) {
                    scrollTo(0, 1);
                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    //endregion

    //region: 提供方法

    /**
     * 设置web滑动监听
     *
     * @param listener
     */
    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.mListener = listener;
    }

    //endregion

    //region: Inner Class OnScrollChangeListener

    public interface OnScrollChangeListener {

        /**
         * 滑动监听
         *
         * @param l
         * @param t
         * @param oldl
         * @param oldt
         */
        void onWebScrollChanged(int l, int t, int oldl, int oldt);
    }

    //endregion

}