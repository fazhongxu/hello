package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
     * 画心形
     *
     * @param canvas
     */
    private void drawHeartBitmap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();

        RectF rectF = new RectF(0, 0, getWidth(), getHeight());

        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        float radius = rectF.width() / 2;

        // 移动到顶部中心点
        path.moveTo(centerX, centerY - radius * 0.75F);
        // 左半边心 从顶部画到底部 (x1,y1) 为控制点1坐标;(x2,y2) 为控制点2坐标;(x3,y3) 为终点坐标;
        path.cubicTo(centerX - radius * 0.75F, centerY - radius * 1.2F, centerX - radius * 1.5F, centerY, centerX, centerY + radius * 0.85F);

        // 移动到顶部中心点
        path.moveTo(centerX, centerY - radius * 0.75F);
        // 右半边心
        path.cubicTo(centerX + radius * 0.75F, centerY - radius * 1.2F, centerX + radius * 1.5F, centerY, centerX, centerY + radius * 0.85F);

        // 左半边心控制点，为了方便看控制点位置，方便调整形状
        canvas.drawCircle(centerX - radius * 0.75F, centerY - radius * 1.2F, 10, paint);
        canvas.drawCircle(centerX - radius * 1.5F, centerY, 10, paint);

        // 右半边心控制点
        canvas.drawCircle(centerX + radius * 0.75F, centerY - radius * 1.2F, 10, paint);
        canvas.drawCircle(centerX + radius * 1.5F, centerY, 10, paint);

        canvas.drawPath(path, paint);
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

        RectF rectF = new RectF(0, 0, getWidth(), getHeight());

        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        float radius = rectF.width() / 2;

        // 左边水滴
        path.moveTo(centerX, centerY + radius *0.9F);
        path.cubicTo(centerX - radius * 0.7F, centerY + radius*0.9F, centerX - radius * 1.5F, centerY, centerX, centerY - radius * 0.9F);

        // 右边水滴
        path.moveTo(centerX, centerY + radius *0.9F);
        path.cubicTo(centerX + radius * 0.7F, centerY + radius*0.9F, centerX + radius * 1.5F, centerY, centerX, centerY - radius * 0.9F);

//        canvas.drawCircle(centerX, centerY + radius *0.9F,10,paint);
        canvas.drawCircle(centerX - radius * 0.7F, centerY + radius*0.8F,10,paint);
        canvas.drawCircle(centerX - radius * 1.5F, centerY,10,paint);

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