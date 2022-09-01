package com.xxl.hello.widget.ui.view.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.widget.R;
import com.xxl.kit.KeyboardWrapper;
import com.xxl.kit.StringUtils;

/**
 * 评论输入布局
 *
 * @author xxl.
 * @date 2022/8/31.
 */
public class CommentKeyboardLayout extends LinearLayout implements ICommentKeyboardLayout {

    //region: 成员变量

    /**
     * 内容输入框
     */
    private EditText mEtContent;

    /**
     * 键盘改变事件监听
     */
    private KeyboardWrapper.OnKeyboardStateChangeListener mKeyboardStateChangeListener;

    //endregion

    //region: 构造函数

    public CommentKeyboardLayout(Context context) {
        this(context, null);
    }

    public CommentKeyboardLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentKeyboardLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupLayout(context);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置页面视图
     *
     * @param context
     */
    private void setupLayout(Context context) {
        inflate(context, R.layout.widget_layout_common_keyboard, this);
        mEtContent = findViewById(R.id.et_content);
    }

    //endregion

    //region: ICommentKeyboardLayout

    /**
     * 显示评论键盘
     *
     * @param hint
     */
    @Override
    public void show(@Nullable final CharSequence hint) {
        show("", hint);
    }

    /**
     * 显示评论键盘
     *
     * @param text
     * @param hint
     */
    @Override
    public void show(@Nullable final CharSequence text,
                     @Nullable final CharSequence hint) {
        setVisibility(VISIBLE);
        mEtContent.setText(StringUtils.isEmpty(text) ? "" : text);
        mEtContent.setSelection(StringUtils.length(text));
        mEtContent.setHint(StringUtils.isEmpty(hint) ? StringUtils.getString(R.string.resources_please_input_content_hint) : hint);
    }

    /**
     * 隐藏评论键盘
     */
    @Override
    public void hide() {
        setVisibility(GONE);
    }

    /**
     * 设置键盘状态监听
     */
    public void setKeyboardStateChangeListener(@NonNull final KeyboardWrapper.OnKeyboardStateChangeListener keyboardStateChangeListener) {
        mKeyboardStateChangeListener = keyboardStateChangeListener;
    }

    //endregion

    //region: 提供方法

    //endregion

}