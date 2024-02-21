package com.xxl.core.image.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 亮度调节
 */
public class BrightnessTransformation extends BitmapTransformation {

    private static final String ID = "com.xxl.core.image.transform.BrightnessTransformation";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    /**
     * 0-1 1最亮，0最暗
     */
    private final float brightness;

    public BrightnessTransformation(Context context, float brightness) {
        super();
        this.brightness = brightness;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return adjustBrightness(toTransform, brightness);
    }

    public Bitmap adjustBrightness(Bitmap bitmap, float brightness) {

        // 将0-1的亮度值映射到-255-0范围
        float adjustedBrightness = brightness * 255f - 255;

        Bitmap adjustedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        float[] matrix = new float[]{
                1, 0, 0, 0, adjustedBrightness,
                0, 1, 0, 0, adjustedBrightness,
                0, 0, 1, 0, adjustedBrightness,
                0, 0, 0, 1, 0
        };

        ColorMatrix colorMatrix = new ColorMatrix(matrix);

        Paint paint = new Paint();
        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorFilter);

        Canvas canvas = new Canvas(adjustedBitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return adjustedBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

}