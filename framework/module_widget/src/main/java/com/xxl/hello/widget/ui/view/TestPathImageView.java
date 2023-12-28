package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author xxl.
 * @date 2023/12/28.
 */
public class TestPathImageView extends AppCompatImageView {

    public TestPathImageView(@NonNull Context context) {
        super(context);
    }

    public TestPathImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestPathImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangleBitmap(canvas);
    }

    /**
     * 画一个三角形
     *
     * @param canvas
     */
    private void drawTriangleBitmap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        int width = getWidth();
        int height = getHeight();

        Path path = new Path();

        path.moveTo(width/2,paint.getStrokeWidth());

        path.lineTo(0,height-paint.getStrokeWidth());

        path.lineTo(width-paint.getStrokeWidth(),height-paint.getStrokeWidth());
        path.close();

        canvas.drawPath(path,paint);
    }

    /**
     * 画线条
     *
     * @param canvas
     */
    private void drawLineBitmap(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();

        // 第一个点的坐标
        path.lineTo(100, 100);
        // 第二个点的坐标
        path.lineTo(100,200);

        canvas.drawPath(path, paint);
    }

}