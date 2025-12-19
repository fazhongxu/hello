package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xxl.hello.widget.R;
import com.xxl.kit.CountdownWrapper;

import java.util.concurrent.TimeUnit;

/**
 * 倒计时时间显示类
 *
 * @author xxl.
 * @date 2024/6/27.
 */
public class CountDownTimeView extends LinearLayout implements CountdownWrapper.OnCountDownCallback {

    /**
     * 天
     */
    private TextView mTvDay;

    /**
     * 天分隔
     */
    private ImageView mIvDaySplit;

    /**
     * 小时
     */
    private TextView mTvHour;

    /**
     * 分钟
     */
    private TextView mTvMinute;

    /**
     * 秒
     */
    private TextView mTvSecond;

    private long mCountDownTime;

    private CountdownWrapper mCountdownWrapper;

    public CountDownTimeView(Context context) {
        this(context, null);
    }

    public CountDownTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCountdownWrapper = CountdownWrapper.create(this);
        setupLayout(context);
    }

    /**
     * 设置视图
     *
     * @param context
     */
    private void setupLayout(Context context) {
        inflate(context, R.layout.widget_layout_count_down_time, this);
        mTvDay = findViewById(R.id.tv_day);
        mIvDaySplit = findViewById(R.id.tv_day_split);
        mTvHour = findViewById(R.id.tv_hour);
        mTvMinute = findViewById(R.id.tv_minute);
        mTvSecond = findViewById(R.id.tv_second);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);

        if (days > 0) {
            mTvDay.setVisibility(View.VISIBLE);
            mTvDay.setText(String.valueOf(days));
            mIvDaySplit.setVisibility(View.VISIBLE);
        } else {
            mTvDay.setVisibility(View.GONE);
            mTvDay.setText("");
            mIvDaySplit.setVisibility(View.GONE);
        }
        long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

        mTvHour.setText(String.valueOf(hours));
        mTvMinute.setText(String.valueOf(minutes));
        mTvSecond.setText(String.valueOf(seconds));
    }

    @Override
    public void onFinish() {
        mTvHour.setText("00");
        mTvMinute.setText("00");
        mTvSecond.setText("00");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCountdownWrapper != null) {
            mCountdownWrapper.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mCountdownWrapper != null && mCountDownTime > 0) {
            mCountdownWrapper.start(mCountDownTime);
        }
    }

    /**
     * 设置倒计时
     *
     * @param countDownTime
     */
    public void setCountDownTime(long countDownTime) {
        mCountDownTime = countDownTime;
        mCountdownWrapper.start(countDownTime);
    }
}