package com.xxl.hello.widget.ui.window;

import android.app.Activity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.xxl.hello.widget.R;
import com.xxl.kit.DisplayUtils;

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
    private RoundTextView mTvCancel;

    /**
     * 确定
     */
    private RoundTextView mTvConfirm;

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
        mTvTitle = rootView.findViewById(R.id.tv_title);
        mTvMessage = rootView.findViewById(R.id.tv_message);
        mTvCancel = rootView.findViewById(R.id.tv_cancel);
        mTvConfirm = rootView.findViewById(R.id.tv_confirm);
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
        mTvTitle.setText(title);
        mTvTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param color
     * @return
     */
    public CommonMessagePopupWindow setTitleColor(@IntegerRes final int color) {
        mTvTitle.setTextColor(color);
        return this;
    }

    /**
     * 设置标题是否加粗
     *
     * @param fakeBoldText
     * @return
     */
    public CommonMessagePopupWindow setTitleFakeBoldText(final boolean fakeBoldText) {
        TextPaint paint = mTvTitle.getPaint();
        if (paint != null) {
            paint.setFakeBoldText(fakeBoldText);
        }
        return this;
    }

    /**
     * 设置标题是否可见
     *
     * @param isVisible
     * @return
     */
    public CommonMessagePopupWindow setTitleVisibility(final boolean isVisible) {
        mTvTitle.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置消息
     *
     * @param message
     * @return
     */
    public CommonMessagePopupWindow setMessage(final CharSequence message) {
        mTvMessage.setText(message);
        return this;
    }

    /**
     * 设置消息颜色
     *
     * @param color
     * @return
     */
    public CommonMessagePopupWindow setMesageColor(@IntegerRes final int color) {
        mTvMessage.setTextColor(color);
        return this;
    }

    /**
     * 设置消息是否加粗
     *
     * @param fakeBoldText
     * @return
     */
    public CommonMessagePopupWindow setMessageFakeBoldText(final boolean fakeBoldText) {
        TextPaint paint = mTvMessage.getPaint();
        if (paint != null) {
            paint.setFakeBoldText(fakeBoldText);
        }
        return this;
    }

    /**
     * 设置消息是否可见
     *
     * @param isVisible
     * @return
     */
    public CommonMessagePopupWindow setMessageVisibility(final boolean isVisible) {
        mTvMessage.setVisibility(isVisible ? View.VISIBLE : View.GONE);
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
        mTvCancel.setText(text);
        mTvCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(v);
            }
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
        mTvConfirm.setText(text);
        mTvConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(v);
            }
            dismiss();
        });
        return this;
    }

    /**
     * 设置只展示取消按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public CommonMessagePopupWindow setSingleNegativeButton(CharSequence text, View.OnClickListener listener) {
        mTvCancel.setText(text);
        mTvCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(v);
            }
            dismiss();
        });
        mTvConfirm.setVisibility(View.GONE);
        RoundViewDelegate delegate = mTvCancel.getDelegate();
        if (delegate != null) {
            delegate.setCornerRadius_BL(DisplayUtils.dp2px(8));
            delegate.setCornerRadius_BR(DisplayUtils.dp2px(8));
        }
        return this;
    }

    /**
     * 设置只展示确定按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public CommonMessagePopupWindow setSinglePositiveButton(CharSequence text, View.OnClickListener listener) {
        mTvConfirm.setText(text);
        mTvConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(v);
            }
            dismiss();
        });
        mTvCancel.setVisibility(View.GONE);
        RoundViewDelegate delegate = mTvConfirm.getDelegate();
        if (delegate != null) {
            delegate.setCornerRadius_BL(DisplayUtils.dp2px(8));
            delegate.setCornerRadius_BR(DisplayUtils.dp2px(8));
        }
        return this;
    }

    /**
     * 设置取消文本颜色
     *
     * @param color
     * @return
     */
    public CommonMessagePopupWindow setNegativeButtonTextColor(final int color) {
        mTvCancel.setTextColor(color);
        return this;
    }

    /**
     * 设置确定文本颜色
     *
     * @param color
     * @return
     */
    public CommonMessagePopupWindow setPositiveButtonTextColor(final int color) {
        mTvConfirm.setTextColor(color);
        return this;
    }

    /**
     * 设置取消文本是否加粗
     *
     * @param fakeBoldText
     * @return
     */
    public CommonMessagePopupWindow setNegativeButtonFakeBoldText(final boolean fakeBoldText) {
        TextPaint paint = mTvCancel.getPaint();
        if (paint != null) {
            paint.setFakeBoldText(fakeBoldText);
        }
        return this;
    }

    /**
     * 设置确定文本是否加粗
     *
     * @param fakeBoldText
     * @return
     */
    public CommonMessagePopupWindow setPositiveButtonFakeBoldText(final boolean fakeBoldText) {
        TextPaint paint = mTvConfirm.getPaint();
        if (paint != null) {
            paint.setFakeBoldText(fakeBoldText);
        }
        return this;
    }

    //endregion

    //endregion

}