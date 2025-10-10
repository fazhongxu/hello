package com.xxl.hello.widget.ui.window;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetWindowLayoutRecordCountdownBinding;
import com.xxl.kit.CountdownWrapper;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author xxl.
 * @date 2025/10/9.
 */
public class RecordCountdownPopupWindow extends BasePopupWindow implements CountdownWrapper.OnCountDownCallback {

    //region: 成员变量

    /**
     * 视图
     */
    private WidgetWindowLayoutRecordCountdownBinding mViewBinding;

    /**
     * 点击事件
     */
    private OnRecordCountdownPopupWindowListener mListener;

    /**
     * 时长
     */
    private int mDuration;

    /**
     * 倒计时包装类
     */
    private CountdownWrapper mCountdownWrapper;

    //endregion

    //region: 构造函数

    public RecordCountdownPopupWindow(@NonNull final Activity activity,
                                      final int duration,
                                      @Nullable final OnRecordCountdownPopupWindowListener listener) {
        super(activity);
        mDuration = duration;
        mListener = listener;
        setPopupGravity(Gravity.CENTER);
        setBackgroundColor(Color.TRANSPARENT);
        setOutSideDismiss(false);
        setOutSideTouchable(false);
        setBackPressEnable(false);
        setupLayout();
    }

    public static RecordCountdownPopupWindow from(@NonNull final Activity activity,
                                                  final int duration,
                                                  @Nullable final OnRecordCountdownPopupWindowListener listener) {
        return new RecordCountdownPopupWindow(activity, duration, listener);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
        mCountdownWrapper = CountdownWrapper.create(this);
        mCountdownWrapper.start((mDuration + 1) * 1000L);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountdownWrapper != null) {
            mCountdownWrapper.dispose();
        }
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.widget_window_layout_record_countdown);
        mViewBinding = DataBindingUtil.bind(rootView);
        setContentView(rootView);
    }

    //endregion

    //region: OnCountDownCallback

    @Override
    public void onTick(long millisUntilFinished) {
        long time = millisUntilFinished / 1000;
        if (time >= 1) {
            mViewBinding.tvDuration.setText(String.valueOf(time));
        } else {
            dismiss();
            if (mListener != null) {
                mListener.onFinished();
            }
        }
    }

    @Override
    public void onFinish() {

    }

    //endregion

    //region: 提供方法

    //endregion

    //region: OnRecordCountdownPopupWindowListener

    public interface OnRecordCountdownPopupWindowListener {

        /**
         * 倒计时结束
         */
        void onFinished();
    }

    //endregion

}