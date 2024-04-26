package com.xxl.hello.widget.ui.window;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xxl.hello.widget.R;
import com.xxl.kit.ViewUtils;

import razerdp.basepopup.BasePopupWindow;

/**
 * 常用信息弹窗
 *
 * @author xxl.
 * @date 2024/04/25.
 */
public class CommonMessagePopupWindow extends BasePopupWindow {

    //region: 成员变量

    /**
     * 标题
     */
    private TextView mTvTitle;

    /**
     * 消息
     */
    private TextView mTvMessage;

    /**
     * 取消
     */
    private TextView mTvCancel;

    /**
     * 确定
     */
    private TextView mTvConfirm;

    //endregion

    //region: 构造函数

    public CommonMessagePopupWindow(@NonNull final Activity activity) {
        super(activity);
        setPopupGravity(Gravity.CENTER);
        setupLayout();
    }

    public static CommonMessagePopupWindow from(@NonNull final Activity activity) {
        return new CommonMessagePopupWindow(activity);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.widget_window_layout_common_message);
        mTvTitle = ViewUtils.findView(rootView, R.id.tv_title);
        mTvMessage = ViewUtils.findView(rootView, R.id.tv_message);
        mTvCancel = ViewUtils.findView(rootView, R.id.tv_cancel);
        mTvConfirm = ViewUtils.findView(rootView, R.id.tv_confirm);
        setContentView(rootView);
    }

    //endregion

    //region: 提供方法

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public CommonMessagePopupWindow setTitle(final CharSequence title) {
        ViewUtils.setText(mTvTitle, title);
        return this;
    }

    /**
     * 设置消息
     *
     * @param message
     * @return
     */
    public CommonMessagePopupWindow setMessage(final CharSequence message) {
        ViewUtils.setText(mTvMessage, message);
        return this;
    }

    /**
     * 设置取消按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public CommonMessagePopupWindow setNegativeButton(CharSequence text, View.OnClickListener listener) {
        ViewUtils.setText(mTvCancel, text);
        ViewUtils.setOnClickListener(mTvCancel, v -> {
            listener.onClick(v);
            dismiss();
        });
        return this;
    }

    /**
     * 设置确定按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public CommonMessagePopupWindow setPositiveButton(CharSequence text, View.OnClickListener listener) {
        ViewUtils.setText(mTvConfirm, text);
        ViewUtils.setOnClickListener(mTvConfirm, v -> {
            listener.onClick(v);
            dismiss();
        });
        return this;
    }

    //endregion

    //endregion

}