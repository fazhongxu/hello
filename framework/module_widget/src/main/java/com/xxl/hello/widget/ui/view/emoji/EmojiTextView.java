package com.xxl.hello.widget.ui.view.emoji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.flyco.roundview.RoundViewDelegate;

/**
 * 支持emoji的自定义textview
 *
 * @author xxl.
 * @date 2024/6/3.
 */
public class EmojiTextView extends com.vanniktech.emoji.EmojiTextView {
    private RoundViewDelegate delegate;

    private int mLineY;
    private int mViewWidth;

    public EmojiTextView(Context context) {
        this(context, null);
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new RoundViewDelegate(this, context, attrs);
    }

    /**
     * use delegate to set attr
     */
    public RoundViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (delegate.isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate.isRadiusHalfHeight()) {
            delegate.setCornerRadius(getHeight() / 2);
        } else {
            delegate.setBgSelector();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            // 重新绘制解决中英文混合后感觉显示得下，但是换行问题
            TextPaint paint = getPaint();
            paint.setColor(getCurrentTextColor());
            paint.drawableState = getDrawableState();
            mViewWidth = getMeasuredWidth();
            String text = getText().toString();
            mLineY = 0;
            mLineY += getTextSize();
            Layout layout = getLayout();

            // layout.getLayout()在4.4.3出现NullPointerException
            if (layout == null) {
                return;
            }

            Paint.FontMetrics fm = paint.getFontMetrics();

            int textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
            textHeight = (int) (textHeight * layout.getSpacingMultiplier() + layout
                    .getSpacingAdd());
            //解决了最后一行文字间距过大的问题
            for (int i = 0; i < layout.getLineCount(); i++) {
                int lineStart = layout.getLineStart(i);
                int lineEnd = layout.getLineEnd(i);
                float width = StaticLayout.getDesiredWidth(text, lineStart,
                        lineEnd, getPaint());
                String line = text.substring(lineStart, lineEnd);

                if (i < layout.getLineCount() - 1) {
                    if (needScale(line)) {
                        drawScaledText(canvas, lineStart, line, width);
                    } else {
                        canvas.drawText(line, 0, mLineY, paint);
                    }
                } else {
                    canvas.drawText(line, 0, mLineY, paint);
                }
                mLineY += textHeight;
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.onDraw(canvas);
        }
    }

    private void drawScaledText(Canvas Canvas, int lineStart, String line,
                                float lineWidth) {
        float x = 0;
        if (isFirstLineOfParagraph(lineStart, line)) {
            String blanks = "  ";
            Canvas.drawText(blanks, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(blanks, getPaint());
            x += bw;

            line = line.substring(3);
        }

        int gapCount = line.length() - 1;
        int i = 0;
        if (line.length() > 2 && line.charAt(0) == 12288
                && line.charAt(1) == 12288) {
            String substring = line.substring(0, 2);
            float cw = StaticLayout.getDesiredWidth(substring, getPaint());
            Canvas.drawText(substring, x, mLineY, getPaint());
            x += cw;
            i += 2;
        }

        float d = (mViewWidth - lineWidth) / gapCount;
        for (; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            Canvas.drawText(c, x, mLineY, getPaint());
            x += cw + d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        return line.length() > 3 && line.charAt(0) == ' '
                && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {
        if (line == null || line.length() == 0) {
            return false;
        } else {
            return line.charAt(line.length() - 1) != '\n';
        }
    }

}