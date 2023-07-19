package com.xxl.core.widget.window;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.R;
import com.xxl.core.data.model.enums.VipEnumsApi.VipModel;
import com.xxl.kit.ToastUtils;

import java.util.Random;

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
     * VIP模块
     */
    @VipModel
    private String mVipModel;

    /**
     * 功能ID
     */
    private long mFunctionId;

    //endregion

    //region: 构造函数

    public VipInterceptPopupWindow(@NonNull final Activity activity,
                                   @NonNull final OnVipInterceptPopupWindowListener listener,
                                   @VipModel final String vipModel,
                                   final long functionId) {
        super(activity);
        mListener = listener;
        mVipModel = vipModel;
        mFunctionId = functionId;
        setBackgroundColor(0);
        setupLayout();
        setOnBeforeShowCallback((view, view1, b) -> {
            requestData();
            return true;
        });
    }

    public static VipInterceptPopupWindow from(@NonNull final Activity activity,
                                               @NonNull final OnVipInterceptPopupWindowListener listener,
                                               @VipModel final String vipModel,
                                               final long functionId) {
        return new VipInterceptPopupWindow(activity, listener, vipModel, functionId);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.core_window_layout_vip_intercept);
        setContentView(rootView);
    }

    //endregion

    //region: 页面声明周期

    /**
     * 请求数据
     */
    private void requestData() {
        if (TextUtils.equals(mVipModel, VipModel.USER)) {
            requestUserVipFunctionInfo();
            return;
        }
    }

    /**
     * 请求用户模块VIP功能信息
     */
    private void requestUserVipFunctionInfo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //模拟请求VIP功能信息
                int nextInt = new Random().nextInt(2);
                dismiss();
                // 1 不需要VIP 0 需要VIP
                if (nextInt == 1) {
                    final UserVipPopupWindow.OnUserVipPopupWindowListener listener = new UserVipPopupWindow.OnUserVipPopupWindowListener() {
                        @Override
                        public void onOpenVipClick() {
                            if (mListener != null) {
                                mListener.onOpenVipClick();
                            }
                        }

                        @Override
                        public void onVerifyComplete(boolean isSuccess) {
                            if (mListener != null) {
                                mListener.onVerifyComplete(isSuccess);
                            }
                        }
                    };
                    UserVipPopupWindow.from(getContext(), listener)
                            .setFunctionId(mFunctionId)
                            .setUserVipInfo(false)
                            .showPopupWindow();
                } else {
                    ToastUtils.success("此功能不需要VIP了").show();
                    if (mListener != null) {
                        mListener.onVerifyComplete(true);
                    }
                }
            }
        }, 60);
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