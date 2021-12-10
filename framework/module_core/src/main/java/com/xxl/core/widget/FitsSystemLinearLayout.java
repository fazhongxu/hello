package com.xxl.core.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

/**
 * @author xxl.
 * @date 2021/10/22.
 */
public class FitsSystemLinearLayout extends LinearLayout {

    //region: 构造函数

    public FitsSystemLinearLayout(final Context context) {
        this(context, null);
    }

    public FitsSystemLinearLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitsSystemLinearLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //endregion

    //region: 页面生命周期

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        }
        return super.fitSystemWindows(insets);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0, insets.getSystemWindowInsetBottom()));
        } else {
            return insets;
        }
    }
    //endregion
}
