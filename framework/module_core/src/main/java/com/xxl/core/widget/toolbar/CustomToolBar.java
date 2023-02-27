package com.xxl.core.widget.toolbar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * 自定义Toolbar
 *
 * @author xxl.
 * @date 2021/10/11.
 */
public class CustomToolBar extends Toolbar implements OnToolbarListener {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public CustomToolBar(@NonNull Context context) {
        this(context, null);
    }

    public CustomToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //endregion

    //region: 提供方法

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setToolbarTitle(@NonNull CharSequence title) {
        setTitle(title);
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    @Override
    public void setToolbarTitle(int resId) {
        setTitle(resId);
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}