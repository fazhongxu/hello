package com.xxl.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.xxl.core.R;

/**
 * 边缘阴影(透明渐变)效果的view
 *
 * @author xxl.
 * @date 2022/10/26.
 */
public class FadeScrollView extends NestedScrollView {

    //渐变从0-1.0
    private static final boolean DEFAULT_FADING_EDGE_ENABLED = false;
    private static final float DEFAULT_FADING_EDGE_STRENGTH = 0.9f;

    private boolean horizontalFadeEdgeEnabled = DEFAULT_FADING_EDGE_ENABLED;
    private boolean verticalFadeEdgeEnabled = DEFAULT_FADING_EDGE_ENABLED;
    private float topFadeEdgeStrength = DEFAULT_FADING_EDGE_STRENGTH;
    private float bottomFadeEdgeStrength = DEFAULT_FADING_EDGE_STRENGTH;
    private float leftFadeEdgeStrength = DEFAULT_FADING_EDGE_STRENGTH;
    private float rightFadeEdgeStrength = DEFAULT_FADING_EDGE_STRENGTH;

    public FadeScrollView(@NonNull Context context) {
        this(context, null);
    }

    public FadeScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FadeScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FadeScrollView, defStyleAttr, 0);
        horizontalFadeEdgeEnabled = array.getBoolean(R.styleable.FadeScrollView_horizontalFadingEdgeEnabled, horizontalFadeEdgeEnabled);
        verticalFadeEdgeEnabled = array.getBoolean(R.styleable.FadeScrollView_verticalFadingEdgeEnabled, verticalFadeEdgeEnabled);
        topFadeEdgeStrength = array.getFloat(R.styleable.FadeScrollView_topFadingEdgeStrength, topFadeEdgeStrength);
        bottomFadeEdgeStrength = array.getFloat(R.styleable.FadeScrollView_bottomFadingEdgeStrength, bottomFadeEdgeStrength);
        leftFadeEdgeStrength = array.getFloat(R.styleable.FadeScrollView_leftFadingEdgeStrength, leftFadeEdgeStrength);
        rightFadeEdgeStrength = array.getFloat(R.styleable.FadeScrollView_rightFadingEdgeStrength, rightFadeEdgeStrength);

        array.recycle();

        setVerticalFadingEdgeEnabled(horizontalFadeEdgeEnabled);
        setHorizontalFadingEdgeEnabled(horizontalFadeEdgeEnabled);

    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return getFadingEdgeStrength(verticalFadeEdgeEnabled, topFadeEdgeStrength);
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return getFadingEdgeStrength(verticalFadeEdgeEnabled, bottomFadeEdgeStrength);
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return getFadingEdgeStrength(horizontalFadeEdgeEnabled, leftFadeEdgeStrength);
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return getFadingEdgeStrength(horizontalFadeEdgeEnabled, rightFadeEdgeStrength);
    }

    private float getFadingEdgeStrength(Boolean fadeEdgeEnabled, Float fadeEdgeStrength) {
        if (fadeEdgeEnabled) {
            if (fadeEdgeStrength <= 0) {
                return 0F;
            }
            if (fadeEdgeStrength >= 1) {
                return 1F;
            }
            return fadeEdgeStrength;
        }
        return 0F;
    }
}