package com.xxl.hello.widget.ui.view.emoji;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.vanniktech.emoji.emoji.EmojiCategory;
import com.xxl.hello.widget.R;

/**
 * 自定义emoji分类
 */
public class CustomCategory implements EmojiCategory {
    private static final CustomEmoji[] EMOJIS = new CustomEmoji[]{
        new CustomEmoji("微笑",R.drawable.emoji_backspace),
        new CustomEmoji("微笑",R.drawable.emoji_backspace),
        new CustomEmoji("微笑",R.drawable.emoji_backspace),
    };

    @Override @NonNull
    public CustomEmoji[] getEmojis() {
        return EMOJIS;
    }

    @Override @DrawableRes
    public int getIcon() {
        return R.drawable.emoji_ios_category_smileysandpeople;
    }

    @Override @StringRes
    public int getCategoryName() {
        return R.string.emoji_ios_category_smileysandpeople;
    }
}