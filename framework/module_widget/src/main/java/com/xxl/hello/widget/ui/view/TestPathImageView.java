package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
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
        drawWaterBitmap(canvas);
    }

    /**
     * 画水滴
     *
     * @param canvas
     */
    private void drawWaterBitmap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();

        int width = getWidth();
        int height = getHeight();

        RectF rectF = new RectF(0, 0, width, height);
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();

        PointF controlPointOne = new PointF();
        PointF controlPointTwo = new PointF();

        controlPointOne.x = centerX - width * 0.5F;
        controlPointOne.y = centerY + height * 0.5F;

        controlPointTwo.x = centerX - width * 0.75F;
        controlPointTwo.y = centerY;

        PointF controlPointThree = new PointF();
        PointF controlPointFour = new PointF();

        controlPointThree.x = centerX + width * 0.5F;
        controlPointThree.y = centerY + height * 0.5F;

        controlPointFour.x = centerX + width * 0.75F;
        controlPointFour.y = centerY;

        path.moveTo(centerX, height);
        path.cubicTo(controlPointOne.x, controlPointOne.y, controlPointTwo.x, controlPointTwo.y, centerX, 10);

        path.moveTo(centerX, height);
        path.cubicTo(controlPointThree.x, controlPointThree.y, controlPointFour.x, controlPointFour.y, centerX, 10);

        canvas.drawCircle(controlPointOne.x, controlPointOne.y, 10, paint);
        canvas.drawCircle(controlPointTwo.x, controlPointTwo.y, 10, paint);
        canvas.drawPath(path, paint);
    }

    /**
     * 画椭圆
     *
     * @param canvas
     */
    private void drawOvalBitmap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);

        Path path = new Path();

        int width = getWidth();
        int height = getHeight();

        //RectF rectF = new RectF(0,0,width,height); 椭圆的外切矩形区域，如果外部是正方形，这个椭圆就是圆了
        RectF rectF = new RectF(0, 0, width, height * 0.8F);
        path.addOval(rectF, Path.Direction.CW);

        // 让画的区域变成外部的，比如内部画了一个椭圆，设置这个之后，变成中间扣了一个椭圆
        //path.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        // 大圆的一半高度-椭圆的上下高度，等于要平移的距离
        float offsetY = height / 2 - rectF.centerY();
        path.offset(0, offsetY);

        canvas.drawPath(path, paint);
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
        paint.setStyle(Paint.Style.FILL);

        int width = getWidth();
        int height = getHeight();

        Path path = new Path();

        path.moveTo(width / 2, paint.getStrokeWidth());

        path.lineTo(0, height - paint.getStrokeWidth());

        path.lineTo(width - paint.getStrokeWidth(), height - paint.getStrokeWidth());
        path.close();

        canvas.drawPath(path, paint);
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
        path.lineTo(100, 200);

        canvas.drawPath(path, paint);
    }

}