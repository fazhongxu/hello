package com.xxl.hello.widget.ui.view.combine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import com.othershe.combinebitmap.layout.ILayoutManager;

/**
 * 自定义视图管理
 *
 * @author xxl.
 * @date 2024/02/02.
 */
public class CustomLayoutManager implements ILayoutManager {

    /**
     * 是否有视频
     */
    private boolean mHasVideo;

    /**
     * 视频索引
     */
    private int mVideoPosition = 0;

    /**
     * 视频标识
     */
    private Bitmap mVideoBitmap;

    public CustomLayoutManager() {

    }

    public CustomLayoutManager(boolean hasVideo, int videoPosition, Bitmap videoBitmap) {
        mHasVideo = hasVideo;
        mVideoPosition = videoPosition;
        mVideoBitmap = videoBitmap;
    }

    @Override
    public Bitmap combineBitmap(int size, int subSize, int gap, int gapColor, Bitmap[] bitmaps) {
        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        if (gapColor == 0) {
            gapColor = Color.WHITE;
        }
        canvas.drawColor(gapColor);

        int count = bitmaps.length;
        Bitmap subBitmap;

        int[][] dxy = {{0, 0}, {1, 0}, {1, 1}, {0, 1}};

        for (int i = 0; i < count; i++) {
            if (bitmaps[i] == null) {
                continue;
            }
            subBitmap = Bitmap.createScaledBitmap(bitmaps[i], size, size, true);
            if (count == 2 || (count == 3 && i == 0)) {
                subBitmap = Bitmap.createBitmap(subBitmap, (size + gap) / 4, 0, (size - gap) / 2, size);
            } else if ((count == 3 && (i == 1 || i == 2)) || count == 4) {
                subBitmap = Bitmap.createBitmap(subBitmap, (size + gap) / 4, (size + gap) / 4, (size - gap) / 2, (size - gap) / 2);
            }

            int dx = dxy[i][0];
            int dy = dxy[i][1];

            canvas.drawBitmap(subBitmap, dx * (size + gap) / 2.0f, dy * (size + gap) / 2.0f, null);
            if (mHasVideo && mVideoPosition >= 0 && mVideoPosition == i && mVideoBitmap != null) {
                RectF rectF = new RectF(dx * (size + gap) / 2.0f, dy * (size + gap) / 2.0f, dx * (size + gap) / 2.0f + subBitmap.getWidth(), dy * (size + gap) / 2.0f + subBitmap.getHeight());
                canvas.drawBitmap(mVideoBitmap, (int) (rectF.centerX() - mVideoBitmap.getWidth() / 2), (int) (rectF.centerY() - mVideoBitmap.getHeight() / 2), null);
            }
        }
        return result;
    }
}