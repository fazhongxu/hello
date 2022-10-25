package com.xxl.core.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;

/**
 * 侧滑菜单
 *
 * @author xxl.
 * @date 2022/10/25.
 */
public class SwipeMenuLayout extends EasySwipeMenuLayout {

    public SwipeMenuLayout(Context context) {
        this(context, null);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}