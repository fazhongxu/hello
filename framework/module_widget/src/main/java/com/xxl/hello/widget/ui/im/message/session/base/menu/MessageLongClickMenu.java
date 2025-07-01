package com.xxl.hello.widget.ui.im.message.session.base.menu;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.xxl.core.widget.recyclerview.adapter.BaseBindingAdapter;
import com.xxl.core.widget.recyclerview.adapter.BaseRecycleItemListener;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageLongClickMenuBinding;
import com.xxl.hello.widget.databinding.WidgetWindowLayoutMessageLongClickMenuBinding;
import com.xxl.hello.widget.ui.im.message.session.base.actions.MessageLongClickAction;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.ListUtils;

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
     * 菜单适配器
     */
    private MenuAdapter mMenuAdapter;

    private GridLayoutManager mGridLayoutManager;

    /**
     * 菜单弹窗
     */
    private BubblePopupWindow mBubblePopupWindow;

    private OnMenuItemClickListener mMenuItemClickListener;

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
        mMenuAdapter = new MenuAdapter();
        mMenuAdapter.setListener(action -> {
            if (mMenuItemClickListener != null) {
                mMenuItemClickListener.onMessageMenuItemClick(action);
            }
            if (mBubblePopupWindow != null) {
                mBubblePopupWindow.dismiss();
            }
        });
        mMenuBinding.rvList.setAdapter(mMenuAdapter);
        mBubblePopupWindow = new BubblePopupWindow(contentView, mMenuBinding.llBubbleLayout);
        mBubblePopupWindow.setOutsideTouchable(true);
    }

    private class MenuAdapter extends BaseBindingAdapter<MessageLongClickAction, OnMenuItemClickListener, WidgetRecycleItemMessageLongClickMenuBinding> {

        public MenuAdapter() {
            super(R.layout.widget_recycle_item_message_long_click_menu);
        }

        @Override
        public void convert(@NonNull WidgetRecycleItemMessageLongClickMenuBinding binding,
                            @NonNull MessageLongClickAction item) {
            binding.tvItem.setText(item.getTitle());
            binding.tvItem.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onMessageMenuItemClick(item);
                }
            });
            binding.executePendingBindings();
        }
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
        mGridLayoutManager = new GridLayoutManager(mAnchorView.getContext(), Math.min(ListUtils.getSize(actions),5));
        mMenuBinding.rvList.setLayoutManager(mGridLayoutManager);
        mMenuAdapter.setNewInstance(actions);
        return this;
    }

    /**
     * 设置菜单条目点击
     */
    public MessageLongClickMenu setOnMenuItemClickListener(@NonNull OnMenuItemClickListener listener) {
        mMenuItemClickListener = listener;
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        if (mBubblePopupWindow != null) {
            mBubblePopupWindow.showArrowTo(mAnchorView, BubbleStyle.ArrowDirection.Down, DisplayUtils.dp2px(4));
        }
    }

    //endregion

    //region: OnMenuItemClickListener

    public interface OnMenuItemClickListener extends BaseRecycleItemListener {

        /**
         * 菜单操作条目点击
         *
         * @param action
         */
        void onMessageMenuItemClick(@NonNull MessageLongClickAction action);
    }

    //endregion

}