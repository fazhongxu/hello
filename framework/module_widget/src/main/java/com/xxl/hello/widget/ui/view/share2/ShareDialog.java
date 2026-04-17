package com.xxl.hello.widget.ui.view.share2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xxl.hello.widget.R;

import java.util.ArrayList;
import java.util.List;

public class ShareDialog extends BottomSheetDialogFragment {
    private Context context;
    private List<SharePlatformItem> platformItems;
    private ShareContent shareContent;
    private ShareHelper shareHelper;
    private OnShareListener shareListener;
    private OnShareInterceptor shareInterceptor;

    private RecyclerView recyclerView;
    private SharePlatformAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private View progressContainer;
    private View contentContainer;

    public interface OnShareListener {
        void onShareStart(SharePlatform platform);
        void onShareSuccess(SharePlatform platform);
        void onShareFailure(SharePlatform platform, String error);
    }

    public static ShareDialog newInstance(ShareContent content, List<SharePlatform> platforms) {
        ShareDialog dialog = new ShareDialog();
        Bundle args = new Bundle();
        args.putSerializable("content", content);
        args.putSerializable("platforms", new ArrayList<>(platforms));
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        shareHelper = new ShareHelper(context);

        if (getArguments() != null) {
            shareContent = (ShareContent) getArguments().getSerializable("content");
            List<SharePlatform> platforms = (List<SharePlatform>) getArguments().getSerializable("platforms");
            platformItems = new ArrayList<>();
            for (SharePlatform platform : platforms) {
                platformItems.add(new SharePlatformItem(platform));
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);

        final View rootView = getLayoutInflater().inflate(R.layout.widget_dailog_layout_share, null, false);
        initViews(rootView);
        setupRecyclerView();
        dialog.setContentView(rootView);
        final ViewGroup parent = (ViewGroup) rootView.getParent();
        parent.setBackgroundColor(Color.TRANSPARENT);
        return  dialog;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        tvProgress = view.findViewById(R.id.tv_progress);
        progressContainer = view.findViewById(R.id.progress_container);
        contentContainer = view.findViewById(R.id.content_container);
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> dismiss());

        GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 8, false));
    }

    private void setupRecyclerView() {
        adapter = new SharePlatformAdapter();
        adapter.setList(platformItems);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            SharePlatformItem item = (SharePlatformItem) adapter.getItem(position);
            if (item != null) {
                handleShare(item.getPlatform());
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void handleShare(SharePlatform platform) {
        showProgress(true);
        
        // 使用 ShareHelper 处理分享逻辑
        ShareHelper helper = new ShareHelper(context);
        helper.setShareListener(new OnShareListener() {
            @Override
            public void onShareStart(SharePlatform platform) {
                if (shareListener != null) {
                    shareListener.onShareStart(platform);
                }
            }

            @Override
            public void onShareSuccess(SharePlatform platform) {
                showProgress(false);
                if (shareListener != null) {
                    shareListener.onShareSuccess(platform);
                }
                dismiss();
            }

            @Override
            public void onShareFailure(SharePlatform platform, String error) {
                showProgress(false);
                if (shareListener != null) {
                    shareListener.onShareFailure(platform, error);
                }
            }
        });
        helper.setShareInterceptor(shareInterceptor);
        helper.share(platform, shareContent);
    }



    private void showProgress(boolean show) {
        if (show) {
            progressContainer.setVisibility(View.VISIBLE);
            contentContainer.setVisibility(View.GONE);
        } else {
            progressContainer.setVisibility(View.GONE);
            contentContainer.setVisibility(View.VISIBLE);
        }
    }

    public void setShareInterceptor(OnShareInterceptor interceptor) {
        this.shareInterceptor = interceptor;
        if (shareHelper != null) {
            shareHelper.setShareInterceptor(interceptor);
        }
    }

    public void setOnShareListener(OnShareListener listener) {
        this.shareListener = listener;
        if (shareHelper != null) {
            shareHelper.setShareListener(listener);
        }
    }

    private static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(android.graphics.Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;
                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }
}