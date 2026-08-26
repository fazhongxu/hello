package com.xxl.hello.widget.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.xxl.hello.widget.R;
import com.xxl.kit.DisplayUtils;

/**
 * 滑块拼图验证控件
 *
 * <p>渲染服务端下发的背景图 + 滑块图，用户向右拖动滑块，使滑块图对准背景图中的缺口，
 * 松手后通过 {@link OnSwipeCaptchaListener#onCaptchaRelease(int)} 上报滑块横坐标偏移
 * （原始背景图像素），由调用方决定校验逻辑。
 *
 * @author xxl.
 * @date 2026/8/26.
 */
public class SwipeCaptchaView extends View {

    //region: 成员变量

    // 默认尺寸
    private static final int PADDING_DP = 12;
    private static final int TRACK_HEIGHT_DP = 50;
    private static final int THUMB_SIZE_DP = 44;
    private static final int TRACK_GAP_DP = 10;
    private static final int DEFAULT_HEIGHT_DP = 210;

    // 默认颜色
    private static final int COLOR_THUMB = Color.parseColor("#2F9DD8");
    private static final int COLOR_TRACK_BG = Color.parseColor("#E4E7ED");
    private static final int COLOR_TRACK_PROGRESS = Color.parseColor("#A8CBF0");
    private static final int COLOR_TEXT = Color.parseColor("#999999");
    private static final int COLOR_TEXT_SUCCESS = Color.parseColor("#2F9DD8");
    private static final int COLOR_TEXT_ERROR = Color.parseColor("#E64340");
    private static final int COLOR_ARROW = Color.WHITE;

    // 画笔
    private final Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final Paint mTrackBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mTrackProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mThumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 背景图（服务端下发，本地为生成）
     */
    private Bitmap mBgBitmap;

    /**
     * 滑块图（服务端下发，本地为生成）
     */
    private Bitmap mBlockBitmap;

    /**
     * 滑块在背景图中的初始 top 偏移量（原始图像像素）
     */
    private int mBlockY;

    /**
     * 滑块图原始宽高（像素）
     */
    private int mBlockWidth;
    private int mBlockHeight;

    /**
     * 当前滑块横坐标（视图坐标）
     */
    private float mBlockLeft;

    /**
     * 当前手柄横坐标（视图坐标）
     */
    private float mThumbLeft;

    /**
     * 是否正在拖动
     */
    private boolean mDragging;

    /**
     * 是否验证成功（成功后锁定）
     */
    private boolean mSuccess;

    /**
     * 是否处于验证失败提示状态
     */
    private boolean mError;

    /**
     * 待复位（重新设置数据后把滑块归位）
     */
    private boolean mPendingReset;

    /**
     * 手指按下时的横坐标与手柄位置
     */
    private float mDownX;
    private float mDownThumbLeft;

    /**
     * 提示文案
     */
    private String mTipsText;
    private String mSuccessText;
    private String mErrorText;

    /**
     * 验证结果回调
     */
    private OnSwipeCaptchaListener mListener;

    //endregion

    //region: 构造函数

    public SwipeCaptchaView(Context context) {
        this(context, null);
    }

