package com.xxl.hello.widget.ui.view.share;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.ui.view.share.adapter.ResourcesShareAdapter;
import com.xxl.hello.widget.ui.view.share.adapter.ResourcesShareItemListener;
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
     * 点击事件
     */
    private OnShareItemOperate mListener;

    /**
     * 分享列表
     */
    private RecyclerView mRvShareList;

    /**
     * 资源分享列表适配器
     */
    private ResourcesShareAdapter mResourcesShareAdapter;

    //endregion

    //region: 构造函数

    public ResourcesShareWindow(@NonNull final Fragment fragment) {
        super(fragment);
        setPopupGravity(Gravity.BOTTOM)
                .setWidth(MATCH_PARENT)
                .setHeight(WRAP_CONTENT);
        setupLayout();
    }

    public static ResourcesShareWindow from(@NonNull final Fragment fragment) {
        return new ResourcesShareWindow(fragment);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.widget_window_layout_resource_share);
        mRvShareList = ViewUtils.findView(rootView, R.id.rv_list);
        if (mResourcesShareAdapter == null) {
            mResourcesShareAdapter = new ResourcesShareAdapter();
        }
        mRvShareList.setAdapter(mResourcesShareAdapter);
        final ResourcesShareItemListener listener = (targetView, itemPosition, targetItem) -> {
            if (mListener != null) {
                mListener.onClick(this, targetItem, targetView, itemPosition);
            }
        };
        mResourcesShareAdapter.setListener(listener);
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
        mResourcesShareAdapter.setList(operateItems);
        return this;
    }

    /**
     * 设置分享列表视图管理
     *
     * @param layoutManager
     * @return
     */
    public ResourcesShareWindow setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRvShareList.setLayoutManager(layoutManager);
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