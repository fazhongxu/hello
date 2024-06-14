package com.xxl.hello.im.ui.template;

import android.content.Context;
import android.util.AttributeSet;
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
        if (mTargetView == null) {
            mTargetView = inflate(getContext(), provider.getLayoutRes(), this);
        }
        return mTargetView;
    }
}