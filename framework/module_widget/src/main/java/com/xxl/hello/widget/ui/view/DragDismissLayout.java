package com.xxl.hello.widget.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.florent37.viewanimator.ViewAnimator;

/**
 * 自定义可拖拽消失的viewGroup
 *
 * @author xxl.
 * @date 2023/06/01.
 */
public class DragDismissLayout extends ViewGroup {

    /**
     * 透明度最大值
     */
    private static final int ALPHA = 255;

    /**
     * 关闭动画开始的下拉屏幕比率
     */
    private float mDragRatio = 0.2f;

    /**
     * //滑动阻力值
     */
    private float mResistance = 0.7f;

    /**
     * 动画时长
     */
    private int mAnimDuration = 250;

    /**
     * 附着的Activity
     */
    private Activity mActivity;

    /**
     * Activity的真实子布局
     */
    private View mContentView;

    /**
     * action_down事件的纵坐标
     */
    private int mActionDownY;

    /**
     * action_down事件的横坐标
     */
    private int mActionDownX;

    /**
     * 屏幕高度
     */
    private int mWindowHeight;

    /**
     * 辅助滑动
     */
    private Scroller mScroller;

    /**
     * 布局背景
     */
    private ColorDrawable mColorDrawable;

    /**
     * 上次滑动纵坐标
     */
    private int mLastY = 0;

    /**
     * 上次滑动横坐标
     */
    private int mLastX = 0;

    /**
     * 目标View的横坐标
     */
    private int mTargetLeft;

    /**
     * 目标View的纵坐标
     */
    private int mTargetTop;

    /**
     * 目标View的宽度
     */
    private int mTargetWidth;

    /**
     * 目标View的高度
     */
    private int mTargetHeight;

    /**
     * 事件拦截最小高度
     */
    private int mTouchSlop;

    /**
     * 屏幕高度一半，用于临界点判断
     */
    private int mHalfWindowHeight;

    private PhotoView mPhotoView;

    /**
     * 拦截拖拽的View
     */
    private View mInterceptView;

    /**
     * 当前颜色的透明度
     */
    private float mCurrentColorDrawableAlpha = 1;

    /**
     * 判断是否启用拖拽
     */
    private boolean mEnableDrag = true;

    /**
     * 判断是否处理禁止拦截触摸事件
     */
    private boolean mUseDisallowInterceptTouchEvent = false;

    private OnDragDismissLayoutListener mOnDragDismissLayoutListener;

