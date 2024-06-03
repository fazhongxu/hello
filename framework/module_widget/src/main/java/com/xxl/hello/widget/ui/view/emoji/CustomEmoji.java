package com.xxl.hello.widget.ui.view.emoji;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.vanniktech.emoji.emoji.Emoji;

/**
 * 自定义emoji
 *
 * @author xxl.
 * @date 2024/6/3.
 */
public class CustomEmoji extends Emoji {

    private String mCode;

    private int mDrawableRes;

    public CustomEmoji(String code,
                       @DrawableRes int drawableRes) {
        super(0,new String[]{},drawableRes,false);
        mCode = code;
        mDrawableRes = drawableRes;
    }

    public CustomEmoji(@NonNull int[] codePoints, @NonNull String[] shortcodes, int resource, boolean isDuplicate) {
        super(codePoints, shortcodes, resource, isDuplicate);
    }

    public CustomEmoji(int codePoint, @NonNull String[] shortcodes, int resource, boolean isDuplicate) {
        super(codePoint, shortcodes, resource, isDuplicate);
    }

    public CustomEmoji(int codePoint, @NonNull String[] shortcodes, int resource, boolean isDuplicate, Emoji... variants) {
        super(codePoint, shortcodes, resource, isDuplicate, variants);
    }

    public CustomEmoji(@NonNull int[] codePoints, @NonNull String[] shortcodes, int resource, boolean isDuplicate, Emoji... variants) {
        super(codePoints, shortcodes, resource, isDuplicate, variants);
    }

    @NonNull
    @Override
    public String getUnicode() {
        return mCode;
    }
}