package com.xxl.kit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

/**
 * @author xxl.
 * @date 2024/9/3.
 */
public class BitmapUtils {

    /**
     * bitmap水平翻转
     *
     * @param original
     * @return
     */
    public static Bitmap mirrorBitmap(Bitmap original) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        Bitmap mirroredBitmap = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, false);
        return mirroredBitmap;
    }

    /**
     * 两张图片融合
     *
     * @param bitmap1
     * @param bitmap2
     * @param progress
     * @returnø
     */
    public static Bitmap blendBitmap(Bitmap bitmap1, Bitmap bitmap2, float progress) {
        // 确保两张图像大小相同
        if (bitmap1.getWidth() != bitmap2.getWidth() || bitmap1.getHeight() != bitmap2.getHeight()) {
            throw new IllegalArgumentException("两张bitmap 的宽高必须一致");
        }

        // 创建结果 Bitmap
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);

        // 计算融合比例
        float alpha = Math.min(Math.max(progress / 100.0f, 0.0f), 1.0f); // 使 alpha 值在0到1之间
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 绘制第一张图像
        canvas.drawBitmap(bitmap1, 0, 0, null);

        // 设置 Paint 的 Xfermode 为融合模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 计算第二张图像的 Alpha 值
        int alphaValue = (int) ((1.0f - alpha) * 255); // alpha 为0时 bitmap2 不透明，为1时 bitmap2 完全透明
        paint.setAlpha(alphaValue);

        // 绘制第二张图像
        canvas.drawBitmap(bitmap2, 0, 0, paint);

        // 清除 Xfermode
        paint.setXfermode(null);

        return resultBitmap;
    }

    /**
     * 羽化
     *
     * @param bitmap
     * @param progress 0 - 100
     * @return
     */
    public static Bitmap featherBitmap(Bitmap bitmap, float progress) {
        // 创建一个新的Bitmap，大小与原Bitmap相同
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // 设置羽化效果的半径，最大值为图片宽度或高度的一半
        float radius = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2 * progress;

        // 创建一个带有羽化效果的Paint
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);

        // 创建一个带有羽化效果的Shader
        RadialGradient gradient = new RadialGradient(
                bitmap.getWidth() / 2,
                bitmap.getHeight() / 2,
                radius,
                new int[]{0xFFFFFFFF, 0x00FFFFFF},
                new float[]{0.0f, 1.0f},
                Shader.TileMode.CLAMP
        );
        paint.setShader(gradient);

        // 绘制羽化效果
        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

        // 使用Xfermode将原Bitmap与羽化效果结合
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return output;
    }

    /**
     * 裁剪图片
     *
     * @param originBitmap
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap cropBitmap(Bitmap originBitmap,
                                    int targetWidth,
                                    int targetHeight) {
        if (originBitmap == null) {
            return null;
        }
        int originWidth = originBitmap.getWidth();
        int originHeight = originBitmap.getHeight();

        int cropWidth = targetWidth;
        int cropHeight = targetHeight;

        int offsetX = (originWidth - cropWidth) / 2;
        int offsetY = (originHeight - cropHeight) / 2;

        cropWidth = Math.min(cropWidth, originWidth);
        cropHeight = Math.min(cropHeight, originHeight);

        Bitmap croppedBitmap = Bitmap.createBitmap(cropWidth, cropHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(croppedBitmap);

        Rect srcRect = new Rect(offsetX, offsetY, offsetX + cropWidth, offsetY + cropHeight);
        RectF destRect = new RectF(0, 0, cropWidth, cropHeight);
        canvas.drawBitmap(originBitmap, srcRect, destRect, null);

        return croppedBitmap;
    }


    /**
     * 裁间图片把图片中间扣出一个透明的矩形
     *
     * @param originBitmap 原图
     * @param gap          透明矩形和原图的边距
     * @param radius       中间镂空矩形圆角
     * @param alpha        剩余图形的透明度
     * @return
     */
    public static Bitmap cropBitmapWithHole(Bitmap originBitmap,
                                            int gap,
                                            int radius,
                                            int alpha) {
        int width = originBitmap.getWidth();
        int height = originBitmap.getHeight();

        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);

        Paint paint = new Paint();
        paint.setAlpha(alpha);

        canvas.drawBitmap(originBitmap, 0, 0, paint);

        Paint paint1 = new Paint();
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        RectF rectF = new RectF(gap, gap, width - gap, height - gap);
        canvas.drawRoundRect(rectF, radius, radius, paint1);

        return resultBitmap;
    }

    /**
     * 合并边框
     *
     * @param border       外边框
     * @param insideBorder 内边框
     * @return
     */
    public static Bitmap mergeBorder(Bitmap border,
                                     Bitmap insideBorder) {
        int width = border.getWidth();
        int height = border.getHeight();
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(insideBorder, 0, 0, null);
        canvas.drawBitmap(border, 0, 0, null);

        return resultBitmap;

    }


    private BitmapUtils() {

    }


}