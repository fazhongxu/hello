package com.xxl.hello.main.ui.main.window;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xxl.hello.main.R;
import com.xxl.hello.main.ui.main.adapter.VideoCoverAdapter;
import com.xxl.hello.main.ui.main.adapter.VideoCoverEntity;
import com.xxl.hello.main.ui.main.adapter.VideoCoverRecycleItemListener;
import com.xxl.kit.ViewUtils;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * 视频封面弹窗
 *
 * @author xxl.
 * @date 2026/3/24.
 */
public class VideoCoverPopupWindow extends BasePopupWindow {

    //region: 成员变量

    /**
     * 点击事件
     */
    private OnVideoCoverPopupWindowListener mListener;

    /**
     * 标题
     */
    private TextView mTvTitle;

    /**
     * 封面列表
     */
    private RecyclerView mRvCovers;

    /**
     * 封面适配器
     */
    private VideoCoverAdapter mAdapter;

    //endregion

    //region: 构造函数

    public VideoCoverPopupWindow(@NonNull final Activity activity, @Nullable final OnVideoCoverPopupWindowListener listener) {
        super(activity);
        mListener = listener;
        setPopupGravity(Gravity.BOTTOM);
        setupLayout();
    }

    public static VideoCoverPopupWindow from(@NonNull final Activity activity, @Nullable final OnVideoCoverPopupWindowListener listener) {
        return new VideoCoverPopupWindow(activity, listener);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置视图
     */
    private void setupLayout() {
        View rootView = createPopupById(R.layout.main_window_layout_video_cover);
        mTvTitle = ViewUtils.findView(rootView, R.id.tv_title);
        mRvCovers = ViewUtils.findView(rootView, R.id.rv_covers);

        // 初始化RecyclerView
        mAdapter = new VideoCoverAdapter();
        mAdapter.setListener(new VideoCoverRecycleItemListener() {
            @Override
            public void onCoverClick(@NonNull VideoCoverEntity targetEntity) {
                if (mListener != null) {
                    mListener.onCoverSelected(targetEntity);
                }
                dismiss();
            }
        });

        mRvCovers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvCovers.setAdapter(mAdapter);

        setContentView(rootView);
    }

    //endregion

    //region: 提供方法

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public VideoCoverPopupWindow setTitle(final String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置封面数据
     *
     * @param coverEntities
     * @return
     */
    public VideoCoverPopupWindow setCoverData(final List<VideoCoverEntity> coverEntities) {
        if (mAdapter != null) {
            mAdapter.setNewData(coverEntities);
        }
        return this;
    }

    //endregion

    //region: OnVideoCoverPopupWindowListener

    public interface OnVideoCoverPopupWindowListener {

        /**
         * 封面选择
         *
         * @param coverEntity
         */
        void onCoverSelected(final VideoCoverEntity coverEntity);
    }

    //endregion

}
