package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义加粗字体粗细比系统的细的TextView
 *
 * @author xxl.
 * @date 2022/10/9.
 */
public class CustomBoldTextView extends AppCompatTextView {

    public CustomBoldTextView(@NonNull Context context) {
        this(context, null);
    }

    public CustomBoldTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBoldTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        getPaint().setStrokeWidth(0.75F);
    }
}