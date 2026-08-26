package com.xxl.hello.user.data.local;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Base64;

import com.xxl.hello.user.data.model.api.SwipeCaptchaData;

import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.UUID;

/**
 * 滑块验证码本地数据源
 *
 * <p>接口未就绪前，本地生成与「服务端下发结构」一致的验证码数据（base64 背景图 + 滑块图）。
 * 接入接口后删除此类，改为从服务端拉取 {@link SwipeCaptchaData} 即可。
 *
 * @author xxl.
 * @date 2026/8/26.
 */
public final class SwipeCaptchaLocalDataSource {

    //region: 默认图片参数(px)

    private static final int BG_WIDTH = 310;
    private static final int BG_HEIGHT = 155;
    private static final int BLOCK_WIDTH = 63;
    private static final int BLOCK_HEIGHT = 63;

    private static final int COLOR_BG_START = Color.parseColor("#D6E8FA");
    private static final int COLOR_BG_END = Color.parseColor("#A8CBF0");
    private static final int COLOR_PIECE = Color.parseColor("#2F9DD8");
    private static final int COLOR_PIECE_BORDER = Color.parseColor("#1F7FB0");

    //endregion

    private SwipeCaptchaLocalDataSource() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //region: 提供方法

    /**
     * 本地生成一份验证码数据
     *
     * @return 验证码数据 + 本地校验用的正确横坐标（服务端场景下该值由服务端持有，不下发）
     */
    public static Captcha obtainCaptcha() {
        final Random random = new Random();
        final int correctX = random.nextInt(BG_WIDTH - BLOCK_WIDTH + 1);
        final int y = random.nextInt(BG_HEIGHT - BLOCK_HEIGHT + 1);

        final Bitmap bgBitmap = createBgBitmap(correctX, y);
        final Bitmap blockBitmap = createBlockBitmap();

        final SwipeCaptchaData data = SwipeCaptchaData.obtain()
                .setChallenge(UUID.randomUUID().toString().replace("-", ""))
                .setBgImage(encodeToDataUri(bgBitmap))
                .setBlockImage(encodeToDataUri(blockBitmap))
                .setY(y)
                .setBlockWidth(BLOCK_WIDTH)
                .setBlockHeight(BLOCK_HEIGHT);

        recycleQuietly(bgBitmap);
        recycleQuietly(blockBitmap);

        return new Captcha(data, correctX);
    }

    /**
     * 解码 base64 图片数据（兼容 "data:image/png;base64,xxx" 前缀）
     */
    public static Bitmap decodeBase64Image(final String dataUri) {
        if (dataUri == null || dataUri.length() == 0) {
            return null;
        }
        final int commaIndex = dataUri.indexOf(',');
        final String base64 = commaIndex >= 0 ? dataUri.substring(commaIndex + 1) : dataUri;
        final byte[] bytes = Base64.decode(base64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 生成背景图：渐变底 + 在指定位置抠出拼图缺口
     */
    private static Bitmap createBgBitmap(final int holeX, final int holeY) {
        final Bitmap bitmap = Bitmap.createBitmap(BG_WIDTH, BG_HEIGHT, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new LinearGradient(0, 0, BG_WIDTH, BG_HEIGHT, COLOR_BG_START, COLOR_BG_END, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, BG_WIDTH, BG_HEIGHT, paint);

        // 抠出透明缺口（形状与滑块图一致）
        paint.setShader(null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPath(createPiecePath(new RectF(holeX, holeY, holeX + BLOCK_WIDTH, holeY + BLOCK_HEIGHT),
                BLOCK_WIDTH / 4f), paint);
        paint.setXfermode(null);

        return bitmap;
    }

    /**
     * 生成滑块图：填充拼图块
     */
    private static Bitmap createBlockBitmap() {
        final Bitmap bitmap = Bitmap.createBitmap(BLOCK_WIDTH, BLOCK_HEIGHT, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        final Path piecePath = createPiecePath(new RectF(0, 0, BLOCK_WIDTH, BLOCK_HEIGHT), BLOCK_WIDTH / 4f);

        final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(COLOR_PIECE);
        canvas.drawPath(piecePath, fillPaint);

        final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2);
        borderPaint.setColor(COLOR_PIECE_BORDER);
        canvas.drawPath(piecePath, borderPaint);

        return bitmap;
    }

    /**
     * 构造拼图块路径：主体为矩形，上边凸起半圆、下边凹陷半圆，完整落在 bounds 内
     */
    private static Path createPiecePath(final RectF bounds, final float radius) {
        final float left = bounds.left;
        final float top = bounds.top + radius;
        final float right = bounds.right;
        final float bottom = bounds.bottom;
        final float centerX = bounds.centerX();

        final Path path = new Path();
        path.moveTo(left, top + radius);
        path.lineTo(left, top);
        path.lineTo(centerX - radius, top);
        // 顶部凸起（向上拱起至 bounds.top）
        path.arcTo(new RectF(centerX - radius, top - radius, centerX + radius, top + radius), 180f, -180f, false);
        path.lineTo(right, top);
        path.lineTo(right, bottom);
        path.lineTo(centerX + radius, bottom);
        // 底部凹陷（向上凹入 bounds.bottom - radius）
        path.arcTo(new RectF(centerX - radius, bottom - radius, centerX + radius, bottom + radius), 0f, -180f, false);
        path.lineTo(left, bottom);
        path.close();
        return path;
    }

    private static String encodeToDataUri(final Bitmap bitmap) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return "data:image/png;base64," + Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
    }

    private static void recycleQuietly(final Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    //endregion

    //region: Captcha

    /**
     * 验证码数据封装（含本地校验坐标）
     */
    public static class Captcha {

        public final SwipeCaptchaData data;

        /**
         * 正确横坐标（原始背景图像素），仅本地校验使用；服务端场景由服务端校验
         */
        public final int correctX;

        private Captcha(final SwipeCaptchaData data, final int correctX) {
            this.data = data;
            this.correctX = correctX;
        }
    }

    //endregion

}
