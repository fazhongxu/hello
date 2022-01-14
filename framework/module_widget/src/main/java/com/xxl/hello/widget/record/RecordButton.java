package com.xxl.hello.widget.record;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xxl.hello.widget.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 自定义录制按钮
 * <pre>
 *        <com.xxl.hello.widget.record.RecordButton
 *         android:id="@+id/recordBtn"
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         app:buttonGap="10dp"
 *         android:layout_centerInParent="true"
 *         app:buttonRadius="40dp"
 *         app:maxMillisecond="10000"
 *         app:progressColor="@color/colorPrimary"
 *         app:progressStroke="15"
 *         app:stopIcon="@drawable/ic_launcher"
 *         app:recordIcon="@drawable/ic_keyboard_voice_white_36dp" />
 * </pre>
 *
 * @author xxl.
 * @date 2022/1/13.
 */
public class RecordButton extends View implements Animatable {

    /**
     * values to draw
     */
    private Paint buttonPaint;
    private Paint progressEmptyPaint;
    private Paint progressPaint;
    private RectF rectF;

    /**
     * Bitmap for record icon
     */
    private Bitmap bitmap;

    /**
     * Bitmap for record stop icon
     */
    private Bitmap recordStopBitmap;

    /**
     * record button radius
     */
    private float buttonRadius;

    /**
     * progress ring stroke
     */
    private int progressStroke;

    /**
     * spacing for button and progress ring
     */
    private float buttonGap;

    /**
     * record button fill color
     */
    private int buttonColor;

    /**
     * progress ring background color
     */
    private int progressEmptyColor;

    /**
     * progress ring arc color
     */
    private int progressColor;

    /**
     * record icon res id
     */
    private int recordIcon;

    /**
     * record stop icon res id
     */
    private int recordStopIcon;

    private boolean isRecording;

    private int currentMilliSecond;

    private int maxMilliSecond;
    /**
     * progress starting degress for arc
     */
    private final int startAngle = 270;

    /**
     * progress sweep angle degress for arc
     */
    private int sweepAngle;

    /**
     * Listener that notify on record and record finish
     */
    private OnRecordListener recordListener;

    public RecordButton(Context context) {
        this(context, null);
    }

    public RecordButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordButton);
        buttonRadius = typedArray.getDimension(R.styleable.RecordButton_buttonRadius, getResources().getDisplayMetrics().scaledDensity * 40f);
        progressStroke = typedArray.getInt(R.styleable.RecordButton_progressStroke, 10);
        buttonGap = typedArray.getDimension(R.styleable.RecordButton_buttonGap, getResources().getDisplayMetrics().scaledDensity * 8f);
        buttonColor = typedArray.getColor(R.styleable.RecordButton_buttonColor, Color.RED);
        progressEmptyColor = typedArray.getColor(R.styleable.RecordButton_progressEmptyColor, Color.LTGRAY);
        progressColor = typedArray.getColor(R.styleable.RecordButton_progressColor, Color.BLUE);
        recordIcon = typedArray.getResourceId(R.styleable.RecordButton_recordIcon, -1);
        recordStopIcon = typedArray.getResourceId(R.styleable.RecordButton_stopIcon, -1);
        maxMilliSecond = typedArray.getInt(R.styleable.RecordButton_maxMillisecond, 5000);
        typedArray.recycle();

        buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buttonPaint.setColor(buttonColor);
        buttonPaint.setStyle(Paint.Style.FILL);

        progressEmptyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressEmptyPaint.setColor(progressEmptyColor);
        progressEmptyPaint.setStyle(Paint.Style.STROKE);
        progressEmptyPaint.setStrokeWidth(progressStroke);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressStroke);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        rectF = new RectF();

        bitmap = BitmapFactory.decodeResource(context.getResources(), recordIcon);
        recordStopBitmap = BitmapFactory.decodeResource(context.getResources(), recordStopIcon);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        canvas.drawCircle(cx, cy, buttonRadius, buttonPaint);
        canvas.drawCircle(cx, cy, buttonRadius + buttonGap, progressEmptyPaint);

        if (isRecording) {
            if (recordStopIcon != -1) {
                canvas.drawBitmap(recordStopBitmap, (cx - recordStopBitmap.getHeight() / 2), (cy - recordStopBitmap.getWidth() / 2), null);
            }
        } else {
            if (recordIcon != -1) {
                canvas.drawBitmap(bitmap, (cx - bitmap.getHeight() / 2), (cy - bitmap.getWidth() / 2), null);
            }
        }

        sweepAngle = 360 * currentMilliSecond / maxMilliSecond;
        rectF.set(cx - buttonRadius - buttonGap, cy - buttonRadius - buttonGap, cx + buttonRadius + buttonGap, cy + buttonRadius + buttonGap);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, progressPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = (int) (buttonRadius * 2 + buttonGap * 2 + progressStroke + 30);

        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case View.MeasureSpec.AT_MOST:
                width = Math.min(size, widthSize);
                break;
            case View.MeasureSpec.UNSPECIFIED:
            default:
                width = size;
        }

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case View.MeasureSpec.AT_MOST:
                height = Math.min(size, heightSize);
                break;
            case View.MeasureSpec.UNSPECIFIED:
            default:
                height = size;
        }

        setMeasuredDimension(width, height);
    }

//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                start();
//                progressAnimate().start();
//                return true;
//            case MotionEvent.ACTION_UP:
//                stop();
//                return true;
//        }
//        return super.onTouchEvent(event);
//    }

    /**
     * record button scale animation starting
     */
    @Override
    public void start() {
        this.isRecording = true;
        this.scaleAnimation(1.1F, 1.1F);
        this.progressAnimate().start();
    }

    /**
     * record button scale animation stopping
     */
    @Override
    public void stop() {
        this.isRecording = false;
        this.currentMilliSecond = 0;
        this.scaleAnimation(1.0F, 1.0F);
    }

    @Override
    public boolean isRunning() {
        return this.isRecording;
    }

    /**
     * This method provides scale animation to view
     * between scaleX and scale Y values
     *
     * @param scaleX
     * @param scaleY
     */
    private void scaleAnimation(float scaleX, float scaleY) {
        this.animate().scaleX(scaleX).scaleY(scaleY).start();
    }

    /**
     * Progress starting animation
     *
     * @return progress animate
     */
    @SuppressLint("ObjectAnimatorBinding")
    private ObjectAnimator progressAnimate() {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", currentMilliSecond, maxMilliSecond);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animator.getAnimatedValue();
                if (isRecording) {
                    setCurrentMilliSecond(value);
                    if (recordListener != null) {
                        recordListener.onRecord();
                    }
                } else {
                    animation.cancel();
                    isRecording = false;

                    if (recordListener != null) {
                        recordListener.onRecordCancel();
                    }
                }

                if (value == maxMilliSecond) {
                    if (recordListener != null) {
                        recordListener.onRecordFinish();
                    }
                    stop();
                }
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(maxMilliSecond);
        return animator;
    }

    private final void setCurrentMilliSecond(int currentMilliSecond) {
        this.currentMilliSecond = currentMilliSecond;
        this.postInvalidate();
    }

    public final int getCurrentMiliSecond() {
        return this.currentMilliSecond;
    }

    public final void setButtonRadius(int buttonRadius) {
        this.buttonRadius = (float) buttonRadius;
    }

    public final void setButtonGap(int buttonGap) {
        this.buttonGap = (float) buttonGap;
    }

    public final void setRecordListener(@NotNull OnRecordListener recordListener) {
        this.recordListener = recordListener;
    }

}