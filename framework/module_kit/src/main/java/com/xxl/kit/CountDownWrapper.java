package com.xxl.kit;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

/**
 * 倒计时包装类
 *
 * @author xxl.
 * @date 2022/6/24.
 */
public class CountDownWrapper {

    private CountDownTimer mCountDownTimer;

    private PreciseCountdown mPreciseCountdown;

    private Handler mHander = new Handler(Looper.getMainLooper());

    /**
     * 是否是自定义精确的倒计时
     */
    private boolean mIsPrecise;

    private OnCountDownCallback mCallBack;

    private CountDownWrapper(final boolean isPrecise,
                             OnCountDownCallback callback) {
        mIsPrecise = isPrecise;
        mCallBack = callback;
    }

    public static CountDownWrapper create(OnCountDownCallback callback) {
        return new CountDownWrapper(false, callback);
    }

    public static CountDownWrapper create(boolean isPrecise,
                                          OnCountDownCallback callback) {
        return new CountDownWrapper(isPrecise, callback);
    }

    public void start(long millisInFuture) {
        start(millisInFuture, 1000);
    }

    public void start(long millisInFuture, long countDownInterval) {
        cancel();
        if (mIsPrecise) {
            mPreciseCountdown = new PreciseCountdown(millisInFuture, countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (mHander != null) {
                        mHander.post(() -> mCallBack.onTick(millisUntilFinished));
                    }
                }

                @Override
                public void onFinished() {
                    if (mHander != null) {
                        mHander.post(() -> mCallBack.onFinish());
                    }
                }
            };
            mPreciseCountdown.start();
            return;
        }
        mCountDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (mCallBack != null) {
                    mCallBack.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (mCallBack != null) {
                    mCallBack.onFinish();
                }
            }
        };
        mCountDownTimer.start();
    }

    public void cancel() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        if (mPreciseCountdown != null) {
            mPreciseCountdown.stop();
        }
    }

    public void dispose() {

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        if (mPreciseCountdown != null) {
            mPreciseCountdown.dispose();
        }
    }

    public interface OnCountDownCallback {

        /**
         * 定期触发回调
         *
         * @param millisUntilFinished 单位时间内完成倒计时后剩余的时间
         */
        void onTick(long millisUntilFinished);

        /**
         * 倒计时结束
         */
        void onFinish();
    }
}