    public DragDismissLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragDismissLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragDismissLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupLayout(context, attrs);
    }

    private void setupLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mWindowHeight = getScreenHeight();
        mScroller = new Scroller(getContext());
        mHalfWindowHeight = mWindowHeight / 2;
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
        setFitsSystemWindows(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 1) {
            throw new IllegalStateException("DragDismissLayout must have one child!!");
        }
        mContentView = getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mContentView == null) {
            return;
        }
        mContentView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mContentView == null) {
            return;
        }
        int currentTop = mContentView.getTop();
        int currentLeft = mContentView.getLeft();
        mContentView.layout(currentLeft, currentTop, currentLeft + mContentView.getMeasuredWidth(), currentTop + mContentView.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isTouchEvent(event)) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mActionDownY = (int) event.getRawY();
                    mActionDownX = (int) event.getRawX();
                    mLastY = 0;
                    mLastX = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int yDistance = (int) (event.getRawY() - mActionDownY);
                    int xDistance = (int) (event.getRawX() - mActionDownX);
                    if (Math.abs(xDistance) > mTouchSlop || Math.abs(yDistance) > mTouchSlop) {
                        return true;
                    }
                    break;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isTouchEvent = isTouchEvent(event);
        if (isTouchEvent) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mActionDownY = (int) event.getRawY();
                    mActionDownX = (int) event.getRawX();
                    mLastY = 0;
                    mLastX = 0;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (mOnDragDismissLayoutListener != null) {
                        mOnDragDismissLayoutListener.onDragging();
                    }
                    final int currentY = (int) event.getRawY();
                    final int currentX = (int) event.getRawX();
                    final int yDistance = currentY - mActionDownY;
                    final int xDistance = currentX - mActionDownX;
                    int currentTop = mContentView.getTop();
                    if (yDistance < 0 && currentTop <= 0) {
                        //顶点处不可向上滑动
                        return super.onTouchEvent(event);
                    }
                    mContentView.offsetLeftAndRight((int) (xDistance * mResistance));
                    //使用offsetTopAndBottom产生滑动
                    mContentView.offsetTopAndBottom((int) (yDistance * mResistance));
                    doScale(currentTop);
                    setColorDrawable(computeAlpha(currentTop));
                    mActionDownY = currentY;
                    mActionDownX = currentX;
                    if (mUseDisallowInterceptTouchEvent) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    int top = mContentView.getTop();
                    int left = mContentView.getLeft();
                    //到达临界值，开始执行关闭动画
                    if (top >= mWindowHeight * mDragRatio) {
                        dismiss();
                    } else {
                        if (mOnDragDismissLayoutListener != null) {
                            mOnDragDismissLayoutListener.onEndDrag();
                        }
                        //没有到达临界值，返回顶部
                        mScroller.startScroll(0, 0, -left, -top, mAnimDuration);
                        invalidate();
                    }
                    if (mUseDisallowInterceptTouchEvent) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int currentY = mScroller.getCurrY();
            int currentX = mScroller.getCurrX();
            int yDis = currentY - mLastY;
            int xDis = currentX - mLastX;
            mContentView.offsetTopAndBottom(yDis);
            mContentView.offsetLeftAndRight(xDis);
            final int top = mContentView.getTop();
            doScale(top);
            setColorDrawable(computeAlpha(top));
            mLastY = currentY;
            mLastX = currentX;
            invalidate();
        }
    }

    /**
     * 绑定photoView
     *
     * @param view
     */
    public void bindPhotoView(PhotoView view) {
        mPhotoView = view;
    }

    /**
     * 拦截拖拽事件的View
     *
     * @param view
     */
    public void bindInterceptView(View view) {
        mInterceptView = view;
    }

    /**
     * 设置是否启用拖拽
     *
     * @param enableDrag
     * @return
     */
    public DragDismissLayout setEnableDrag(final boolean enableDrag) {
        mEnableDrag = enableDrag;
        return this;
    }

    public void attachTo(Activity activity) {
        if (activity == null) {
            return;
        }
        mActivity = activity;
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        mContentView = decorView.getChildAt(0);
        decorView.removeView(mContentView);
        decorView.addView(this);
        addView(mContentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 设置目标View数据
     *
     * @param x      目标View绝对横坐标
     * @param y      目标View绝对纵坐标
     * @param width  目标View宽度
     * @param height 目标View高度
     */
    public void setTargetData(int x, int y, int width, int height) {
        this.mTargetLeft = x;
        this.mTargetTop = y;
        this.mTargetWidth = width;
        this.mTargetHeight = height;
    }

    public float getDragRatio() {
        return mDragRatio;
    }

    public void setDragRatio(float dragRatio) {
        this.mDragRatio = dragRatio;
    }

    public float getResistance() {
        return mResistance;
    }

    public void setResistance(float resistance) {
        this.mResistance = resistance;
    }

    public int getAnimDuration() {
        return mAnimDuration;
    }

    public void setAnimDuration(int animDuration) {
        this.mAnimDuration = animDuration;
    }

    public void setUseDisallowInterceptTouchEvent(final boolean useDisallowInterceptTouchEvent) {
        mUseDisallowInterceptTouchEvent = useDisallowInterceptTouchEvent;
    }

    public void setDefaultBackground() {
        mColorDrawable = new ColorDrawable(Color.BLACK);
        setBackground(mColorDrawable);
    }

    public void setOnDragDismissLayoutListener(OnDragDismissLayoutListener onDragDismissLayoutListener) {
        mOnDragDismissLayoutListener = onDragDismissLayoutListener;
    }

    /**
     * 计算此时背景透明度
     *
     * @param currentTop
     * @return
     */
    private int computeAlpha(int currentTop) {
        //防止top< 0导致透明度反转
        if (currentTop <= 0) {
            return ALPHA;
        }

        if (currentTop > mHalfWindowHeight) {
            currentTop = mHalfWindowHeight;
        }
        return (int) (ALPHA * 1.0f * (mHalfWindowHeight - currentTop) / mHalfWindowHeight);
    }

    private void doScale(int currentTop) {
        final float scale = 1.0f * (mWindowHeight - currentTop) / mWindowHeight;
        mContentView.setScaleX(scale);
        mContentView.setScaleY(scale);
    }

    private boolean isTouchEvent(MotionEvent event) {
        if (mEnableDrag) {
            if (inRangeOfView(mInterceptView, event)) {
                return false;
            }
            if (mPhotoView != null && mPhotoView.isZoomable()) {
                return event.getPointerCount() == 1;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean inRangeOfView(View view, MotionEvent event) {
        if (view == null || view.getVisibility() == GONE) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (event.getX() < x || event.getX() > (x + view.getWidth()) || event.getY() < y || event.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    private void dismiss() {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }

        if (mOnDragDismissLayoutListener != null) {
            mOnDragDismissLayoutListener.onDismiss();
        }

        int[] location = new int[2];
        mContentView.getLocationOnScreen(location);

        float currentWidth = (mContentView.getMeasuredWidth() * mContentView.getScaleX());
        float currentHeight = (mContentView.getMeasuredHeight() * mContentView.getScaleY());

        float currentCenterX = location[0] + currentWidth / 2.f;
        float currentCenterY = location[1] + currentHeight / 2.f;

        if (mTargetWidth == 0 || mTargetHeight == 0) {
            float starAlpha = mCurrentColorDrawableAlpha;
            ViewAnimator.animate(mContentView)
                    .scale(0.3f)
                    .fadeOut()
                    .translationX(getScreenWidth() / 2.f - currentCenterX)
                    .translationY(getScreenHeight() / 2.f - currentCenterY)
                    .duration(mAnimDuration)
                    .interpolator(new AccelerateDecelerateInterpolator())
                    .onStop(() -> {
                        if (mActivity != null && !mActivity.isFinishing()) {
                            mActivity.finish();
                            mActivity.overridePendingTransition(0, 0);
                        }
                    })
                    .custom((view, value) -> {
                        if (value >= 1) {
                            value = 1f;
                        }
                        if (value <= 0) {
                            value = 0.0f;
                        }
                        setColorDrawable((int) (255 * value));
                    }, starAlpha, 0)
                    .start();
        } else {
            float targetCenterX = mTargetLeft + mTargetWidth / 2.f;
            float targetCenterY = mTargetTop + mTargetHeight / 2.f;

            float currentAspectRatio = getImageAspectRatio(currentWidth, currentHeight);

            if (mPhotoView != null && mPhotoView.getDisplayRect() != null) {
                currentAspectRatio = getImageAspectRatio(mPhotoView.getDisplayRect().width(), mPhotoView.getDisplayRect().height());
            }

            float targetAspectRatio = getImageAspectRatio(mTargetWidth, mTargetHeight);

            float scale;

            if (targetAspectRatio >= currentAspectRatio) {
                scale = mTargetWidth * mContentView.getScaleX() / currentWidth;
            } else {
                scale = mTargetHeight * mContentView.getScaleY() / currentHeight;
            }

            if (mPhotoView != null) {
                scale = mTargetWidth * mContentView.getScaleX() / currentWidth;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mPhotoView.getLayoutParams();
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.width = mContentView.getMeasuredWidth();
                layoutParams.height = (int) (layoutParams.width / targetAspectRatio);

                mPhotoView.setLayoutParams(layoutParams);
                mPhotoView.setScaleType(ScaleType.CENTER_CROP);
                mPhotoView.setDisplayMatrix(new Matrix());
            }

            float starAlpha = mCurrentColorDrawableAlpha;
            ViewAnimator.animate(mContentView)
                    .scale(1, scale)
                    .translationX(targetCenterX - currentCenterX)
                    .translationY(targetCenterY - currentCenterY)
                    .duration(mAnimDuration)
                    .interpolator(new AccelerateDecelerateInterpolator())
                    .onStop(() -> {
                        mContentView.setVisibility(GONE);
                        if (mActivity != null && !mActivity.isFinishing()) {
                            mActivity.finish();
                            mActivity.overridePendingTransition(0, 0);
                        }
                    })
                    .custom((view, value) -> {
                        if (value >= 1) {
                            value = 1f;
                        }
                        if (value <= 0) {
                            value = 0.0f;
                        }
                        setColorDrawable((int) (255 * value));
                    }, starAlpha, 0)
                    .start();
        }
    }

    private float getImageAspectRatio(final float width,
                                      final float height) {
        return width * 1.0f / height;
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return getContext().getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    private int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return getContext().getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    private void setColorDrawable(int alpha) {
        mCurrentColorDrawableAlpha = alpha * 1.0f / 255;
        if (mColorDrawable != null) {
            mColorDrawable.setAlpha(alpha);
        }

        if (mOnDragDismissLayoutListener != null) {
            mOnDragDismissLayoutListener.onComputeAlpha(alpha);
        }
    }

    public interface OnDragDismissLayoutListener {
        /**
         * 计算背景透明度
         *
         * @param alpha
         */
        void onComputeAlpha(int alpha);

        /**
         * 关闭界面
         */
        void onDismiss();

        /**
         * 开始拖拽
         */
        void onDragging();

        /**
         * 结束拖拽
         */
        void onEndDrag();
    }
}
