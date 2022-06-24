package com.xxl.kit;

/**
 * 倒计时包装类
 *
 * @author xxl.
 * @date 2022/6/24.
 */
public class CountDownWrapper {

    private PreciseCountdown mPreciseCountdown;

    private OnCountDownCallback mCallBack;

    private CountDownWrapper(OnCountDownCallback callback) {
        mCallBack = callback;
    }

    public static CountDownWrapper create(OnCountDownCallback callback) {
        return new CountDownWrapper(callback);
    }

    public void start(long millisInFuture) {
        start(millisInFuture, 1000);
    }

    public void start(long millisInFuture, long countDownInterval) {
        cancel();
        mPreciseCountdown = new PreciseCountdown(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long timeLeft) {
                if (mCallBack != null) {
                    mCallBack.onTick(timeLeft);
                }
            }

            @Override
            public void onFinished() {
                if (mCallBack != null) {
                    mCallBack.onFinish();
                }
            }
        };
        mPreciseCountdown.start();
    }

    public void cancel() {
        if (mPreciseCountdown != null) {
            mPreciseCountdown.stop();
        }
    }

    /**
     * Call this when there's no further use for this timer
     */
    public void dispose() {
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