package com.xxl.core.service;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * 下载进度自定义RequestBody
 *
 * @author xxl.
 * @date 2024/7/22.
 */
public class ProgressResponseBody extends ResponseBody {
    private final ResponseBody mResponseBody;

    private OnRequstCallBack mOnRequestCallBack;

    private BufferedSource mBufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, OnRequstCallBack callBack) {
        mResponseBody = responseBody;
        mOnRequestCallBack = callBack;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    /**
     * 重写进行包装source
     *
     * @return BufferedSource
     */
    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(mResponseBody.source()) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                try {
                    long bytesRead = super.read(sink, byteCount);
                    if (bytesRead != -1) {
                        totalBytesRead += bytesRead;
                    }
                    boolean done = bytesRead == -1;
                    mOnRequestCallBack.onProgress(totalBytesRead, mResponseBody.contentLength());
                    if (done) {
                        mOnRequestCallBack.onFinish();
                    }
                    return bytesRead;
                } catch (IOException e) {
                    mOnRequestCallBack.onError(e);
                    return 0;
                }
            }
        });
    }

    public interface OnRequstCallBack {
        void onProgress(long currentSize, long totalSize);

        void onError(Throwable e);

        void onFinish();
    }
}