    public SwipeCaptchaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeCaptchaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initText();
    }

    //endregion

    //region: 初始化

    private void initPaint() {
        mTrackBgPaint.setStyle(Paint.Style.FILL);
        mTrackBgPaint.setColor(COLOR_TRACK_BG);

        mTrackProgressPaint.setStyle(Paint.Style.FILL);
        mTrackProgressPaint.setColor(COLOR_TRACK_PROGRESS);

        mThumbPaint.setStyle(Paint.Style.FILL);
        mThumbPaint.setColor(COLOR_THUMB);

        mArrowPaint.setStyle(Paint.Style.STROKE);
        mArrowPaint.setColor(COLOR_ARROW);
        mArrowPaint.setStrokeWidth(dp(2));
        mArrowPaint.setStrokeCap(Paint.Cap.ROUND);
        mArrowPaint.setStrokeJoin(Paint.Join.ROUND);

        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(COLOR_TEXT);
        mTextPaint.setTextSize(sp(13));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void initText() {
        mTipsText = getContext().getString(R.string.resources_swipe_captcha_tips);
        mSuccessText = getContext().getString(R.string.resources_swipe_captcha_success_tips);
        mErrorText = getContext().getString(R.string.resources_swipe_captcha_fail_tips);
    }

    //endregion

    //region: 生命周期方法

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            height = computeDesiredHeight(width);
        }
        setMeasuredDimension(width, height);
    }

    private int computeDesiredHeight(int width) {
        if (mBgBitmap == null || mBgBitmap.getWidth() <= 0 || width <= 0) {
            return dp(DEFAULT_HEIGHT_DP);
        }
        final float scale = computeScale(width);
        final int bgDisplayHeight = (int) (mBgBitmap.getHeight() * scale);
        return dp(PADDING_DP) + bgDisplayHeight + dp(TRACK_GAP_DP) + dp(TRACK_HEIGHT_DP) + dp(PADDING_DP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int w = getWidth();
        if (w <= 0) {
            return;
        }
        if (mPendingReset) {
            mBlockLeft = contentLeft();
            mThumbLeft = contentLeft();
            mPendingReset = false;
        }

        final float contentLeft = contentLeft();
        final float contentTop = contentTop();
        final float contentRight = contentRight();
        final float scale = computeScale(w);

        // 1. 背景图
        if (mBgBitmap != null) {
            final int bgDisplayHeight = (int) (mBgBitmap.getHeight() * scale);
            final RectF bgDestRect = new RectF(contentLeft, contentTop, contentRight, contentTop + bgDisplayHeight);
            canvas.drawBitmap(mBgBitmap, null, bgDestRect, mBitmapPaint);
        }

        // 2. 滑块图（沿固定纵向位置水平滑动）
        if (mBlockBitmap != null) {
            final float blockDisplayWidth = mBlockWidth * scale;
            final float blockDisplayHeight = mBlockHeight * scale;
            final float blockDisplayTop = contentTop + mBlockY * scale;
            final RectF blockDestRect = new RectF(mBlockLeft, blockDisplayTop,
                    mBlockLeft + blockDisplayWidth, blockDisplayTop + blockDisplayHeight);
            canvas.drawBitmap(mBlockBitmap, null, blockDestRect, mBitmapPaint);
        }

        // 3. 滑块轨道
        final float trackLeft = contentLeft;
        final float trackRight = contentRight;
        final float trackTop = contentTop + (mBgBitmap != null ? (int) (mBgBitmap.getHeight() * scale) : 0) + dp(TRACK_GAP_DP);
        final float trackHeight = dp(TRACK_HEIGHT_DP);
        final float trackBottom = trackTop + trackHeight;
        final float trackRadius = trackHeight / 2f;
        canvas.drawRoundRect(new RectF(trackLeft, trackTop, trackRight, trackBottom), trackRadius, trackRadius, mTrackBgPaint);

        // 轨道进度填充
        final int thumbSize = dp(THUMB_SIZE_DP);
        if (mThumbLeft > trackLeft) {
            canvas.drawRoundRect(new RectF(trackLeft, trackTop, mThumbLeft + thumbSize, trackBottom),
                    trackRadius, trackRadius, mTrackProgressPaint);
        }

        // 滑块手柄
        final float thumbTop = trackTop - (thumbSize - trackHeight) / 2f;
        final RectF thumbRect = new RectF(mThumbLeft, thumbTop, mThumbLeft + thumbSize, thumbTop + thumbSize);
        canvas.drawRoundRect(thumbRect, thumbSize / 2f, thumbSize / 2f, mThumbPaint);
        drawArrow(canvas, thumbRect);

        // 4. 提示文案
        drawText(canvas, trackTop, trackBottom);
    }

    private void drawArrow(Canvas canvas, RectF thumbRect) {
        final float cx = thumbRect.centerX();
        final float cy = thumbRect.centerY();
        final float size = thumbRect.width() * 0.22f;
        final Path arrowPath = new Path();
        arrowPath.moveTo(cx - size, cy - size);
        arrowPath.lineTo(cx + size * 0.4f, cy);
        arrowPath.lineTo(cx - size, cy + size);
        canvas.drawPath(arrowPath, mArrowPaint);
    }

    private void drawText(Canvas canvas, float trackTop, float trackBottom) {
        final String text = mSuccess ? mSuccessText : (mError ? mErrorText : mTipsText);
        mTextPaint.setColor(mSuccess ? COLOR_TEXT_SUCCESS : (mError ? COLOR_TEXT_ERROR : COLOR_TEXT));
        final Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        final float baseline = (trackTop + trackBottom) / 2f - (fm.ascent + fm.descent) / 2f;
        canvas.drawText(text, getWidth() / 2f, baseline, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSuccess) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isInThumb(event.getX(), event.getY())) {
                    mDragging = true;
                    mError = false;
                    mDownX = event.getX();
                    mDownThumbLeft = mThumbLeft;
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                if (mDragging) {
                    updateThumb(event.getX());
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mDragging) {
                    mDragging = false;
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    onDragEnd();
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isInThumb(float x, float y) {
        final int trackHeight = dp(TRACK_HEIGHT_DP);
        final int thumbSize = dp(THUMB_SIZE_DP);
        final float trackTop = contentTop() + (mBgBitmap != null ? (int) (mBgBitmap.getHeight() * computeScale(getWidth())) : 0) + dp(TRACK_GAP_DP);
        final float thumbTop = trackTop - (thumbSize - trackHeight) / 2f;
        final RectF thumbRect = new RectF(mThumbLeft, thumbTop, mThumbLeft + thumbSize, thumbTop + thumbSize);
        final int expand = dp(8);
        return x >= thumbRect.left - expand && x <= thumbRect.right + expand
                && y >= thumbRect.top - expand && y <= thumbRect.bottom + expand;
    }

    private void updateThumb(float x) {
        final int thumbSize = dp(THUMB_SIZE_DP);
        final float trackLeft = contentLeft();
        final float trackRight = contentRight();
        mThumbLeft = clamp(mDownThumbLeft + (x - mDownX), trackLeft, trackRight - thumbSize);
        updateBlockLeft(trackLeft, trackRight, thumbSize);
        invalidate();
    }

    private void updateBlockLeft(float trackLeft, float trackRight, float thumbSize) {
        final float maxThumbOffset = trackRight - trackLeft - thumbSize;
        final float progress = maxThumbOffset <= 0 ? 0f : (mThumbLeft - trackLeft) / maxThumbOffset;
        final float blockDisplayWidth = mBlockWidth * computeScale(getWidth());
        mBlockLeft = trackLeft + progress * (trackRight - trackLeft - blockDisplayWidth);
    }

    /**
     * 拖动结束，上报滑块横坐标偏移
     */
    private void onDragEnd() {
        final float scale = computeScale(getWidth());
        final int xOffset = Math.round((mBlockLeft - contentLeft()) / scale);
        if (mListener != null) {
            mListener.onCaptchaRelease(xOffset);
        }
    }

    //endregion

    //region: 提供方法

    /**
     * 设置验证码数据（背景图 + 滑块图 + 几何参数）
     */
    public void setCaptcha(Bitmap bgBitmap, Bitmap blockBitmap, int y, int blockWidth, int blockHeight) {
        mBgBitmap = bgBitmap;
        mBlockBitmap = blockBitmap;
        mBlockY = y;
        mBlockWidth = blockWidth;
        mBlockHeight = blockHeight;
        mSuccess = false;
        mError = false;
        mDragging = false;
        mPendingReset = true;
        requestLayout();
        invalidate();
    }

    /**
     * 复位（重新开始滑动）
     */
    public void resetCaptcha() {
        mSuccess = false;
        mError = false;
        mDragging = false;
        mPendingReset = true;
        invalidate();
    }

    /**
     * 展示校验结果
     *
     * @param success 成功则锁定并展示成功文案；失败则提示并回弹滑块
     */
    public void showVerifyResult(boolean success) {
        if (success) {
            mSuccess = true;
            mError = false;
            invalidate();
            return;
        }
        mError = true;
        final float trackLeft = contentLeft();
        final ValueAnimator animator = ValueAnimator.ofFloat(mThumbLeft, trackLeft);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            mThumbLeft = (float) animation.getAnimatedValue();
            updateBlockLeft(trackLeft, contentRight(), dp(THUMB_SIZE_DP));
            invalidate();
        });
        animator.start();
    }

    public void setOnSwipeCaptchaListener(OnSwipeCaptchaListener listener) {
        mListener = listener;
    }

    public void setTipsText(String tipsText) {
        if (tipsText != null) {
            mTipsText = tipsText;
        }
    }

    public void setSuccessText(String successText) {
        if (successText != null) {
            mSuccessText = successText;
        }
    }

    public void setErrorText(String errorText) {
        if (errorText != null) {
            mErrorText = errorText;
        }
    }

    //endregion

    //region: 内部辅助方法

    private float computeScale(int width) {
        if (mBgBitmap == null || mBgBitmap.getWidth() <= 0) {
            return 1f;
        }
        final float contentWidth = width - 2f * dp(PADDING_DP);
        return contentWidth <= 0 ? 1f : contentWidth / mBgBitmap.getWidth();
    }

    private float contentLeft() {
        return dp(PADDING_DP);
    }

    private float contentTop() {
        return dp(PADDING_DP);
    }

    private float contentRight() {
        return getWidth() - dp(PADDING_DP);
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private int dp(int value) {
        return DisplayUtils.dp2px(getContext(), value);
    }

    private int sp(int value) {
        return DisplayUtils.sp2px(getContext(), value);
    }

    //endregion

    //region: 回调接口

    /**
     * 滑块验证结果回调
     */
    public interface OnSwipeCaptchaListener {

        /**
         * 用户拖动结束
         *
         * @param xOffset 滑块横坐标偏移（原始背景图像素坐标）
         */
        void onCaptchaRelease(int xOffset);
    }

    //endregion

}
