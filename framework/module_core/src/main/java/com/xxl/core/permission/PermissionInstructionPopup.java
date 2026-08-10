package com.xxl.core.permission;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.xxl.core.R;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * 权限使用说明弹窗：顶部下拉显示，请求权限期间向用户说明权限用途
 *
 * @author xxl.
 * @date 2024/08/10.
 */
public class PermissionInstructionPopup extends BasePopupWindow {

    private static final long ANIMATION_DURATION = 200L;

    private TextView mTvTitle;
    private TextView mTvDes;

    //region: 构造函数

    public PermissionInstructionPopup(Activity activity) {
        super(activity);
        setPopupGravity(Gravity.TOP);
        setBackgroundColor(Color.TRANSPARENT);
        setupLayout();
    }

    public static PermissionInstructionPopup from(Activity activity) {
        return new PermissionInstructionPopup(activity);
    }

    //endregion

    //region: 页面视图渲染

    private void setupLayout() {
        View rootView = createPopupById(R.layout.core_layout_permission_usage_instruction);
        mTvTitle = rootView.findViewById(R.id.tv_title);
        mTvDes = rootView.findViewById(R.id.tv_des);
        setContentView(rootView);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        Animation animation = ((AnimationHelper.AnimationBuilder) AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.FROM_TOP)).toShow();
        animation.setDuration(ANIMATION_DURATION);
        return animation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        Animation animation = ((AnimationHelper.AnimationBuilder) AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.TO_TOP)).toDismiss();
        animation.setDuration(ANIMATION_DURATION);
        return animation;
    }

    //endregion

    //region: 提供方法

    /**
     * 设置权限说明标题
     *
     * @param title
     * @return
     */
    public PermissionInstructionPopup setTitle(CharSequence title) {
        mTvTitle.setText(title);
        return this;
    }

    /**
     * 设置权限说明描述
     *
     * @param message
     * @return
     */
    public PermissionInstructionPopup setMessage(CharSequence message) {
        mTvDes.setText(message);
        return this;
    }

    //endregion
}
