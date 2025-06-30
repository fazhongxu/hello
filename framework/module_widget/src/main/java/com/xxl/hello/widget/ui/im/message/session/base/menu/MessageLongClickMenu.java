package com.xxl.hello.widget.ui.im.message.session.base.menu;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetWindowLayoutMessageLongClickMenuBinding;
import com.xxl.hello.widget.ui.im.message.session.base.actions.MessageLongClickAction;

import java.util.List;

/**
 * 消息长按菜单
 *
 * @author xxl.
 * @date 2025/6/30.
 */
public class MessageLongClickMenu {

    //region: 成员变量

    /**
     * 锚点视图
     */
    private View mAnchorView;

    /**
     * 菜单视图
     */
    private WidgetWindowLayoutMessageLongClickMenuBinding mMenuBinding;

    /**
     * 菜单弹窗
     */
    private BubblePopupWindow mBubblePopupWindow;

    //endregion

    //region: 构造函数

    public MessageLongClickMenu(View anchorView) {
        mAnchorView = anchorView;
        setupLayout();
    }

    public final static MessageLongClickMenu from(View contentView) {
        return new MessageLongClickMenu(contentView);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View contentView = LayoutInflater.from(mAnchorView.getContext()).inflate(R.layout.widget_window_layout_message_long_click_menu, null);
        mMenuBinding = DataBindingUtil.bind(contentView);
        mBubblePopupWindow = new BubblePopupWindow(contentView, mMenuBinding.llBubbleLayout);
        mBubblePopupWindow.setOutsideTouchable(true);
    }

    //endregion

    //region: 提供方法

    /**
     * 设置菜单item
     *
     * @param actions
     * @return
     */
    public MessageLongClickMenu setItems(@NonNull List<MessageLongClickAction> actions) {
        // TODO: 2025/6/30  后面换成真正的rv显示和操作
        StringBuilder items = new StringBuilder();
        for (MessageLongClickAction action : actions) {
            items.append(action.getTitle())
                    .append(" ");
        }
        mMenuBinding.tvItems.setText(items);
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        if (mBubblePopupWindow != null) {
            mBubblePopupWindow.showArrowTo(mAnchorView, BubbleStyle.ArrowDirection.Up);
        }
    }

    //endregion

}