package com.xxl.hello.widget.ui.view.share2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

public class ShareHelper {
    private Context context;
    private DownloadManager downloadManager;
    private OnShareInterceptor shareInterceptor;
    private ShareDialog.OnShareListener shareListener;

    public ShareHelper(Context context) {
        this.context = context;
        this.downloadManager = new DownloadManager(context);
    }

    public void setShareInterceptor(OnShareInterceptor shareInterceptor) {
        this.shareInterceptor = shareInterceptor;
    }

    public void setShareListener(ShareDialog.OnShareListener shareListener) {
        this.shareListener = shareListener;
    }

    public void share(SharePlatform platform, ShareContent content) {
        if (shareListener != null) {
            shareListener.onShareStart(platform);
        }

        if (shareInterceptor != null && shareInterceptor.shouldInterceptBeforeDownload(platform, content)) {
            if (shareInterceptor.onShare(platform, content)) {
                if (shareListener != null) {
                    shareListener.onShareSuccess(platform);
                }
                return;
            }
        }

        if (needDownload(content)) {
            downloadAndShare(platform, content);
        } else {
            processShare(platform, content);
        }
    }

    private boolean needDownload(ShareContent content) {
        if (content == null) {
            return false;
        }

        if (content.getType() == ShareContent.Type.IMAGE) {
            List<String> imageUrls = content.getImageUrls();
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

        if (content.getType() == ShareContent.Type.VIDEO) {
            String videoUrl = content.getVideoUrl();
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

    private void downloadAndShare(SharePlatform platform, ShareContent content) {
        if (content.getType() == ShareContent.Type.IMAGE) {
            downloadManager.downloadImages(content.getImageUrls(), new DownloadManager.DownloadCallback() {
                @Override
                public void onSuccess(List<String> localPaths) {
                    content.setImageUrls(localPaths);
                    processShare(platform, content);
                }

                @Override
                public void onFailure(String error) {
                    if (shareListener != null) {
                        shareListener.onShareFailure(platform, error);
                    }
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProgress(int current, int total) {
                    // 可以添加进度回调
                }
            });
        } else if (content.getType() == ShareContent.Type.VIDEO) {
            downloadManager.downloadVideo(content.getVideoUrl(), new DownloadManager.DownloadCallback() {
                @Override
                public void onSuccess(List<String> localPaths) {
                    if (localPaths != null && !localPaths.isEmpty()) {
                        content.setVideoUrl(localPaths.get(0));
                    }
                    processShare(platform, content);
                }

                @Override
                public void onFailure(String error) {
                    if (shareListener != null) {
                        shareListener.onShareFailure(platform, error);
                    }
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProgress(int current, int total) {
                    // 可以添加进度回调
                }
            });
        }
    }

    private void processShare(SharePlatform platform, ShareContent content) {
        if (shareInterceptor != null && shareInterceptor.onShare(platform, content)) {
            if (shareListener != null) {
                shareListener.onShareSuccess(platform);
            }
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
                    java.util.ArrayList<Uri> uris = new java.util.ArrayList<>();
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
}
