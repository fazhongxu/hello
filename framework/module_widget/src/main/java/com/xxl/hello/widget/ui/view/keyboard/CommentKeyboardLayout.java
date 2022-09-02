package com.xxl.hello.widget.ui.view.keyboard;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.core.listener.OnTextChangeListener;
import com.xxl.hello.widget.R;
import com.xxl.kit.KeyboardUtils;
import com.xxl.kit.KeyboardWrapper;
import com.xxl.kit.StringUtils;

/**
 * 评论输入布局
 *
 * @author xxl.
 * @date 2022/8/31.
 */
public class CommentKeyboardLayout extends LinearLayout implements ICommentKeyboardLayout,
        OnTextChangeListener {

    //region: 成员变量

    /**
     * 内容输入框
     */
    private EditText mEtContent;

    /**
     * 表情
     */
    private ImageView mIvFace;

    /**
     * 发送按钮
     */
    private TextView mTvSend;

    private LinearLayout mLLExpressionContainer;

    /**
     * 键盘改变事件监听
     */
    private KeyboardWrapper.OnKeyboardStateChangeListener mKeyboardStateChangeListener;

    /**
     * 输入类型
     */
    @CommentKeyboardInputType
    private int mInputType;

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
        mIvFace = findViewById(R.id.iv_face);
        mTvSend = findViewById(R.id.tv_send);
        mLLExpressionContainer = findViewById(R.id.ll_expression_container);
        mEtContent.addTextChangedListener(this);
        mIvFace.setOnClickListener(v -> {
            changeInputType(mInputType == CommentKeyboardInputType.TEXT ? CommentKeyboardInputType.EXPRESSION : CommentKeyboardInputType.TEXT);
        });
    }

    /**
     * 切换输入类型
     *
     * @param inputType
     */
    private void changeInputType(@CommentKeyboardInputType int inputType) {
        mInputType = inputType;
        if (inputType == CommentKeyboardInputType.EXPRESSION) {
            KeyboardUtils.hideSoftInput(mEtContent);
        } else {
            KeyboardUtils.showSoftInput(mEtContent);
        }
    }

    /**
     * 刷新表情布局
     *
     * @param keyboardHeight
     */
    private void refreshExpressionLayout(final int keyboardHeight) {
        final LinearLayout.LayoutParams layoutParams = (LayoutParams) mLLExpressionContainer.getLayoutParams();
        if (layoutParams.height <= 0 && keyboardHeight > 0) {
            layoutParams.height = keyboardHeight;
            mLLExpressionContainer.setLayoutParams(layoutParams);
        }
        if (mInputType == CommentKeyboardInputType.EXPRESSION) {
            mLLExpressionContainer.setVisibility(View.VISIBLE);
        } else {
            mLLExpressionContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 刷新文本输入布局
     */
    private void refreshTextInputLayout() {
        if (mInputType == CommentKeyboardInputType.EXPRESSION) {
            mIvFace.setImageResource(R.drawable.widget_ic_comment_keyboard);
            mTvSend.setVisibility(View.GONE);
        } else {
            mIvFace.setImageResource(R.drawable.widget_ic_comment_face);
            final Editable editable = mEtContent.getText();
            if (editable != null && !TextUtils.isEmpty(editable)) {
                mTvSend.setVisibility(View.VISIBLE);
            } else {
                mTvSend.setVisibility(View.GONE);
            }
        }
    }

    //endregion

    //region: OnTextChangeListener

    @Override
    public void onTextChanged(CharSequence s,
                              int start,
                              int before,
                              int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        refreshTextInputLayout();
    }

    //endregion

    //region: OnKeyboardStateChangeListener

    @Override
    public void onOpenKeyboard(final int keyboardHeight) {
        if (mInputType != CommentKeyboardInputType.TEXT) {
            changeInputType(CommentKeyboardInputType.TEXT);
        }
        refreshExpressionLayout(keyboardHeight);
        refreshTextInputLayout();
    }

    @Override
    public void onCloseKeyboard(final int keyboardHeight) {
        refreshExpressionLayout(keyboardHeight);
        refreshTextInputLayout();
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