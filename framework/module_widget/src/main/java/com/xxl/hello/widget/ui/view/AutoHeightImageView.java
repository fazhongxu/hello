package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 高度自适应ImageView，高度始终充满显示区域，宽度成比例缩放
 *
 * @author xxl.
 * @date 2022/12/05.
 */
public class AutoHeightImageView extends AppCompatImageView {

    public AutoHeightImageView(Context context) {
        super(context);
    }

    public AutoHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHeightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int width = drawable.getMinimumWidth();
            int height = drawable.getMinimumHeight();
            float scale = (float) width / height;
            //强制根据图片原有比例，重新计算ImageView显示区域宽度
            int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);
            int widthMeasure = (int) (heightMeasure * scale);

            //并设置为MeasureSpec.EXACTLY精确模式保证之后的super.onMeasure()不再调整
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasure, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}