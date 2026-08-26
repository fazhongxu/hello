package com.xxl.hello.user.ui.login.window;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xxl.hello.user.R;
import com.xxl.hello.user.data.local.SwipeCaptchaLocalDataSource;
import com.xxl.hello.user.data.model.api.SwipeCaptchaData;
import com.xxl.hello.widget.ui.view.SwipeCaptchaView;
import com.xxl.kit.LogUtils;

import razerdp.basepopup.BasePopupWindow;

/**
 * 登录滑块验证弹窗
 *
 * <p>数据来源当前为本地生成（结构与服务端一致），校验逻辑也在本地完成；
 * 接入接口后，仅需替换 {@link #obtainCaptchaData()} 为真实请求 + 服务端校验即可。
 *
 * @author xxl.
 * @date 2026/8/26.
 */
public class SwipeCaptchaPopupWindow extends BasePopupWindow {

    //region: 常量

    /**
     * 校验容差（原始背景图像素）
     */
    private static final int VERIFY_TOLERANCE_PX = 4;

    /**
     * 成功提示展示时长
     */
    private static final int SUCCESS_DELAY_MS = 400;

    //endregion

    //region: 成员变量

    /**
     * 验证成功点击
     */
    private View.OnClickListener mOnSuccessClickListener;

    /**
     * 关闭点击
     */
    private View.OnClickListener mOnCloseClickListener;

    /**
     * 本地校验用正确横坐标（服务端场景由服务端校验，不再本地比较）
     */
    private int mCorrectX;

    //endregion

    //region: 构造函数

    private SwipeCaptchaPopupWindow(@NonNull final Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER)
                .setOutSideDismiss(true)
                .setOutSideTouchable(true)
                .setBackPressEnable(true);
        setupLayout();
    }

    public final static SwipeCaptchaPopupWindow from(@NonNull final Context context) {
        return new SwipeCaptchaPopupWindow(context);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        final View rootView = createPopupById(R.layout.user_window_layout_swipe_captcha);
        setContentView(rootView);

        final SwipeCaptchaView swipeCaptchaView = rootView.findViewById(R.id.swipe_captcha);
        swipeCaptchaView.setOnSwipeCaptchaListener(xOffset -> onCaptchaRelease(swipeCaptchaView, xOffset));

        bindCaptcha(swipeCaptchaView);

        final TextView tvClose = rootView.findViewById(R.id.tv_captcha_close);
        tvClose.setOnClickListener(v -> {
            dismiss();
            if (mOnCloseClickListener != null) {
                mOnCloseClickListener.onClick(v);
            }
        });
    }

    /**
     * 获取验证码数据并绑定到视图
     */
    private void bindCaptcha(@NonNull final SwipeCaptchaView swipeCaptchaView) {
        final SwipeCaptchaLocalDataSource.Captcha captcha = obtainCaptchaData();
        mCorrectX = captcha.correctX;

        final SwipeCaptchaData data = captcha.data;
        final Bitmap bgBitmap = SwipeCaptchaLocalDataSource.decodeBase64Image(data.getBgImage());
        final Bitmap blockBitmap = SwipeCaptchaLocalDataSource.decodeBase64Image(data.getBlockImage());
        swipeCaptchaView.setCaptcha(bgBitmap, blockBitmap,
                data.getY(), data.getBlockWidth(), data.getBlockHeight());
    }

    /**
     * 获取验证码数据（当前为本地生成，接口就绪后替换为服务端请求）
     */
    private SwipeCaptchaLocalDataSource.Captcha obtainCaptchaData() {
        return SwipeCaptchaLocalDataSource.obtainCaptcha();
    }

    /**
     * 拖动结束后的校验处理
     */
    private void onCaptchaRelease(@NonNull final SwipeCaptchaView swipeCaptchaView, final int xOffset) {
        LogUtils.d("滑块验证：challenge 校验中，拖动横坐标 = " + xOffset + "，正确横坐标 = " + mCorrectX);
        if (Math.abs(xOffset - mCorrectX) <= VERIFY_TOLERANCE_PX) {
            swipeCaptchaView.showVerifyResult(true);
            swipeCaptchaView.postDelayed(() -> {
                dismiss();
                if (mOnSuccessClickListener != null) {
                    mOnSuccessClickListener.onClick(swipeCaptchaView);
                }
            }, SUCCESS_DELAY_MS);
        } else {
            swipeCaptchaView.showVerifyResult(false);
        }
    }

    //endregion

    //region: 提供方法

    /**
     * 设置验证成功回调
     */
    public SwipeCaptchaPopupWindow setOnSuccessClickListener(@NonNull final View.OnClickListener listener) {
        mOnSuccessClickListener = listener;
        return this;
    }

    /**
     * 设置关闭回调
     */
    public SwipeCaptchaPopupWindow setOnCloseClickListener(@NonNull final View.OnClickListener listener) {
        mOnCloseClickListener = listener;
        return this;
    }

    //endregion

}
