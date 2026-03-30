
package com.xxl.hello.widget.ui.view.share2;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManager {
    private static final String TAG = "DownloadManager";
    private Context context;
    private OkHttpClient okHttpClient;
    private ExecutorService executorService;

    public interface DownloadCallback {
        void onSuccess(List<String> localPaths);

        void onFailure(String error);

        void onProgress(int current, int total);
    }

    public DownloadManager(Context context) {
        this.context = context.getApplicationContext();
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        this.executorService = Executors.newFixedThreadPool(3);
    }

    public void downloadImages(List<String> urls, DownloadCallback callback) {
        downloadFiles(urls, "image", callback);
    }

    public void downloadVideo(String url, DownloadCallback callback) {
        List<String> list = new ArrayList<>();
        list.add(url);
        downloadFiles(list, "video", callback);
    }

    private void downloadFiles(List<String> urls, String fileType, DownloadCallback callback) {
        if (urls == null || urls.isEmpty()) {
            callback.onSuccess(new ArrayList<>());
            return;
        }

        executorService.execute(() -> {
            List<String> localPaths = new ArrayList<>();
            int total = urls.size();

            for (int i = 0; i < total; i++) {
                String url = urls.get(i);

                if (isFileExists(url)) {
                    localPaths.add(url);
                    callback.onProgress(i + 1, total);
                    continue;
                }

                try {
                    String fileName = generateFileName(url, fileType);
                    File saveFile = new File(context.getExternalCacheDir(), fileName);

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = okHttpClient.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        callback.onFailure("下载失败: " + response.code());
                        return;
                    }

                    InputStream inputStream = response.body().byteStream();
                    FileOutputStream outputStream = new FileOutputStream(saveFile);

                    byte[] buffer = new byte[8192];
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, read);
                    }

                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();

                    localPaths.add(saveFile.getAbsolutePath());
                    callback.onProgress(i + 1, total);

                } catch (Exception e) {
                    Log.e(TAG, "Download error", e);
                    callback.onFailure("下载失败: " + e.getMessage());
                    return;
                }
            }

            callback.onSuccess(localPaths);
        });
    }

    private boolean isFileExists(String path) {
        if (path == null) return false;
        File file = new File(path);
        return file.exists();
    }

    private String generateFileName(String url, String fileType) {
        String extension = "";
        if ("image".equals(fileType)) {
            extension = ".jpg";
        } else if ("video".equals(fileType)) {
            extension = ".mp4";
        }
        return "share_" + System.currentTimeMillis() + "_" + Math.abs(url.hashCode()) + extension;
    }
}