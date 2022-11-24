package com.xxl.core.widget.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * SpannableString 使用的时候，居中文字和图片的ImageSpan
 *
 * @author xxl.
 * @date 2022/08/02.
 */
public class CenterImageSpan extends ImageSpan {

    //region: 成员变量

    /**
     * 图片和文字的右边边距
     */
    private int mMarginRight = 0;

    /**
     * 图片和文字的左边边距
     */
    private int mMarginLeft = 0;

    //endregion

    //region: 构造函数

    public CenterImageSpan(@NonNull Bitmap bitmap) {
        super(bitmap);
    }

    public CenterImageSpan(@NonNull Drawable drawable) {
        super(drawable);
    }

    public CenterImageSpan(@NonNull Drawable drawable,
                           final int marginLeft,
                           final int marginRight) {
        super(drawable);
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;
    }

    //endregion

    //region: 生命周期方法

    @Override
    public void draw(@NonNull final Canvas canvas,
                     @NonNull final CharSequence text,
                     final int start,
                     final int end,
                     final float x,
                     final int top,
                     final int y,
                     final int bottom,
                     @NonNull final Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        //计算y方向的位移
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;
        canvas.save();
        //绘制图片位移一段距离
        canvas.translate(x + mMarginLeft, transY);
        b.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(@NonNull final Paint paint,
                       @NonNull final CharSequence text,
                       final int start,
                       final int end,
                       @Nullable final Paint.FontMetricsInt fm) {
        return mMarginLeft + super.getSize(paint, text, start, end, fm) + mMarginRight;
    }

    //endregion
}
