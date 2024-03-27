package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2024/3/27.
 */
public class GraffitiView extends View {

    private Paint mPaint = new Paint();

    private float mLastX;
    private float mLastY;

    private List<PathStep> mHistory = new ArrayList<>();

    private int mCurrentIndex;

    public GraffitiView(Context context) {
        this(context, null);
    }

    public GraffitiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraffitiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PathStep pathStep = getPath(mCurrentIndex);
    }

    public PathStep getPath(int index) {
        if (mCurrentIndex >= 0 && mCurrentIndex < mHistory.size() && mHistory.size() > 1) {
            return mHistory.get(index);
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        Path path = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                PathStep pathStep = new PathStep();
                path = new Path();
                path.moveTo(x, y);
                mHistory.add(pathStep);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
            default:
        }
        return ret;
    }

    static class PathStep {
        List<Path> mPaths = new ArrayList<>();
    }
}