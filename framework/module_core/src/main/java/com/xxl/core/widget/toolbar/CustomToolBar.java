package com.xxl.core.widget.toolbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.xxl.core.R;
import com.xxl.kit.ResourceUtils;

/**
 * 自定义Toolbar
 *
 * @author xxl.
 * @date 2021/10/11.
 */
public class CustomToolBar extends Toolbar implements OnToolbarProvider {

    //region: 成员变量

    /**
     * 标题
     */
    private TextView mTvTitle;

    /**
     * 左边视图
     */
    private LinearLayout mLeftContainer;

    /**
     * 右边视图
     */
    private LinearLayout mRightContainer;

    //endregion

    //region: 构造函数

    public CustomToolBar(@NonNull Context context) {
        this(context, null);
    }

    public CustomToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupLayout(context);
    }

    //endregion

    //region: 页面视图渲染

    private void setupLayout(@NonNull final Context context) {
        inflate(context, getLayoutRes(), this);
        mTvTitle = findViewById(R.id.tv_title);

        mTvTitle.setTextColor(getNavigationTitleColor());
    }

    //endregion

    //region: 提供方法

    /**
     * 获取要加载的布局
     *
     * @return
     */
    public int getLayoutRes() {
        return R.layout.core_layout_custom_toolbar;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setToolbarTitle(@NonNull CharSequence title) {
        mTvTitle.setText(title);
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    @Override
    public void setToolbarTitle(int resId) {
        mTvTitle.setText(resId);
    }

    /**
     * 设置导航栏左边视图
     *
     * @param leftText
     * @param onClickListener
     */
    public void setupToolbarLeftLayout(CharSequence leftText,
                                       View.OnClickListener onClickListener,
                                       View.OnLongClickListener onLongClickListener) {
        setupToolbarLeftLayout(true, true, getNavigationLeftIconRes(), leftText, onClickListener, onLongClickListener);
    }

    /**
     * 设置导航栏右边视图
     *
     * @param rightText
     * @param onClickListener
     */
    public void setupToolbarRightLayout(CharSequence rightText,
                                        int rightIcon,
                                        View.OnClickListener onClickListener,
                                        View.OnLongClickListener onLongClickListener) {
        Drawable rightDrawable = null;
        try {
            if (rightIcon > 0) {
                rightDrawable = ResourceUtils.getDrawable(rightIcon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupToolbarRightLayout(rightIcon > 0, !TextUtils.isEmpty(rightText), rightDrawable, rightText, onClickListener, onLongClickListener);
    }

    /**
     * 获取导航栏左边视图
     *
     * @return
     */
    public LinearLayout getLeftContainer() {
        return mLeftContainer;
    }

    /**
     * 获取导航栏右边视图
     *
     * @return
     */
    public LinearLayout getRightContainer() {
        return mRightContainer;
    }

    /**
     * 获取导航栏左边图标
     *
     * @return
     */
    public Drawable getNavigationLeftIconRes() {
        return ResourceUtils.getAttrDrawable(getContext(), R.attr.toolbarLeftIcon);
    }

    /**
     * 获取导航栏标题颜色
     *
     * @return
     */
    public int getNavigationTitleColor() {
        return ResourceUtils.getAttrColor(getContext(), R.attr.toolbarTitleColor);
    }

    /**
     * 获取导航栏右边文本颜色
     *
     * @return
     */
    public int getNavigationRightTextColor() {
        return ResourceUtils.getAttrColor(getContext(), R.attr.toolbarRightTextColor);
    }

    /**
     * 设置导航栏左边视图
     *
     * @param isDisplayLeftIcon
     * @param isDisplayLeftText
     * @param leftIcon
     * @param leftText
     * @param onClickListener
     */
    public void setupToolbarLeftLayout(boolean isDisplayLeftIcon,
                                       boolean isDisplayLeftText,
                                       Drawable leftIcon,
                                       CharSequence leftText,
                                       View.OnClickListener onClickListener,
                                       View.OnLongClickListener onLongClickListener) {
        LayoutInflater.from(getContext()).inflate(R.layout.core_layout_include_toolbar_left, this);
        mLeftContainer = findViewById(R.id.ll_left_container);
        ImageView ivLeftIcon = findViewById(R.id.iv_left_icon);
        TextView tvLeftText = findViewById(R.id.tv_left_text);
        if (leftIcon != null) {
            ivLeftIcon.setImageDrawable(leftIcon);
        }
        if (leftText != null) {
            tvLeftText.setText(leftText);
        }
        ivLeftIcon.setVisibility(isDisplayLeftIcon ? View.VISIBLE : View.GONE);
        tvLeftText.setVisibility(isDisplayLeftText ? View.VISIBLE : View.GONE);
        if (mLeftContainer != null) {
            mLeftContainer.setOnClickListener(onClickListener);
            mLeftContainer.setOnLongClickListener(onLongClickListener);
        }
    }


    /**
     * 设置导航栏左边视图
     *
     * @param isDisplayRightIcon
     * @param isDisplayRightText
     * @param rightIcon
     * @param rightText
     * @param onClickListener
     */
    public void setupToolbarRightLayout(boolean isDisplayRightIcon,
                                        boolean isDisplayRightText,
                                        Drawable rightIcon,
                                        CharSequence rightText,
                                        View.OnClickListener onClickListener,
                                        View.OnLongClickListener onLongClickListener) {
        LayoutInflater.from(getContext()).inflate(R.layout.core_layout_include_toolbar_right, this);
        mRightContainer = findViewById(R.id.ll_right_container);
        ImageView ivRightIcon = findViewById(R.id.iv_right_icon);
        TextView tvRightText = findViewById(R.id.tv_right_text);
        if (rightIcon != null) {
            ivRightIcon.setImageDrawable(rightIcon);
        }
        if (rightText != null) {
            tvRightText.setText(rightText);
        }
        ivRightIcon.setVisibility(isDisplayRightIcon ? View.VISIBLE : View.GONE);
        tvRightText.setVisibility(isDisplayRightText ? View.VISIBLE : View.GONE);
        if (mRightContainer != null) {
            mRightContainer.setOnClickListener(onClickListener);
            mRightContainer.setOnLongClickListener(onLongClickListener);
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}