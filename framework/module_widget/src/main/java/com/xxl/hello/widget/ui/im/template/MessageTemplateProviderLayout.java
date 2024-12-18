package com.xxl.hello.widget.ui.im.template;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 消息模板内容视图
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public class MessageTemplateProviderLayout extends FrameLayout {

    private View mTargetView;

    public MessageTemplateProviderLayout(@NonNull Context context,
                                         @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public <T extends MessageTemplateProvider> View inflate(T provider) {
        if (mTargetView != null) {
            removeView(mTargetView);
        }
        mTargetView = provider.inflate(getContext(), this);
        if (mTargetView != null) {
            addView(mTargetView);
        }

        return mTargetView;
    }

    /**
     * 设置子视图左对齐
     */
    public void setChildGravityLeft() {
        setChildViewGravity(Gravity.LEFT);
    }

    /**
     * 设置子视图右对齐
     */
    public void setChildGravityRight() {
        setChildViewGravity(Gravity.RIGHT);
    }

    /**
     * 设置子视图居中
     */
    public void setChildGravityCenter() {
        setChildViewGravity(Gravity.CENTER);
    }

    /**
     * 设置子视图对齐方式
     */
    public void setChildViewGravity(int gravity) {
        if (mTargetView != null) {
            FrameLayout.LayoutParams layoutParams = (LayoutParams) mTargetView.getLayoutParams();
            layoutParams.gravity = gravity;
        }
    }

}