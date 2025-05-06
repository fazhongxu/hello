package com.xxl.hello.widget.ui.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.xxl.hello.widget.R;
import com.xxl.kit.DisplayUtils;

/**
 * reference https://github.com/gittjy/CirclePercentBar
 */
public class CirclePercentBar extends View {

    private int mArcColor;
    private int mArcWidth;
    private int mCenterTextColor;
    private int mPercentDesTextColor;
    private int mCenterTextSize;
    private int mPercentDesTextSize;
    private int mCircleRadius;
    private Paint arcPaint;
    private Paint arcCirclePaint;
    private Paint centerTextPaint;
    private Paint percentDesTextPaint;
    private RectF arcRectF;
    private Rect textBoundRect;
    private Rect desTextBoundRect;
    private float mCurData = 0;
    private String mSuffix = "%";
    private String mPercentDes = "";
    private int arcStartColor;
    private int arcEndColor;
    private Paint startCirclePaint;

    public CirclePercentBar(Context context) {
        this(context, null);
    }

    public CirclePercentBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentBar, defStyleAttr, 0);
        mArcColor = typedArray.getColor(R.styleable.CirclePercentBar_arcColor, 0xff0000);
        mArcWidth = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_arcWidth, DisplayUtils.dp2px(context, 20));
        mCenterTextColor = typedArray.getColor(R.styleable.CirclePercentBar_centerTextColor, 0x0000ff);
        mCenterTextSize = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_centerTextSize, DisplayUtils.dp2px(context, 20));
        mPercentDesTextColor = typedArray.getColor(R.styleable.CirclePercentBar_percentDesTextColor, 0x0000ff);
        mPercentDesTextSize = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_percentDesTextSize, DisplayUtils.dp2px(context, 20));
        mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_circleRadius, DisplayUtils.dp2px(context, 100));
        arcStartColor = typedArray.getColor(R.styleable.CirclePercentBar_arcStartColor, Color.parseColor("#1212F6"));
        arcEndColor = typedArray.getColor(R.styleable.CirclePercentBar_arcEndColor, Color.parseColor("#F212C6"));

        typedArray.recycle();

        initPaint();

    }

    private void initPaint() {

        startCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        startCirclePaint.setStyle(Paint.Style.FILL);
        //startCirclePaint.setStrokeWidth(mArcWidth);
        startCirclePaint.setColor(arcStartColor);

        arcCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcCirclePaint.setStyle(Paint.Style.STROKE);
        arcCirclePaint.setStrokeWidth(mArcWidth);
        arcCirclePaint.setColor(Color.parseColor("#100000ff"));
        arcCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(mArcWidth);
        arcPaint.setColor(mArcColor);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setStyle(Paint.Style.FILL);
        centerTextPaint.setColor(mCenterTextColor);
        centerTextPaint.setTextSize(mCenterTextSize);

        percentDesTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percentDesTextPaint.setStyle(Paint.Style.FILL);
        percentDesTextPaint.setColor(mPercentDesTextColor);
        percentDesTextPaint.setTextSize(mPercentDesTextSize);

        //圓弧的外接矩形
        arcRectF = new RectF();

        //文字的边界矩形
        textBoundRect = new Rect();
        desTextBoundRect = new Rect();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mCircleRadius * 2;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.rotate(-90, getWidth() / 2, getHeight() / 2);

        arcRectF.set(getWidth() / 2 - mCircleRadius + mArcWidth / 2, getHeight() / 2 - mCircleRadius + mArcWidth / 2
                , getWidth() / 2 + mCircleRadius - mArcWidth / 2, getHeight() / 2 + mCircleRadius - mArcWidth / 2);


        canvas.drawArc(arcRectF, 0, 360, false, arcCirclePaint);

        arcPaint.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2, arcStartColor, arcEndColor));
        canvas.drawArc(arcRectF, 0, 360 * mCurData / 100, false, arcPaint);

        canvas.rotate(90, getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2 - mCircleRadius + mArcWidth / 2, mArcWidth / 2, startCirclePaint);

        String data = String.valueOf(mCurData) + mSuffix;
        centerTextPaint.getTextBounds(data, 0, data.length(), textBoundRect);

        if (TextUtils.isEmpty(mPercentDes)) {
            canvas.drawText(data, getWidth() / 2 - textBoundRect.width() / 2, getHeight() / 2 + textBoundRect.height() / 2, centerTextPaint);
        } else {
            percentDesTextPaint.getTextBounds(mPercentDes, 0, mPercentDes.length(), desTextBoundRect);
            canvas.drawText(data, getWidth() / 2 - textBoundRect.width() / 2, (getHeight() / 2 + textBoundRect.height() / 2) - 20, centerTextPaint);
            canvas.drawText(mPercentDes, getWidth() / 2 - desTextBoundRect.width() / 2, (getHeight() / 2 + desTextBoundRect.height() / 2) + 80, percentDesTextPaint);
        }
    }

    public void setPercentData(float data, String percentDes) {
        setPercentData(data, percentDes, false, new DecelerateInterpolator());
    }

    public void setPercentData(float data, String percentDes, boolean reset, TimeInterpolator interpolator) {
        if (reset) {
            mCurData = 0;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mCurData, data);
        valueAnimator.setDuration((long) (Math.abs(mCurData - data) * 30));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                mCurData = (float) (Math.round(value * 10)) / 10;
                mPercentDes = percentDes;
                invalidate();
            }
        });
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.start();
    }
}