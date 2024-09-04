package com.xxl.kit;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;

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
     * bitmap羽化
     *
     * @param sourceBitmap
     * @param progress
     * @return
     */
    public static Bitmap applyFeatherEffect(Bitmap sourceBitmap, int progress) {
        if (sourceBitmap == null || progress < 0 || progress > 100) {
            return sourceBitmap; // 返回原始 Bitmap
        }

        // 羽化程度，范围为 0 到 1
        //float featherAmount = progress / 100f; // 正常的羽化程度
        // 1 - 0 // 反转羽化程度
        float featherAmount = 1 - (progress / 100f);

        Bitmap outputBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outputBitmap);
        Paint paint = new Paint();

        // 计算羽化半径，取决于羽化程度和最大半径
        float maxRadius = Math.max(sourceBitmap.getWidth(), sourceBitmap.getHeight()) * 0.5f;
        float featherRadius = featherAmount * maxRadius;

        RadialGradient gradient = new RadialGradient(
                sourceBitmap.getWidth() / 2f,
                sourceBitmap.getHeight() / 2f,
                featherRadius,
                0x00FFFFFF,
                0xFFFFFFFF,
                Shader.TileMode.CLAMP);

        // 设置渐变作为画笔的遮罩
        paint.setShader(gradient);

        // 绘制源 Bitmap
        canvas.drawBitmap(sourceBitmap, 0, 0, null);

        // 在源 Bitmap 上绘制羽化效果
        canvas.drawRect(0, 0, outputBitmap.getWidth(), outputBitmap.getHeight(), paint);

        return outputBitmap;
    }


    private BitmapUtils() {

    }


}