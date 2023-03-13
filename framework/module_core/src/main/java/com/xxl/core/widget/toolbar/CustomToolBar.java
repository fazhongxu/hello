package com.xxl.core.widget.toolbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
                                       View.OnClickListener onClickListener) {
        setupToolbarLeftLayout(true, true, getNavigationLeftIconRes(), leftText, onClickListener);
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
                                       View.OnClickListener onClickListener) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.core_layout_include_toolbar_left, this);
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
        view.setOnClickListener(onClickListener);

    }

    //endregion

    //region: 内部辅助方法

    //endregion

}