package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.xxl.hello.widget.R;
import com.xxl.kit.ImageUtils;

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
        drawBitmap(canvas);
    }

    /**
     * 画图片
     * https://blog.csdn.net/hanxiongwei/article/details/103686476
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        Bitmap bitmap = ImageUtils.getBitmap(R.drawable.resources_ic_app_white_logo);
        // 绘制一个left 左边偏移量 top 上边偏移量
        //canvas.drawBitmap(bitmap, 10, 10, paint);

        Matrix matrix = new Matrix();
        //preTranslate 可以叠加，如果不用pre 使用 setTranslate 则效果不叠加
        //matrix.preTranslate(10, 10);
        //matrix.setRotate(45);
        //canvas.drawBitmap(bitmap, matrix, null);

        // 重新指定bitmap宽高
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, getWidth() / 2, getHeight() / 2, false);
        Rect rect = new Rect(0,0,getWidth()/2,getHeight()/2);
        // 旋转45度 以bitmap中心坐标为原点
        matrix.preRotate(45,rect.centerX(),rect.centerY());
        // 缩小 以bitmap中心坐标为原点
        matrix.preScale(0.6F,0.6F,rect.centerX(),rect.centerY());
        canvas.drawBitmap(scaledBitmap, matrix, null);

        // src 要裁剪的bitmap的区域（需要裁剪原图的某个区域，比如左上角，宽高），null则表示需要绘制整个图片
        // dst 表示需要绘制bitmap的矩形区域
        // 把这个图片画到右上角4分之1区域内（避免和下面的遮挡，留10像素的间隔区域）
        canvas.drawBitmap(bitmap, null, new Rect(getWidth() / 2, 10, getWidth() - 10, (getHeight() / 2) - 10), null);

        // 先裁剪再展示 取图片的左上角4分之1区域，绘制到右下角4分之1区域
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2), new Rect(getWidth() / 2, getHeight() / 2, getWidth(), getHeight()), null);

        // 裁剪再展示 取图片4分之1中间区域，绘制到左下角4分之1区域
        int cropBitmapWidth = bitmap.getWidth() / 4;

        Rect srcRect = new Rect(bitmap.getWidth() / 2 - cropBitmapWidth, bitmap.getHeight() / 2 - cropBitmapWidth, bitmap.getWidth() / 2 + cropBitmapWidth, bitmap.getHeight() / 2 + cropBitmapWidth);
        Rect dstRect = new Rect(0, getHeight() / 2, getWidth() / 2 - 10, getHeight() - 10);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
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
        path.moveTo(centerX, centerY + radius * 0.9F);
        path.cubicTo(centerX - radius * 0.7F, centerY + radius * 0.9F, centerX - radius * 1.5F, centerY, centerX, centerY - radius * 0.9F);

        // 右边水滴
        path.moveTo(centerX, centerY + radius * 0.9F);
        path.cubicTo(centerX + radius * 0.7F, centerY + radius * 0.9F, centerX + radius * 1.5F, centerY, centerX, centerY - radius * 0.9F);

//        canvas.drawCircle(centerX, centerY + radius *0.9F,10,paint);
        canvas.drawCircle(centerX - radius * 0.7F, centerY + radius * 0.8F, 10, paint);
        canvas.drawCircle(centerX - radius * 1.5F, centerY, 10, paint);

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