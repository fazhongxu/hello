package com.xxl.core.widget.window;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xxl.core.R;
import com.xxl.kit.ToastUtils;
import com.xxl.kit.ViewUtils;

import razerdp.basepopup.BasePopupWindow;

/**
 * VIP拦截弹窗
 *
 * @author xxl.
 * @date 2023/7/13.
 */
public class VipInterceptPopupWindow extends BasePopupWindow {

    //region: 成员变量

    /**
     * 点击事件
     */
    private OnVipInterceptPopupWindowListener mListener;

    /**
     * 开通VIP按钮
     */
    private TextView mTvOpenVip;

    //endregion

    //region: 构造函数

    public VipInterceptPopupWindow(@NonNull final Activity activity) {
        super(activity);
        setPopupGravity(Gravity.CENTER);
        setupLayout();
    }

    public static VipInterceptPopupWindow from(@NonNull final Activity activity) {
        return new VipInterceptPopupWindow(activity);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.core_window_layout_vip_intercept);
        mTvOpenVip = ViewUtils.findView(rootView, R.id.tv_open_vip);
        ViewUtils.setOnClickListener(mTvOpenVip, v -> {
            if (mListener != null) {
                mListener.onOpenVipClick();
            }
            ToastUtils.success(R.string.core_clicked_open_vip_text).show();
            dismiss();
        });
        setContentView(rootView);
    }

    //endregion

    //region: 提供方法

    /**
     * 展示弹窗
     */
    public void show() {
        showPopupWindow();
    }

    //endregion

    //region: OnVipInterceptPopupWindowListener

    public interface OnVipInterceptPopupWindowListener {

        /**
         * 开通VIP点击
         */
        void onOpenVipClick();
    }

    //endregion

    //endregion

}