package com.xxl.core.widget.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xxl.core.R;

/**
 * adapter加载更多底部视图
 *
 * @author xxl.
 * @date 2022/10/11.
 */
public class SimpleLoadMoreView extends BaseLoadMoreView {

    @Override
    @NonNull
    public View getRootView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.core_layout_simple_load_more, parent, false);
    }

    @Override
    @NonNull
    public View getLoadingView(@NonNull BaseViewHolder holder) {
        return holder.getView(R.id.load_more_loading_view);
    }

    @Override
    @NonNull
    public View getLoadComplete(@NonNull BaseViewHolder holder) {
        return holder.getView(R.id.load_more_load_complete_view);
    }

    @Override
    @NonNull
    public View getLoadEndView(@NonNull BaseViewHolder holder) {
        return holder.getView(R.id.load_more_load_end_view);
    }

    @Override
    @NonNull
    public View getLoadFailView(@NonNull BaseViewHolder holder) {
        return holder.getView(R.id.load_more_load_fail_view);
    }

}