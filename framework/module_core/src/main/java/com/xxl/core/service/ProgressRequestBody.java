package com.xxl.core.service;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * 上传进度自定义RequestBody
 *
 * @author xxl.
 * @date 2024/7/22.
 */
public class ProgressRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private OnRequstCallBack mCallBack;

    public ProgressRequestBody(RequestBody requestBody, OnRequstCallBack callBack) {
        mRequestBody = requestBody;
        mCallBack = callBack;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink = Okio.buffer(new ForwardingSink(sink) {
            private long bytesWritten = 0L;
            private long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) {
                try {
                    super.write(source, byteCount);
                    if (contentLength == 0) {
                        contentLength = contentLength();
                    }
                    bytesWritten += byteCount;
                    mCallBack.onProgress(bytesWritten, contentLength);
                } catch (IOException e) {
                    mCallBack.onError(e);
                    return;
                } finally {
                    mCallBack.onFinish();
                }
            }
        });

        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    public interface OnRequstCallBack {
        void onProgress(long currentSize, long totalSize);

        void onError(Throwable e);

        void onFinish();
    }

}