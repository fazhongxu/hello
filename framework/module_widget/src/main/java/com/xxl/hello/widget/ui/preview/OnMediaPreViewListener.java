package com.xxl.hello.widget.ui.preview;

import com.xxl.hello.widget.ui.view.DragDismissLayout;

/**
 * @author xxl.
 * @date 2023/6/2.
 */
public interface OnMediaPreViewListener extends DragDismissLayout.OnDragDismissLayoutListener {

    /**
     * 关闭页面点击
     */
    void onCloseClick();
}