package com.xxl.hello.widget.ui.window;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.widget.R;
import com.xxl.kit.ViewUtils;

import razerdp.basepopup.BasePopupWindow;

/**
 * 用户模块VIP弹窗
 *
 * @author xxl.
 * @date 2023/7/13.
 */
public class UserVipPopupWindow extends BasePopupWindow {

    //region: 成员变量

    /**
     * 点击事件
     */
    private OnUserVipPopupWindowListener mListener;

    /**
     * 免费试用按钮
     */
    private TextView mTvFreeTrial;

    /**
     * 开通VIP按钮
     */
    private TextView mTvOpenVip;

    /**
     * 功能ID
     */
    private long mFunctionId;

    //endregion

    //region: 构造函数

    public UserVipPopupWindow(@NonNull final Activity activity,
                              @Nullable final OnUserVipPopupWindowListener listener) {
        super(activity);
        mListener = listener;
        setPopupGravity(Gravity.CENTER);
        setupLayout();
    }

    public static UserVipPopupWindow from(@NonNull final Activity activity,
                                          @Nullable final OnUserVipPopupWindowListener listener) {
        return new UserVipPopupWindow(activity, listener);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.widget_window_layout_vip_user);
        mTvFreeTrial = ViewUtils.findView(rootView, R.id.tv_free_trial);
        mTvOpenVip = ViewUtils.findView(rootView, R.id.tv_open_vip);

        ViewUtils.setOnClickListener(mTvFreeTrial, v -> {
            if (mListener != null) {
                mListener.onVerifyComplete(true);
            }
            dismiss();
        });

        ViewUtils.setOnClickListener(mTvOpenVip, v -> {
            if (mListener != null) {
                mListener.onOpenVipClick();
            }
            dismiss();
        });
        setContentView(rootView);
    }

    //endregion

    //region: 提供方法

    /**
     * 设置功能ID
     *
     * @param functionId
     * @return
     */
    public UserVipPopupWindow setFunctionId(final long functionId) {
        mFunctionId = functionId;
        return this;
    }

    /**
     * 设置用户VIP信息
     *
     * @param isVip
     * @return
     */
    public UserVipPopupWindow setUserVipInfo(final boolean isVip) {

        return this;
    }


    //endregion

    //region: OnUserVipPopupWindowListener

    public interface OnUserVipPopupWindowListener {

        /**
         * 开通VIP点击
         */
        void onOpenVipClick();

        /**
         * 验证完成
         *
         * @param isSuccess
         */
        void onVerifyComplete(final boolean isSuccess);
    }

    //endregion

    //endregion

}