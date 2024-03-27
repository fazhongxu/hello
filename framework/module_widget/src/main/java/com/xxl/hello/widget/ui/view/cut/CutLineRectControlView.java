package com.xxl.hello.widget.ui.view.cut;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.xxl.kit.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义切图切线和面控制视图
 *
 * @author xxl.
 * @date 2024/3/27.
 */
public class CutLineRectControlView extends View {

    /**
     * 辅助线画笔
     */
    private Paint mHelpPaint;

    /**
     * 焦点辅助线画笔
     */
    private Paint mFocusHelpPaint;

    /**
     * 切线画笔
     */
    private Paint mCutLinePaint;

    /**
     * 焦点区域
     */
    private CutStepEntity mFocusElement;

    /**
     * 操作步骤历史记录
     * 只做增加，不做删除，移动索引展示数据，记录某次操作之前的数据，方便撤销和反撤销
     */
    private List<CutStepEntity> mHistoryStepEntities = new ArrayList<>();

    /**
     * 当前显示哪个历史
     */
    private int mCurrentIndex;

    public CutLineRectControlView(Context context) {
        this(context, null);
    }

    public CutLineRectControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CutLineRectControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CutStepEntity cutStepEntity = getCutStepEntity(mCurrentIndex);
        if (cutStepEntity != null) {
            // 获取线条和删除区域画出来
        }
    }

    private float mLastX;

    private float mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // 初始化各种图标bitmap和画笔参数
    }

    /**
     * 获取操作步骤数据
     *
     * @param index
     * @return
     */
    private CutStepEntity getCutStepEntity(int index) {
        return ListUtils.getItem(mHistoryStepEntities, index);
    }

    /**
     * 添加历史
     *
     * @param cutStepEntity
     */
    private void addHistory(CutStepEntity cutStepEntity) {
        if (mCurrentIndex > 0 && mCurrentIndex < ListUtils.getSize(mHistoryStepEntities) - 1) {
            mHistoryStepEntities.add(++mCurrentIndex, cutStepEntity);
        } else {
            mHistoryStepEntities.add(cutStepEntity);
            mCurrentIndex++;
        }
        // 设置focus

        invalidate();
    }

    /**
     * 撤销
     */
    public void revoke() {
        mCurrentIndex--;
        if (mCurrentIndex < 0) {
            mCurrentIndex = 0;
        }
        // 设置focus

        invalidate();
    }

    /**
     * 反撤销
     */
    public void disRevoke() {
        mCurrentIndex++;
        if (mCurrentIndex >= ListUtils.getSize(mHistoryStepEntities)) {
            mCurrentIndex = ListUtils.getSize(mHistoryStepEntities) - 1;
        }
        // 设置focus

        invalidate();
    }
}