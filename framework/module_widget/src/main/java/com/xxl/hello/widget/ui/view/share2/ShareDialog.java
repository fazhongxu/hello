package com.xxl.hello.widget.ui.view.share2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xxl.hello.widget.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShareDialog extends BottomSheetDialogFragment {
    private Context context;
    private List<SharePlatformItem> platformItems;
    private ShareContent shareContent;
    private DownloadManager downloadManager;
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
        downloadManager = new DownloadManager(context);
        
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
        
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            SharePlatformItem item = platformItems.get(position);
            handleShare(item.getPlatform());
        });
        
        recyclerView.setAdapter(adapter);
    }
    
    private void handleShare(SharePlatform platform) {
        if (shareListener != null) {
            shareListener.onShareStart(platform);
        }
        
        if (needDownload()) {
            showProgress(true);
            downloadAndShare(platform);
        } else {
            processShare(platform, shareContent);
        }
    }
    
    private boolean needDownload() {
        if (shareContent == null) {
            return false;
        }
        
        if (shareContent.getType() == ShareContent.Type.IMAGE) {
            List<String> imageUrls = shareContent.getImageUrls();
            if (imageUrls != null && !imageUrls.isEmpty()) {
                for (String url : imageUrls) {
                    if (isHttpUrl(url)) {
                        return true;
                    }
                    if (!isFileExists(url)) {
                        return true;
                    }
                }
            }
            return false;
        }
        
        if (shareContent.getType() == ShareContent.Type.VIDEO) {
            String videoUrl = shareContent.getVideoUrl();
            if (videoUrl != null && !videoUrl.isEmpty()) {
                if (isHttpUrl(videoUrl)) {
                    return true;
                }
                return !isFileExists(videoUrl);
            }
            return false;
        }
        
        return false;
    }
    
    private boolean isHttpUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
    }
    
    private boolean isFileExists(String path) {
        if (path == null) return false;
        File file = new File(path);
        return file.exists();
    }
    
    private void downloadAndShare(SharePlatform platform) {
        if (shareContent.getType() == ShareContent.Type.IMAGE) {
            downloadManager.downloadImages(shareContent.getImageUrls(), new DownloadManager.DownloadCallback() {
                @Override
                public void onSuccess(List<String> localPaths) {
                    showProgress(false);
                    shareContent.setImageUrls(localPaths);
                    processShare(platform, shareContent);
                }
                
                @Override
                public void onFailure(String error) {
                    showProgress(false);
                    if (shareListener != null) {
                        shareListener.onShareFailure(platform, error);
                    }
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                
                @Override
                public void onProgress(int current, int total) {
                    if (tvProgress != null) {
                        tvProgress.setText("下载中: " + current + "/" + total);
                    }
                    if (progressBar != null) {
                        progressBar.setMax(total);
                        progressBar.setProgress(current);
                    }
                }
            });
        } else if (shareContent.getType() == ShareContent.Type.VIDEO) {
            downloadManager.downloadVideo(shareContent.getVideoUrl(), new DownloadManager.DownloadCallback() {
                @Override
                public void onSuccess(List<String> localPaths) {
                    showProgress(false);
                    if (localPaths != null && !localPaths.isEmpty()) {
                        shareContent.setVideoUrl(localPaths.get(0));
                    }
                    processShare(platform, shareContent);
                }
                
                @Override
                public void onFailure(String error) {
                    showProgress(false);
                    if (shareListener != null) {
                        shareListener.onShareFailure(platform, error);
                    }
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                
                @Override
                public void onProgress(int current, int total) {
                    if (tvProgress != null) {
                        tvProgress.setText("下载中: " + current + "/" + total);
                    }
                    if (progressBar != null) {
                        progressBar.setMax(total);
                        progressBar.setProgress(current);
                    }
                }
            });
        }
    }
    
    private void processShare(SharePlatform platform, ShareContent content) {
        if (shareInterceptor != null && shareInterceptor.onShare(platform, content)) {
            if (shareListener != null) {
                shareListener.onShareSuccess(platform);
            }
            dismiss();
            return;
        }
        
        performInternalShare(platform, content);
    }
    
    private void performInternalShare(SharePlatform platform, ShareContent content) {
        boolean success = false;
        String errorMsg = "";
        
        try {
            if (platform == SharePlatform.SYSTEM) {
                success = shareToSystem(content);
            } else if (platform == SharePlatform.WEIXIN || platform == SharePlatform.WEIXIN_CIRCLE) {
                success = shareToWeChat(platform, content);
            } else if (platform == SharePlatform.QQ || platform == SharePlatform.QZONE) {
                success = shareToQQ(platform, content);
            } else if (platform == SharePlatform.WEIBO) {
                success = shareToWeibo(content);
            }
        } catch (Exception e) {
            errorMsg = e.getMessage();
            success = false;
        }
        
        if (shareListener != null) {
            if (success) {
                shareListener.onShareSuccess(platform);
                dismiss();
            } else {
                shareListener.onShareFailure(platform, errorMsg);
            }
        }
    }
    
    private boolean shareToSystem(ShareContent content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(getMimeType(content));
        
        if (content.getType() == ShareContent.Type.LINK) {
            intent.setType("text/plain");
            String text = content.getTitle() + "\n" + 
                         content.getContent() + "\n" + 
                         content.getUrl();
            intent.putExtra(Intent.EXTRA_TEXT, text);
        } else if (content.getType() == ShareContent.Type.IMAGE) {
            List<String> imagePaths = content.getImageUrls();
            if (imagePaths != null && !imagePaths.isEmpty()) {
                if (imagePaths.size() == 1) {
                    Uri uri = FileProvider.getUriForFile(context, 
                        context.getPackageName() + ".fileprovider", 
                        new File(imagePaths.get(0)));
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    ArrayList<Uri> uris = new ArrayList<>();
                    for (String path : imagePaths) {
                        uris.add(FileProvider.getUriForFile(context, 
                            context.getPackageName() + ".fileprovider", 
                            new File(path)));
                    }
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
        } else if (content.getType() == ShareContent.Type.VIDEO) {
            if (content.getVideoUrl() != null) {
                Uri uri = FileProvider.getUriForFile(context,
                    context.getPackageName() + ".fileprovider",
                    new File(content.getVideoUrl()));
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
        
        if (content.getTitle() != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, content.getTitle());
        }
        
        context.startActivity(Intent.createChooser(intent, "分享到"));
        return true;
    }
    
    private boolean shareToWeChat(SharePlatform platform, ShareContent content) {
        Toast.makeText(context, "分享到" + platform.getName(), Toast.LENGTH_SHORT).show();
        return true;
    }
    
    private boolean shareToQQ(SharePlatform platform, ShareContent content) {
        Toast.makeText(context, "分享到" + platform.getName(), Toast.LENGTH_SHORT).show();
        return true;
    }
    
    private boolean shareToWeibo(ShareContent content) {
        Toast.makeText(context, "分享到微博", Toast.LENGTH_SHORT).show();
        return true;
    }
    
    private String getMimeType(ShareContent content) {
        if (content.getType() == ShareContent.Type.IMAGE) {
            return "image/*";
        } else if (content.getType() == ShareContent.Type.VIDEO) {
            return "video/*";
        } else {
            return "text/plain";
        }
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
    }
    
    public void setOnShareListener(OnShareListener listener) {
        this.shareListener = listener;
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