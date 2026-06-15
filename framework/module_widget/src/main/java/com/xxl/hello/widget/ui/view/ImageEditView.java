package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 图片编辑视图 - 支持矩形圈选和自由涂抹功能
 *
 * @author xxl
 * @date 2026/06/15
 */
public class ImageEditView extends View {

    /**
     * 编辑模式
     */
    public interface EditMode {
        int NONE = 0;
        int RECT_SELECT = 1;
        int BRUSH = 2;
    }

    /**
     * 绘制步骤
     */
    private static class DrawStep {
        int type;
        Path path;
        RectF rect;

        DrawStep(int type) {
            this.type = type;
        }
    }

    // 绘图相关
    private Paint mBitmapPaint;
    private Paint mRectPaint;
    private Paint mBrushPaint;
    private Paint mMaskPaint;

    // 图片相关
    private Bitmap mSourceBitmap;
    private Bitmap mDisplayBitmap;
    private Rect mDisplayRect;

    // 编辑状态
    private int mCurrentMode = EditMode.RECT_SELECT;
    private float mBrushSize = 30;

    // 矩形选择
    private float mRectStartX, mRectStartY;
    private float mRectEndX, mRectEndY;
    private RectF mCurrentRect;

    // 涂抹路径
    private Path mCurrentPath;
    private float mLastX, mLastY;

    // 历史记录
    private List<DrawStep> mDrawSteps = new ArrayList<>();
    private Stack<DrawStep> mRedoStack = new Stack<>();

    // 回调接口
    private OnEditListener mOnEditListener;

    public ImageEditView(Context context) {
        this(context, null);
    }

    public ImageEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setColor(Color.parseColor("#66E61919"));
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setStrokeWidth(2);

        mBrushPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBrushPaint.setColor(Color.parseColor("#66E61919"));
        mBrushPaint.setStyle(Paint.Style.STROKE);
        mBrushPaint.setStrokeWidth(mBrushSize);
        mBrushPaint.setStrokeCap(Paint.Cap.ROUND);
        mBrushPaint.setStrokeJoin(Paint.Join.ROUND);

        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskPaint.setColor(Color.parseColor("#80E61919"));
        mMaskPaint.setStyle(Paint.Style.FILL);
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mCurrentRect = new RectF();
        mCurrentPath = new Path();
        mDisplayRect = new Rect();
    }

    /**
     * 设置图片
     */
    public void setImageBitmap(@Nullable Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            mSourceBitmap = bitmap;
            mDisplayBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            calculateDisplayRect();
            invalidate();
        }
    }

    /**
     * 设置图片 URI
     */
    public void setImageUri(@NonNull Context context, @NonNull Uri uri) {
        try {
            Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算显示区域
     */
    private void calculateDisplayRect() {
        if (mSourceBitmap == null) return;

        int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        int bitmapWidth = mSourceBitmap.getWidth();
        int bitmapHeight = mSourceBitmap.getHeight();

        float scale = Math.min((float) viewWidth / bitmapWidth, (float) viewHeight / bitmapHeight);
        float scaledWidth = bitmapWidth * scale;
        float scaledHeight = bitmapHeight * scale;

        float left = getPaddingLeft() + (viewWidth - scaledWidth) / 2f;
        float top = getPaddingTop() + (viewHeight - scaledHeight) / 2f;

        mDisplayRect.set((int) left, (int) top, (int) (left + scaledWidth), (int) (top + scaledHeight));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDisplayRect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDisplayBitmap != null && !mDisplayRect.isEmpty()) {
            // 使用目标区域绘制缩放后的图片
            canvas.drawBitmap(mDisplayBitmap, null, mDisplayRect, mBitmapPaint);
        }

        drawEditLayer(canvas);
    }

    /**
     * 绘制编辑层
     */
    private void drawEditLayer(Canvas canvas) {
        for (DrawStep step : mDrawSteps) {
            if (step.type == EditMode.RECT_SELECT && step.rect != null) {
                drawRectSelect(canvas, step.rect);
            } else if (step.type == EditMode.BRUSH && step.path != null) {
                drawBrushStroke(canvas, step.path);
            }
        }

        if (mCurrentMode == EditMode.RECT_SELECT && mCurrentRect.width() > 0) {
            drawRectSelect(canvas, mCurrentRect);
        } else if (mCurrentMode == EditMode.BRUSH && !mCurrentPath.isEmpty()) {
            drawBrushStroke(canvas, mCurrentPath);
        }
    }

    /**
     * 绘制矩形选择
     */
    private void drawRectSelect(Canvas canvas, RectF rect) {
        canvas.drawRect(rect, mRectPaint);
    }

    /**
     * 绘制涂抹路径
     */
    private void drawBrushStroke(Canvas canvas, Path path) {
        canvas.drawPath(path, mBrushPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDisplayBitmap == null || mDisplayRect.isEmpty()) return false;

        float x = event.getX();
        float y = event.getY();

        // 只在图片区域内响应触摸事件
        if (!mDisplayRect.contains((int) x, (int) y)) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(x, y);
                return true;

            case MotionEvent.ACTION_MOVE:
                handleActionMove(x, y);
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handleActionUp();
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 处理按下事件
     */
    private void handleActionDown(float x, float y) {
        if (mCurrentMode == EditMode.RECT_SELECT) {
            mRectStartX = x;
            mRectStartY = y;
            mRectEndX = x;
            mRectEndY = y;
            mCurrentRect.set(x, y, x, y);
        } else if (mCurrentMode == EditMode.BRUSH) {
            mLastX = x;
            mLastY = y;
            mCurrentPath.reset();
            mCurrentPath.moveTo(x, y);
        }
    }

    /**
     * 处理移动事件
     */
    private void handleActionMove(float x, float y) {
        if (mCurrentMode == EditMode.RECT_SELECT) {
            mRectEndX = x;
            mRectEndY = y;
            mCurrentRect.set(
                    Math.min(mRectStartX, mRectEndX),
                    Math.min(mRectStartY, mRectEndY),
                    Math.max(mRectStartX, mRectEndX),
                    Math.max(mRectStartY, mRectEndY)
            );
        } else if (mCurrentMode == EditMode.BRUSH) {
            mCurrentPath.lineTo(x, y);
            mLastX = x;
            mLastY = y;
        }
    }

    /**
     * 处理抬起事件
     */
    private void handleActionUp() {
        if (mCurrentMode == EditMode.RECT_SELECT && mCurrentRect.width() > 5 && mCurrentRect.height() > 5) {
            DrawStep step = new DrawStep(EditMode.RECT_SELECT);
            step.rect = new RectF(mCurrentRect);
            mDrawSteps.add(step);
            mRedoStack.clear();
            notifyEditChange();
        } else if (mCurrentMode == EditMode.BRUSH && !mCurrentPath.isEmpty()) {
            DrawStep step = new DrawStep(EditMode.BRUSH);
            step.path = new Path(mCurrentPath);
            mDrawSteps.add(step);
            mRedoStack.clear();
            mCurrentPath.reset();
            notifyEditChange();
        }

        mCurrentRect.setEmpty();
    }

    /**
     * 设置编辑模式
     */
    public void setEditMode(int mode) {
        this.mCurrentMode = mode;
    }

    /**
     * 设置画笔大小
     */
    public void setBrushSize(float size) {
        this.mBrushSize = size;
        mBrushPaint.setStrokeWidth(size);
    }

    /**
     * 撤销
     */
    public void undo() {
        if (!mDrawSteps.isEmpty()) {
            DrawStep step = mDrawSteps.remove(mDrawSteps.size() - 1);
            mRedoStack.push(step);
            invalidate();
            notifyEditChange();
        }
    }

    /**
     * 重做
     */
    public void redo() {
        if (!mRedoStack.isEmpty()) {
            DrawStep step = mRedoStack.pop();
            mDrawSteps.add(step);
            invalidate();
            notifyEditChange();
        }
    }

    /**
     * 清除所有
     */
    public void clear() {
        mDrawSteps.clear();
        mRedoStack.clear();
        mCurrentPath.reset();
        mCurrentRect.setEmpty();
        invalidate();
        notifyEditChange();
    }

    /**
     * 是否可以撤销
     */
    public boolean canUndo() {
        return !mDrawSteps.isEmpty();
    }

    /**
     * 是否可以重做
     */
    public boolean canRedo() {
        return !mRedoStack.isEmpty();
    }

    /**
     * 获取编辑后的图片（包含所有绘制内容）
     */
    @Nullable
    public Bitmap getEditedBitmap() {
        if (mDisplayBitmap == null) return null;

        Bitmap result = mDisplayBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(result);

        for (DrawStep step : mDrawSteps) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.parseColor("#80FF0000"));
            paint.setStyle(Paint.Style.FILL);

            if (step.type == EditMode.RECT_SELECT && step.rect != null) {
                canvas.drawRect(step.rect, paint);
            } else if (step.type == EditMode.BRUSH && step.path != null) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(mBrushSize);
                paint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawPath(step.path, paint);
            }
        }

        return result;
    }

    /**
     * 获取选中的矩形区域
     */
    @Nullable
    public RectF getSelectedRect() {
        if (mCurrentRect.width() > 0 && mCurrentRect.height() > 0) {
            return new RectF(mCurrentRect);
        }
        return null;
    }

    /**
     * 设置编辑监听器
     */
    public void setOnEditListener(@Nullable OnEditListener listener) {
        this.mOnEditListener = listener;
    }

    /**
     * 通知编辑状态变化
     */
    private void notifyEditChange() {
        if (mOnEditListener != null) {
            mOnEditListener.onEditChanged(canUndo(), canRedo());
        }
    }

    /**
     * 编辑状态监听器
     */
    public interface OnEditListener {
        void onEditChanged(boolean canUndo, boolean canRedo);
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mSourceBitmap != null && !mSourceBitmap.isRecycled()) {
            mSourceBitmap.recycle();
            mSourceBitmap = null;
        }
        if (mDisplayBitmap != null && !mDisplayBitmap.isRecycled()) {
            mDisplayBitmap.recycle();
            mDisplayBitmap = null;
        }
        mDrawSteps.clear();
        mRedoStack.clear();
    }
}
