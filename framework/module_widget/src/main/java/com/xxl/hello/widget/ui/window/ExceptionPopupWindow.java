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
 * 异常信息弹窗
 *
 * @author xxl.
 * @date 2024/04/25.
 */
public class ExceptionPopupWindow extends BasePopupWindow {

    //region: 成员变量

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

    public ExceptionPopupWindow(@NonNull final Activity activity) {
        super(activity);
        setPopupGravity(Gravity.CENTER);
        setupLayout();
    }

    public static ExceptionPopupWindow from(@NonNull final Activity activity) {
        return new ExceptionPopupWindow(activity);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.widget_window_layout_exception);
        mTvMessage = ViewUtils.findView(rootView, R.id.tv_message);
        mTvCancel = ViewUtils.findView(rootView, R.id.tv_cancel);
        mTvConfirm = ViewUtils.findView(rootView, R.id.tv_confirm);
        setContentView(rootView);
    }

    //endregion

    //region: 提供方法

    /**
     * 设置消息
     *
     * @param message
     * @return
     */
    public ExceptionPopupWindow setMessage(final String message) {
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
    public ExceptionPopupWindow setNegativeButton(CharSequence text, View.OnClickListener listener) {
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
    public ExceptionPopupWindow setPositiveButton(CharSequence text, View.OnClickListener listener) {
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