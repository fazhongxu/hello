package com.xxl.hello.main.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 折叠吸顶效果demo列表内页（demo简化处理：不走依赖注入，纯代码创建视图）
 *
 * @author xxl.
 * @date 2026/10/14.
 */
public class StickyCollapseListFragment extends Fragment {

    //region: 成员变量

    private static final String KEY_TITLE = "key_title";

    //endregion

    //region: 构造函数

    public static StickyCollapseListFragment newInstance(@NonNull final String title) {
        final Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        final StickyCollapseListFragment fragment = new StickyCollapseListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion

    //region: 页面生命周期

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setBackgroundColor(Color.WHITE);

        final String title = getArguments() != null ? getArguments().getString(KEY_TITLE) : "";
        final List<String> items = new ArrayList<>();
        for (int index = 1; index <= 30; index++) {
            items.add(title.concat(" - 第").concat(String.valueOf(index)).concat("条"));
        }
        recyclerView.setAdapter(new ListAdapter(items));
        return recyclerView;
    }

    //endregion

    //region: 列表适配器

    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

        private final List<String> mItems = new ArrayList<>();

        ListAdapter(final List<String> items) {
            mItems.addAll(items);
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
            final TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, parent.getResources().getDisplayMetrics());
            final int minHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 72f, parent.getResources().getDisplayMetrics());
            textView.setPadding(padding, padding / 2, padding, padding / 2);
            textView.setMinHeight(minHeight);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            return new ItemViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
            holder.mTextView.setText(mItems.get(position));
            holder.mTextView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#F7F8FA"));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {

            final TextView mTextView;

            ItemViewHolder(@NonNull final View itemView) {
                super(itemView);
                mTextView = (TextView) itemView;
            }
        }
    }

    //endregion
}
