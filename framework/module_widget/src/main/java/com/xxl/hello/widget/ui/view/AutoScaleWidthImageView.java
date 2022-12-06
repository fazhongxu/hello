package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.xxl.kit.DisplayUtils;

/**
 * 宽度自适应ImageView，宽度始终充满显示区域，高度成比例缩放
 *
 * @author xxl.
 * @date 2022/12/05.
 */
public class AutoScaleWidthImageView extends AppCompatImageView {

    /**
     * 最大高度
     */
    private int mMaxHeight = DisplayUtils.dp2px(getContext(), 0);

    public AutoScaleWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Drawable drawable = getDrawable();
        if (drawable != null) {
            int width = drawable.getMinimumWidth();
            int height = drawable.getMinimumHeight();
            float scale = (float) height / width;
            int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
            int heightMeasure = (int) (widthMeasure * scale);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight > 0 ? Math.min(mMaxHeight, heightMeasure) : heightMeasure, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置最大高度
     *
     * @param maxHeight
     */
    public void setScaleMaxHeight(final int maxHeight) {
        mMaxHeight = maxHeight;
    }
}