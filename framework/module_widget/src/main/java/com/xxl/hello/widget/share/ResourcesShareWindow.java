package com.xxl.hello.widget.share;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi;
import com.xxl.hello.widget.R;
import com.xxl.kit.StringUtils;
import com.xxl.kit.ViewUtils;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * 资源分享弹窗
 *
 * @author xxl.
 * @date 2022/7/19.
 */
public class ResourcesShareWindow extends BasePopupWindow {

    //region: 成员变量

    /**
     * 分享视图
     */
    private GridLayout mGridLayout;

    /**
     * 点击事件
     */
    private OnShareItemOperate mListener;

    //endregion

    //region: 构造函数

    public ResourcesShareWindow(@NonNull final Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM)
                .setWidth(MATCH_PARENT)
                .setHeight(WRAP_CONTENT);
        setupLayout();
    }

    public static ResourcesShareWindow from(@NonNull final Context context) {
        return new ResourcesShareWindow(context);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.widget_window_layout_resource_share);
        mGridLayout = ViewUtils.findView(rootView, R.id.grid_layout);
        setContentView(rootView);
    }

    //endregion

    //region: 提供方法

    /**
     * 构建微信分享操作
     *
     * @param handle
     * @return
     */
    public static <T> ShareOperateItem buildWeChatAction(@NonNull final ShareOperateItem.OnItemHandle<T> handle) {
        return ShareOperateItem.obtain()
                .setOperateType(SystemEnumsApi.ShareOperateType.WE_CHAT)
                .setTitle(StringUtils.getString(R.string.resources_we_chat))
                .setIcon(R.drawable.resources_ic_we_chat)
                .setOnItemHandle(handle);
    }

    /**
     * 构建微信朋友圈分享操作
     *
     * @param handle
     * @return
     */
    public static <T> ShareOperateItem buildWeChatCircleAction(@NonNull final ShareOperateItem.OnItemHandle<T> handle) {
        return ShareOperateItem.obtain()
                .setOperateType(SystemEnumsApi.ShareOperateType.WE_CHAT_CIRCLE)
                .setTitle(StringUtils.getString(R.string.resources_friend_circle))
                .setIcon(R.drawable.resources_ic_we_chat_circle)
                .setOnItemHandle(handle);
    }

    /**
     * 添加条目
     *
     * @param operateItems
     * @return
     */
    public ResourcesShareWindow addItems(@NonNull final List<ShareOperateItem> operateItems) {
        mGridLayout.removeAllViews();
        for (ShareOperateItem operateItem : operateItems) {
            final View itemView = LayoutInflater.from(getContext()).inflate(R.layout.widget_layout_item_resource_share, mGridLayout, false);
            final ImageView ivIcon = ViewUtils.findView(itemView, R.id.iv_icon);
            final TextView tvTitle = ViewUtils.findView(itemView, R.id.tv_title);
            ivIcon.setImageResource(operateItem.getIcon());
            tvTitle.setText(operateItem.getTitle());
            ViewUtils.setOnClickListener(itemView, v -> {
                if (mListener != null) {
                    mListener.onClick(this, operateItem, itemView, operateItems.indexOf(operateItem));
                }
            });
            mGridLayout.addView(itemView);
        }
        return this;
    }

    /**
     * 设置点击事件
     *
     * @param listener
     * @return
     */
    public ResourcesShareWindow setOnItemClickListener(@Nullable final OnShareItemOperate listener) {
        mListener = listener;
        return this;
    }

    /**
     * 展示弹窗
     */
    public void show() {
        showPopupWindow();
    }

    //endregion

    //endregion

    //region: 内部辅助方法

    //endregion

}