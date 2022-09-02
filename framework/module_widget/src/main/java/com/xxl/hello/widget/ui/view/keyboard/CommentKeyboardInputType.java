package com.xxl.hello.widget.ui.view.keyboard;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 评论键盘输入类型
 *
 * @author xxl.
 * @date 2022/9/2.
 */
@IntDef({CommentKeyboardInputType.TEXT,
        CommentKeyboardInputType.EXPRESSION})
@Retention(RetentionPolicy.SOURCE)
public @interface CommentKeyboardInputType {

    /**
     * 文本
     */
    int TEXT = 0;

    /**
     * 表情
     */
    int EXPRESSION = 1;
}