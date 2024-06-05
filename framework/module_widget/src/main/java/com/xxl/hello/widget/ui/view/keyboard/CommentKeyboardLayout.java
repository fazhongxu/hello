package com.xxl.hello.widget.ui.view.keyboard;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vanniktech.emoji.EmojiImageView;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiView;
import com.vanniktech.emoji.RecentEmojiManager;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener;
import com.xxl.core.listener.OnTextChangeListener;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.ui.view.emoji.EmojiEditText;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.StringUtils;

/**
 * 评论输入布局
 *
 * @author xxl.
 * @date 2022/8/31.
 */
public class CommentKeyboardLayout extends LinearLayout implements ICommentKeyboardLayout, OnTextChangeListener,
        OnEmojiClickListener, OnEmojiLongClickListener,
        OnEmojiBackspaceClickListener {

    //region: 成员变量

    /**
     * 内容输入框
     */
    private EmojiEditText mEtContent;

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
     * 最近表情管理
     */
    private RecentEmojiManager mRecentEmojiManager;

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

    //region: 页面生命周期

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mRecentEmojiManager != null) {
            mRecentEmojiManager.persist();
        }
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
    }

    //endregion

    //region: OnTextChangeListener

    @Override
    public void onTextChanged(CharSequence s,
                              int start,
                              int before,
                              int count) {
        LogUtils.d("s");
    }

    @Override
    public void afterTextChanged(Editable s) {
//        refreshTextInputLayout();
    }

    //endregion

    //region: ICommentKeyboardLayout

    /**
     * 初始化
     *
     * @param activity
     * @param contentView
     */
    @Override
    public void init(@NonNull Activity activity,
                     @NonNull View contentView) {
        EmotionKeyboard.with(activity)
                .setEmotionView(mLLExpressionContainer)
                .bindToContent(contentView)
                .bindToEditText(mEtContent)
                .bindToEmotionButton(mIvFace)
                .build();

        mRecentEmojiManager = new RecentEmojiManager(activity);
        EmojiPopup.Builder builder = EmojiPopup.Builder.fromRootView(new View(activity))
                .setRecentEmoji(mRecentEmojiManager);

        EmojiView emojiView = new EmojiView(activity, this, this, builder);
        emojiView.setOnEmojiBackspaceClickListener(this);
        mLLExpressionContainer.removeAllViews();
        mLLExpressionContainer.addView(emojiView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(300)));
    }

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


    //endregion

    //region: OnEmojiClickListener

    @Override
    public void onEmojiClick(@NonNull EmojiImageView view,
                             @NonNull Emoji emoji) {
        mRecentEmojiManager.addEmoji(emoji);
        //variantEmoji.addVariant(emoji);
        view.updateEmoji(emoji);

        mEtContent.input(emoji);
    }

    //endregion

    //region: OnEmojiLongClickListener

    @Override
    public void onEmojiLongClick(@NonNull EmojiImageView view,
                                 @NonNull Emoji emoji) {

    }

    //endregion

    //region: OnEmojiBackspaceClickListener

    @Override
    public void onEmojiBackspaceClick(View v) {
        mEtContent.backspace();
    }

    //endregion

    //region: 提供方法

    //endregion

}