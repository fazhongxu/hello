package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.xxl.hello.widget.R;

/**
 * 花朵view
 *
 * @author xxl.
 * @date 2025/4/15.
 */
public class FlowerView extends View {
    /**
     * 成绩
     */
    private int mScore;

    /**
     * 花朵数量
     */
    private int mFlowerCount;

    /**
     * 花朵图标
     */
    private Drawable mFlowerDrawable;  // 花朵图标

    /**
     * 花朵的宽度
     */
    private int mFlowerWidth;

    /**
     * 花朵之间的间隔
     */
    private int mFlowerSpacing;

    // 构造函数
    public FlowerView(Context context) {
        this(context, null);
    }

    public FlowerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowerView);
        mFlowerCount = a.getInteger(R.styleable.FlowerView_flower_count, 0);
        a.recycle();
        mFlowerDrawable = ContextCompat.getDrawable(context, R.drawable.resources_ic_flower);
        mFlowerWidth = mFlowerDrawable.getIntrinsicWidth();
        mFlowerSpacing = 10;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 计算总宽度：每个花朵的宽度乘以花朵数量，再加上花朵之间的间距
        int totalFlowerWidth = mFlowerWidth * mFlowerCount + mFlowerSpacing * (mFlowerCount - 1);

        // 获取宽度测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 如果宽度是wrap_content，设置为所需的宽度
        int width = (widthMode == MeasureSpec.AT_MOST) ? totalFlowerWidth : widthSize;

        // 获取高度测量模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 计算高度：只有一行，且只有花朵的高度和间距
        int height = mFlowerWidth;  // 花朵的高度
        if (heightMode == MeasureSpec.AT_MOST) {
            height += mFlowerSpacing;  // 如果是wrap_content，添加花朵间距
        } else {
            // 否则，直接使用给定的高度
            height = heightSize;
        }

        // 设置测量结果
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int totalWidth = getWidth();
        int flowerCount = mFlowerCount;

        // 计算总共能容纳的花朵数量
        int totalFlowerWidth = mFlowerWidth * flowerCount + mFlowerSpacing * (flowerCount - 1);

        // 如果花朵数量过多，需要缩放
        if (totalFlowerWidth > totalWidth) {
            int scaleFactor = totalWidth / (flowerCount + (flowerCount - 1) * mFlowerSpacing / mFlowerWidth);
            mFlowerWidth = mFlowerWidth * scaleFactor;
        }

        // 绘制花朵
        for (int i = 0; i < flowerCount; i++) {
            int left = i * (mFlowerWidth + mFlowerSpacing);
            mFlowerDrawable.setBounds(left, 0, left + mFlowerWidth, mFlowerWidth);
            mFlowerDrawable.draw(canvas);
        }
    }

    /**
     * 获取成绩
     *
     * @return
     */
    public int getScore() {
        return mScore;
    }

    /**
     * 设置成绩，更新花朵数量
     *
     * @param score
     */
    public void setScore(int score) {
        this.mScore = score;
        if (score >= 90 && score <= 100) {
            mFlowerCount = 5;
        } else if (score >= 80 && score <= 89) {
            mFlowerCount = 4;
        } else if (score >= 70 && score <= 79) {
            mFlowerCount = 3;
        } else if (score >= 60 && score <= 69) {
            mFlowerCount = 2;
        } else if (score > 0) {
            mFlowerCount = 1;
        } else {
            mFlowerCount = 0;
        }
        requestLayout();
        invalidate();
    }
}