package com.xxl.hello.main.ui.main.window;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xxl.hello.main.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 用户《隐私政策》弹窗
 *
 * @author xxl.
 * @date 2021/11/26.
 */
public class PrivacyPolicyPopupWindow extends BasePopupWindow {

    //region: 成员变量

    /**
     * 不同意点击
     */
    private View.OnClickListener mOnDisagreeClickListener;

    /**
     * 同意点击
     */
    private View.OnClickListener mOnAgreeClickListener;

    //endregion

    //region: 构造函数

    private PrivacyPolicyPopupWindow(@NonNull final Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER)
                .setOutSideDismiss(false)
                .setOutSideTouchable(false)
                .setBackPressEnable(false);
        setupLayout();
    }

    public final static PrivacyPolicyPopupWindow from(@NonNull final Context context) {
        return new PrivacyPolicyPopupWindow(context);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.main_window_layout_privacy_policy);
        setContentView(rootView);
        final TextView tvDisagree = rootView.findViewById(R.id.tv_disagree);
        final TextView tvAgree = rootView.findViewById(R.id.tv_agree);
        tvDisagree.setOnClickListener(v -> {
            dismiss();
            if (mOnDisagreeClickListener != null) {
                mOnDisagreeClickListener.onClick(v);
            }
        });
        tvAgree.setOnClickListener(v -> {
            dismiss();
            if (mOnAgreeClickListener != null) {
                mOnAgreeClickListener.onClick(v);
            }
        });
    }

    //endregion

    //region: 提供方法

    /**
     * 设置不同意按钮点击事件监听
     *
     * @param listener
     * @return
     */
    public PrivacyPolicyPopupWindow setOnDisagreeClickListener(@NonNull final View.OnClickListener listener) {
        mOnDisagreeClickListener = listener;
        return this;
    }

    /**
     * 设置同意按钮点击事件监听
     *
     * @param listener
     * @return
     */
    public PrivacyPolicyPopupWindow setOnAgreeClickListener(@NonNull final View.OnClickListener listener) {
        mOnAgreeClickListener = listener;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}